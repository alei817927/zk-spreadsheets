/*

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2013/12/01 , Created by dennis
}}IS_NOTE

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.zss.model.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

import org.zkoss.poi.ss.formula.eval.EvaluationException;
import org.zkoss.poi.ss.formula.eval.ValueEval;
import org.zkoss.zss.model.CellRegion;
import org.zkoss.zss.model.ErrorValue;
import org.zkoss.zss.model.InvalidFormulaException;
import org.zkoss.zss.model.SBook;
import org.zkoss.zss.model.SBookSeries;
import org.zkoss.zss.model.SCellStyle;
import org.zkoss.zss.model.SColumnArray;
import org.zkoss.zss.model.SComment;
import org.zkoss.zss.model.SHyperlink;
import org.zkoss.zss.model.SRichText;
import org.zkoss.zss.model.SSheet;
import org.zkoss.zss.model.impl.sys.formula.FormulaEngineImpl;
import org.zkoss.zss.model.sys.EngineFactory;
import org.zkoss.zss.model.sys.dependency.DependencyTable;
import org.zkoss.zss.model.sys.dependency.Ref;
import org.zkoss.zss.model.sys.formula.EvaluationResult;
import org.zkoss.zss.model.sys.formula.FormulaClearContext;
import org.zkoss.zss.model.sys.formula.FormulaEngine;
import org.zkoss.zss.model.sys.formula.FormulaEvaluationContext;
import org.zkoss.zss.model.sys.formula.FormulaExpression;
import org.zkoss.zss.model.sys.formula.FormulaParseContext;
import org.zkoss.zss.model.util.Validations;
//import org.zkoss.zss.ngmodel.InvalidateModelValueException;
/**
 * 
 * @author dennis
 * @since 3.5.0
 */
public class CellImpl extends AbstractCellAdv {
	private static final long serialVersionUID = 1L;
	private AbstractRowAdv _row;
	private int _index;
	private CellValue _localValue = null;
	private AbstractCellStyleAdv _cellStyle;
	transient private FormulaResultCellValue _formulaResultValue;// cache
	
	
	//use another object to reduce object reference size
	private OptFields _opts;
	
	private static class OptFields implements Serializable{
		private AbstractHyperlinkAdv _hyperlink;
		private AbstractCommentAdv _comment;
	}

	private OptFields getOpts(boolean create){
		if(_opts==null && create){
			_opts = new OptFields();
		}
		return _opts;
	}

	public CellImpl(AbstractRowAdv row, int index) {
		this._row = row;
		this._index = index;
	}

	@Override
	public CellType getType() {
		CellValue val = getCellValue();
		return val==null?CellType.BLANK:val.getType();
	}

	@Override
	public boolean isNull() {
		return false;
	}

	@Override
	public int getRowIndex() {
		checkOrphan();
		return _row.getIndex();
	}

	@Override
	public int getColumnIndex() {
		checkOrphan();
		return _index;
	}

	@Override
	public String getReferenceString() {
		return new CellRegion(getRowIndex(), getColumnIndex()).getReferenceString();
	}

	@Override
	public void checkOrphan() {
		if (_row == null) {
			throw new IllegalStateException("doesn't connect to parent");
		}
	}

	@Override
	public SSheet getSheet() {
		checkOrphan();
		return _row.getSheet();
	}

	@Override
	public void destroy() {
		checkOrphan();
		clearValue();
		_row = null;
	}

	@Override
	public SCellStyle getCellStyle() {
		return getCellStyle(false);
	}

	@Override
	public SCellStyle getCellStyle(boolean local) {
		if (local || _cellStyle != null) {
			return _cellStyle;
		}
		checkOrphan();
		_cellStyle = (AbstractCellStyleAdv) _row.getCellStyle(true);
		AbstractSheetAdv sheet = (AbstractSheetAdv)_row.getSheet();
		if (_cellStyle == null) {
			SColumnArray array = sheet.getColumnArray(getColumnIndex());
			if(array!=null){
				_cellStyle = (AbstractCellStyleAdv)((AbstractColumnArrayAdv)array).getCellStyle(true);
			}
		}
		if (_cellStyle == null) {
			_cellStyle = (AbstractCellStyleAdv) sheet.getBook()
					.getDefaultCellStyle();
		}
		return _cellStyle;
	}

	@Override
	public void setCellStyle(SCellStyle cellStyle) {
		if(cellStyle!=null){
			Validations.argInstance(cellStyle, AbstractCellStyleAdv.class);
		}
		this._cellStyle = (AbstractCellStyleAdv) cellStyle;
		addCellUpdate();
	}

	@Override
	protected void evalFormula() {
		//20140731, henrichen: when share the same book, many users might 
		//populate CellImpl simultaneously; must synchronize it.
		if(_formulaResultValue != null) return;
		synchronized (this.getSheet().getBook().getBookSeries()) {
			if (_formulaResultValue == null) {
				CellValue val = getCellValue();
				if(val!=null &&  val.getType() == CellType.FORMULA){
					FormulaEngine fe = EngineFactory.getInstance().createFormulaEngine();
					// ZSS-818
					// 20141030, henrichen: callback inside FormulaEngine.evaluate() 
					//    will update _formulaResultValue automatically
					EvaluationResult result = 
						fe.evaluate((FormulaExpression) val.getValue(),
							new FormulaEvaluationContext(this,getRef()));
					// ZSS-818
					// 20141113, henrichen: some special case will not go
					//     thru FormulaEngine.evaluate() method, need to
					//     cache directly here. This is quite patchy but...
					if (_formulaResultValue == null) {
						_formulaResultValue = new FormulaResultCellValue(result);
					}
				}
			}
		}
	}

	@Override
	public CellType getFormulaResultType() {
		checkType(CellType.FORMULA);
		evalFormula();

		return _formulaResultValue.getCellType();
	}

	@Override
	public void clearValue() {
		checkOrphan();
		clearFormulaDependency();
		clearFormulaResultCache();
		
		setCellValue(null);
		
		OptFields opts = getOpts(false); 
		if(opts!=null){
			// clear for value, don't clear hyperlink
//			opts.hyperlink = null;
		};
		//don't update when sheet is destroying
		if(BookImpl.destroyingSheet.get()!=getSheet()){
			addCellUpdate();
		}
	}
	
	private void addCellUpdate(){
		ModelUpdateUtil.addCellUpdate(getSheet(), getRowIndex(), getColumnIndex());
	}
	
	@Override
	public void setFormulaValue(String formula) {
		//ZSS-565: enforce internal US locale
		setFormulaValue(formula, Locale.US);
	}
	
	// ZSS-565: Support input with Swedish locale into Formula
	@Override
	public void setFormulaValue(String formula, Locale locale) {
		checkOrphan();
		Validations.argNotNull(formula);
		FormulaEngine fe = EngineFactory.getInstance().createFormulaEngine();
		FormulaParseContext formulaCtx = 
				new FormulaParseContext(this.getSheet().getBook(),this.getSheet(),this,this.getSheet().getSheetName(),null,locale);
		FormulaExpression expr = fe.parse(formula, formulaCtx);//for test error, no need to build dependency
		if(expr.hasError()){	
			String msg = expr.getErrorMessage();
			throw new InvalidFormulaException(msg==null?"The formula ="+formula+" contains error":msg);
		}
		//ZSS-747. 20140828, henrichen: update dependency table in setValue()		
		setValue(expr);
	}
	
	private void clearValueForSet(boolean clearDependency) {
		//in some situation, we should clear dependency (e.g. old type and new type are both formula)
		if(clearDependency){
			clearFormulaDependency();
		}
		clearFormulaResultCache();
		
		OptFields opts = getOpts(false); 
		if(opts!=null){
			// Clear value only, don't clear hyperlink
//			opts.hyperlink = null;
		};
	}

	@Override
	public void clearFormulaResultCache() {
		//ZSS-818: better performance
		if(_formulaResultValue!=null){			
			//only clear when there is a formula result, or poi will do full cache scan to clean blank.
			EngineFactory.getInstance().createFormulaEngine().clearCache(new FormulaClearContext(this));
		}
		
		_formulaResultValue = null;
	}
	
	@Override
	public boolean isFormulaParsingError() {
		if (getType() == CellType.FORMULA) {
			return ((FormulaExpression)getValue(false)).hasError();
		}
		return false;
	}
	
	private void clearFormulaDependency(){
		if(getType()==CellType.FORMULA){
			((AbstractBookSeriesAdv) getSheet().getBook().getBookSeries())
					.getDependencyTable().clearDependents(getRef());
		}
	}

	@Override
	public Object getValue(boolean evaluatedVal) {
		CellValue val = getCellValue();
		if (evaluatedVal && val!=null && val.getType() == CellType.FORMULA) {
			evalFormula();
			return this._formulaResultValue.getValue();
		}
		return val==null?null:val.getValue();
	}

	private boolean isFormula(String string) {
		return string != null && string.startsWith("=") && string.length() > 1;
	}
	
	private CellValue getCellValue(){
		checkOrphan();
		return _localValue;
	}
	
	private void setCellValue(CellValue value){
		checkOrphan();
		this._localValue = value!=null&&value.getType()==CellType.BLANK?null:value;
		
		//clear the dependent's formula result cache
		SBookSeries bookSeries = getSheet().getBook().getBookSeries();
		ModelUpdateUtil.handlePrecedentUpdate(bookSeries,getRef());
	}

	
	private static boolean valueEuqals(Object val1, Object val2){
		return val1==val2||(val1!=null && val1.equals(val2));
	}
	
	@Override
	public void setValue(Object newVal) {
		setValue(newVal, false); //ZSS-853
	}
	
	//ZSS-853
	@Override
	protected void setValue(Object newVal, boolean aString) {
		CellValue oldVal = getCellValue();
		if( (oldVal==null && newVal==null) ||
			(oldVal != null && valueEuqals(oldVal.getValue(),newVal))) {
			return;
		}
		
		CellType newType;
		
		if (newVal == null) {
			newType = CellType.BLANK;
		} else if (newVal instanceof String) {
			if (!aString && isFormula((String) newVal)) { //ZSS-853
				// recursive back with newVal an instance of FromulaExpression
				setFormulaValue(((String) newVal).substring(1)); 
				return;// break;
			} else {
				newType = CellType.STRING;
			}
		} else if (newVal instanceof SRichText) {
			newType = CellType.STRING;
		} else if (newVal instanceof FormulaExpression) {
			newType = CellType.FORMULA;
		} else if (newVal instanceof Date) {
			newType = CellType.NUMBER;
			newVal = EngineFactory.getInstance().getCalendarUtil().dateToDoubleValue((Date)newVal);
		} else if (newVal instanceof Boolean) {
			newType = CellType.BOOLEAN;
		} else if (newVal instanceof Double) {
			newType = CellType.NUMBER;
		} else if (newVal instanceof Number) {
			newType = CellType.NUMBER;
			newVal = ((Number)newVal).doubleValue();
		} else if (newVal instanceof ErrorValue) {
			newType = CellType.ERROR;
		} else {
			throw new IllegalArgumentException(
					"unsupported type "
							+ newVal
							+ ", supports NULL, String, Date, Number and Byte(as Error Code)");
		}

		
		CellValue newCellVal = new InnerCellValue(newType,newVal);
		//ZSS-747. 
		//20140828, henrichen: clear if previous is a formula; update dependency table if a formula
		clearValueForSet(oldVal!=null && oldVal.getType()==CellType.FORMULA);
		if (newType == CellType.FORMULA) {
			FormulaParseContext context = new FormulaParseContext(this, getRef());
			EngineFactory.getInstance().createFormulaEngine().updateDependencyTable((FormulaExpression)newVal, context);
		}
			
		setCellValue(newCellVal);
	}

	@Override
	public SHyperlink getHyperlink() {
		OptFields opts = getOpts(false);
		return opts==null?null:opts._hyperlink;
	}

	@Override
	public void setHyperlink(SHyperlink hyperlink) {
		Validations.argInstance(hyperlink, AbstractHyperlinkAdv.class);
		getOpts(true)._hyperlink = (AbstractHyperlinkAdv)hyperlink;
		addCellUpdate();
	}
	
	@Override
	public SComment getComment() {
		OptFields opts = getOpts(false);
		return opts==null?null:opts._comment;
	}

	@Override
	public void setComment(SComment comment) {
		Validations.argInstance(comment, AbstractCommentAdv.class);
		getOpts(true)._comment = (AbstractCommentAdv)comment;
		addCellUpdate();
	}
	
	//ZSS-848
	@Override
	public void deleteComment() {
		OptFields opts = getOpts(false);
		if (opts == null) return;
		opts._comment = null;
	}
	
	@Override
	void setIndex(int newidx) {
		if(this._index==newidx){
			return;
		}
		
		CellType type = getType();
		String formula = null;
		DependencyTable table = null;
		if(type == CellType.FORMULA){
			formula = getFormulaValue();
			//clear the old dependency
			Ref oldRef = getRef();
			table = ((AbstractBookSeriesAdv) getSheet().getBook().getBookSeries()).getDependencyTable(); 
			table.clearDependents(oldRef);
		}
		this._index = newidx;
		if(formula!=null){
			FormulaEngine fe = EngineFactory.getInstance().createFormulaEngine();
			Ref ref = getRef();
			fe.parse(formula, new FormulaParseContext(this ,ref));//rebuild the expression to make new dependency with current row,column
			if(_formulaResultValue!=null){
				table.setEvaluated(ref);
			}
		}
	}
	
	@Override
	void setRow(int oldRowIdx, AbstractRowAdv row){
		if(oldRowIdx==row.getIndex() && this._row==row){
			return;
		}
		
		CellType type = getType();
		String formula = null;
		DependencyTable table = null;
		if(type == CellType.FORMULA){
			formula = getFormulaValue();
			//clear the old dependency
			SSheet sheet = getSheet();
			Ref oldRef = new RefImpl(sheet.getBook().getBookName(),sheet.getSheetName(),oldRowIdx,getColumnIndex());
			table = ((AbstractBookSeriesAdv) getSheet().getBook().getBookSeries()).getDependencyTable(); 
			table.clearDependents(oldRef);
		}
		this._row = row;
		if(formula!=null){
			FormulaEngine fe = EngineFactory.getInstance().createFormulaEngine();
			Ref ref = getRef();
			fe.parse(formula, new FormulaParseContext(this ,ref));//rebuild the expression to make new dependency with current row,column
			if(_formulaResultValue!=null){
				table.setEvaluated(ref);
			}
		}
	}
	

	protected Ref getRef(){
		return new RefImpl(this);
	}
	
	private static class InnerCellValue extends CellValue{
		private static final long serialVersionUID = 1L;
		private InnerCellValue(CellType type, Object value){
			super(type,value);
		}
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Cell:"+getReferenceString()+"[").append(getRowIndex()).append(",").append(getColumnIndex()).append("]");
		return sb.toString();
	}
	
	//ZSS-688
	//@since 3.6.0
	@Override
	/*package*/ AbstractCellAdv cloneCell(AbstractRowAdv row) {
		final CellImpl tgt = new CellImpl(row, this._index);
		
		if (_localValue != null) {
			Object newVal = _localValue.getValue();
			if (newVal instanceof SRichText) {
				newVal = ((AbstractRichTextAdv)newVal).clone();
			} else if (newVal instanceof FormulaExpression) {
				newVal = "="+((FormulaExpression)newVal).getFormulaString();
			}
			tgt.setValue(newVal);
		}
		
		tgt._cellStyle = this._cellStyle;
		
		// do not clone _formulaResultValue
		//transient private FormulaResultCellValue _formulaResultValue;// cache
		
		if (this._opts != null) {
			final OptFields opts = tgt.getOpts(true);
			if (this._opts._comment != null) {
				opts._comment = this._opts._comment.clone();
			}
			if (this._opts._hyperlink != null) {
				opts._hyperlink = this._opts._hyperlink.clone();
			}
		}
		
		return tgt;
	}
	
	//ZSS-818
	//@since 3.7.0
	public void setFormulaResultValue(ValueEval value) {
		try {
			_formulaResultValue = new FormulaResultCellValue(FormulaEngineImpl.convertToEvaluationResult(value));
		} catch (EvaluationException e) {
			// ignore it!
		}		
	}
	
	//ZSS-873
	//@since 3.7.0
	public FormulaExpression getFormulaExpression() {
		return _localValue != null && _localValue.getType() == CellType.FORMULA ? 
				(FormulaExpression) _localValue.getValue() : null; 
	}
}
