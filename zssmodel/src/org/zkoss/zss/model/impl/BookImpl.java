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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.zkoss.lang.Objects;
import org.zkoss.poi.ss.SpreadsheetVersion;
import org.zkoss.util.logging.Log;
import org.zkoss.zss.model.EventQueueModelEventListener;
import org.zkoss.zss.model.InvalidModelOpException;
import org.zkoss.zss.model.ModelEvent;
import org.zkoss.zss.model.ModelEventListener;
import org.zkoss.zss.model.SBookSeries;
import org.zkoss.zss.model.SCell;
import org.zkoss.zss.model.SCellStyle;
import org.zkoss.zss.model.SColor;
import org.zkoss.zss.model.SColumnArray;
import org.zkoss.zss.model.SFont;
import org.zkoss.zss.model.SName;
import org.zkoss.zss.model.SPicture;
import org.zkoss.zss.model.SPictureData;
import org.zkoss.zss.model.SRow;
import org.zkoss.zss.model.SSheet;
import org.zkoss.zss.model.SNamedStyle;
import org.zkoss.zss.model.impl.sys.DependencyTableAdv;
import org.zkoss.zss.model.impl.sys.formula.ParsingBook;
import org.zkoss.zss.model.sys.EngineFactory;
import org.zkoss.zss.model.sys.dependency.DependencyTable;
import org.zkoss.zss.model.sys.dependency.Ref;
import org.zkoss.zss.model.sys.dependency.Ref.RefType;
import org.zkoss.zss.model.sys.formula.EvaluationContributor;
import org.zkoss.zss.model.sys.formula.FormulaClearContext;
import org.zkoss.zss.model.util.CellStyleMatcher;
import org.zkoss.zss.model.util.FontMatcher;
import org.zkoss.zss.model.util.Strings;
import org.zkoss.zss.model.util.Validations;

/**
 * @author dennis
 * @since 3.5.0
 */
public class BookImpl extends AbstractBookAdv{
	private static final long serialVersionUID = 1L;
	
	private static final Log _logger = Log.lookup(BookImpl.class);

	private final String _bookName;
	
	private String _shareScope;
	
	private SBookSeries _bookSeries;
	
	private final List<AbstractSheetAdv> _sheets = new ArrayList<AbstractSheetAdv>();
	private List<AbstractNameAdv> _names;
	
	private final List<SCellStyle> _cellStyles = new ArrayList<SCellStyle>(); 
	private final Map<String, SNamedStyle> _namedStyles = new HashMap<String, SNamedStyle>(); //ZSS-854
	private final List<SCellStyle> _defaultCellStyles = new ArrayList<SCellStyle>(); //ZSS-854
	private final List<AbstractFontAdv> _fonts = new ArrayList<AbstractFontAdv>();
	private final AbstractFontAdv _defaultFont;
	private final HashMap<AbstractColorAdv,AbstractColorAdv> _colors = new LinkedHashMap<AbstractColorAdv,AbstractColorAdv>();
	
	private final static Random _random = new Random(System.currentTimeMillis());
	private final static AtomicInteger _bookCount = new AtomicInteger();
	private final String _bookId;
	
	private final HashMap<String,AtomicInteger> _objIdCounter = new HashMap<String,AtomicInteger>();
	private final int _maxRowSize = SpreadsheetVersion.EXCEL2007.getMaxRows();
	private final int _maxColumnSize = SpreadsheetVersion.EXCEL2007.getMaxColumns();
	
	private EventListenerAdaptor _listeners;
	private EventListenerAdaptor _queueListeners;
	
	private HashMap<String,Object> _attributes;
	
	private EvaluationContributor _evalContributor;
	
	private ArrayList<SPictureData> _picDatas; //since 3.6.0
	
	/**
	 * the sheet which is destroying now.
	 */
	/*package*/ final static ThreadLocal<SSheet> destroyingSheet = new ThreadLocal<SSheet>(); 
	
	public BookImpl(String bookName){
		Validations.argNotNull(bookName);
		this._bookName = bookName;
		_bookSeries = new SimpleBookSeriesImpl(this);
		_fonts.add(_defaultFont = new FontImpl());
		initDefaultCellStyles();
		_colors.put(ColorImpl.WHITE,ColorImpl.WHITE);
		_colors.put(ColorImpl.BLACK,ColorImpl.BLACK);
		_colors.put(ColorImpl.RED,ColorImpl.RED);
		_colors.put(ColorImpl.GREEN,ColorImpl.GREEN);
		_colors.put(ColorImpl.BLUE,ColorImpl.BLUE);
		
		_bookId = ((char)('a'+_random.nextInt(26))) + Long.toString(/*System.currentTimeMillis()+*/_bookCount.getAndIncrement(), Character.MAX_RADIX) ;
	}
	
	public void initDefaultCellStyles() {
		AbstractCellStyleAdv defaultCellStyle = new CellStyleImpl(_defaultFont);
		_cellStyles.add(defaultCellStyle); //ZSS-854
		_defaultCellStyles.add(defaultCellStyle); //ZSS-854
	}
	
	@Override
	public SBookSeries getBookSeries(){
		return _bookSeries;
	}
	
	@Override
	public String getBookName(){
		return _bookName;
	}
	
	@Override
	public SSheet getSheet(int i){
		return _sheets.get(i);
	}
	
	@Override
	public int getNumOfSheet(){
		return _sheets.size();
	}
	
	@Override
	public SSheet getSheetByName(String name){
		for(SSheet sheet:_sheets){
			if(sheet.getSheetName().equalsIgnoreCase(name)){
				return sheet;
			}
		}
		return null;
	}
	
	@Override
	public SSheet getSheetById(String id){
		for(SSheet sheet:_sheets){
			if(sheet.getId().equals(id)){
				return sheet;
			}
		}
		return null;
	}
	
	protected void checkOwnership(SSheet sheet){
		if(!_sheets.contains(sheet)){
			throw new IllegalStateException("doesn't has ownership "+ sheet);
		}
	}
	protected void checkOwnership(SName name){
		if(_names==null || !_names.contains(name)){
			throw new IllegalStateException("doesn't has ownership "+ name);
		}
	}

	@Override
	public void sendModelEvent(ModelEvent event){
		if(_listeners!=null){
			_listeners.sendModelEvent(event);
		}
		if(_queueListeners!=null){
			_queueListeners.sendModelEvent(event);
		}
	}
	
	@Override
	public SSheet createSheet(String name) {
		return createSheet(name,null);
	}
	
	@Override
	String nextObjId(String type){
		StringBuilder sb = new StringBuilder(_bookId);
		sb.append("_").append(type).append("_");
		AtomicInteger i = _objIdCounter.get(type);
		if(i==null){
			_objIdCounter.put(type, i = new AtomicInteger(0));
		}
		sb.append(i.getAndIncrement());
		return sb.toString();
	}
	
	@Override
	public SSheet createSheet(String name,SSheet src) {
		checkLegalSheetName(name);
		if(src!=null)
			checkOwnership(src);
		

		AbstractSheetAdv sheet = new SheetImpl(this,nextObjId("sheet"));
		sheet.setSheetName(name);
		_sheets.add(sheet);
		
		if(src instanceof AbstractSheetAdv){
			((AbstractSheetAdv)src).copyTo(sheet);
		}
		
		//create formula cache for any sheet, sheet name, position change
		EngineFactory.getInstance().createFormulaEngine().clearCache(new FormulaClearContext(this));

		ModelUpdateUtil.handlePrecedentUpdate(getBookSeries(),new RefImpl(sheet, -1));

		return sheet;
	}

	protected Ref getRef() {
		return new RefImpl(this);
	}

	@Override
	public void setSheetName(SSheet sheet, String newname) {
		checkLegalSheetName(newname);
		checkOwnership(sheet);
		
		int index = getSheetIndex(sheet);
		String oldname = sheet.getSheetName();
		((AbstractSheetAdv)sheet).setSheetName(newname);
		
		//create formula cache for any sheet, sheet name, position change
		EngineFactory.getInstance().createFormulaEngine().clearCache(new FormulaClearContext(this));
		
		ModelUpdateUtil.handlePrecedentUpdate(getBookSeries(),new RefImpl(this.getBookName(),newname, index));//to clear the cache of formula that has unexisted name
		ModelUpdateUtil.handlePrecedentUpdate(getBookSeries(),new RefImpl(this.getBookName(),oldname, index));
		
		renameSheetFormula(oldname,newname,index);
	}
	
	private void renameSheetFormula(String oldName, String newName, int index){
		AbstractBookSeriesAdv bs = (AbstractBookSeriesAdv)getBookSeries();
		DependencyTable dt = bs.getDependencyTable();
		Set<Ref> dependents = dt.getDirectDependents(new RefImpl(getBookName(),oldName,index));
		if(dependents.size()>0){
			
			//clear the dependents dependency before rename it's sheet name
			for(Ref dependent:dependents){
				dt.clearDependents(dependent);
			}
			
			//rebuild the the formula by tuner
			FormulaTunerHelper tuner = new FormulaTunerHelper(bs);
			tuner.renameSheet(this,oldName,newName,dependents);
		}
	}

	private void checkLegalSheetName(String name) {
		if(Strings.isBlank(name)){
			throw new InvalidModelOpException("sheet name '"+name+"' is not legal");
		}
		if(getSheetByName(name)!=null){
			throw new InvalidModelOpException("sheet name '"+name+"' is duplicated");
		}
	}
	
	private void checkLegalNameName(String name,String sheetName) {
		if(Strings.isBlank(name)){
			throw new InvalidModelOpException("name '"+name+"' is not legal");
		}
		if(getNameByName(name,sheetName)!=null){ //must be unique in the scope
			throw new InvalidModelOpException("name '"+name+"' "+(sheetName==null?"":" in '"+sheetName+"'")+" is duplicated");
		}
		if(sheetName!=null && getSheetByName(sheetName)==null){
			throw new InvalidModelOpException("no such sheet "+sheetName);
		}
		//ZSS-660: valid name
		//@see  http://office.microsoft.com/en-us/excel-help/define-and-use-names-in-formulas-HA010147120.aspx
		//length must less than or equals to 255
		if (name.length() > 255) {
			throw new InvalidModelOpException("name '"+name+"' is not legal: cannot exceed 255 characters");
		}
		
		//1st character must be a letter, underscore, or backslash
		char c1 = name.charAt(0);
		if (!Character.isLetter(c1) && c1 != '_' && c1 != '\\') {
			throw new InvalidModelOpException("name '"+name+"' is not legal: first character must be a letter, an underscore, or a backslash");
		}
		
		boolean invalid = c1 == '_' || c1 == '\\' || c1 == '?' || c1 == '.'; //impossible be a valid cell reference
		int colIndex = invalid ? -2 : Character.getNumericValue(c1) - 9;
		if (!invalid) {
			invalid = colIndex < 0;
		}
		int rowIndex = -1;
		//remaining characters must be letters, digits, periods, or underscores.
		for (int j = 1, len = name.length(); j < len; ++j) {
			char ch = name.charAt(j);
			if (Character.isLetter(ch)) { //analyze colIndex
				if (invalid) continue;  
				if (rowIndex >= 0) { //letter -> digit -> letter
					invalid = true;
					continue;
				}
				int c = Character.getNumericValue(ch) - 9;
				if (c < 0) {
					invalid = true;
					continue;
				}
				colIndex = colIndex * 26 + c;
			} else if (Character.isDigit(ch)) { //analyze rowIndex
				if (invalid) continue; 
				if (rowIndex < 0) {
					rowIndex = Character.getNumericValue(ch);
				} else {
					rowIndex = rowIndex * 10 + Character.getNumericValue(ch);
				}
			} else if (ch != '.' && ch != '_' && ch != '?' && ch != '\\') {
				throw new InvalidModelOpException("name '"+name+"' is not legal: the character '"+ ch+ "' at index "+ j + " must be a letter, a digit, an underscore, a period, a question mark, or a backslash");
			} else { //ZSS-792
				invalid = true; // '.' or '-' or '?' or '\', impossible to be a valid cell reference
			}
		}
		
		//cannot be a valid cell reference address
		if (!invalid && colIndex >= 0 && colIndex <= getMaxColumnSize() && rowIndex >= 0 && rowIndex < getMaxRowSize()) {
			throw new InvalidModelOpException("name '"+name+"' is not legal: cannot be a cell reference");
		}
			
		//cannot be 'C' or 'R'
		if (name.equalsIgnoreCase("C") || name.equalsIgnoreCase("R")) {
			throw new InvalidModelOpException("name '"+name+"' is not legal: cannot be 'C', 'c', 'R', or 'r'");
		}
	}

	@Override
	public void deleteSheet(SSheet sheet) {
		checkOwnership(sheet);
		
		final String bookName = sheet.getBook().getBookName();
		
		destroyingSheet.set(sheet);
		try{
			((AbstractSheetAdv)sheet).destroy();
		}finally{
			destroyingSheet.set(null);
		}
		String oldName = sheet.getSheetName();
		int index = _sheets.indexOf(sheet);
		_sheets.remove(index);
		
		//create formula cache for any sheet, sheet name, position change
		EngineFactory.getInstance().createFormulaEngine().clearCache(new FormulaClearContext(this));
		
//		sendModelInternalEvent(ModelInternalEvents.createModelInternalEvent(ModelInternalEvents.ON_SHEET_DELETED, 
//				this,ModelInternalEvents.createDataMap(ModelInternalEvents.PARAM_SHEET_OLD_INDEX, index)));
		
		ModelUpdateUtil.handlePrecedentUpdate(getBookSeries(),new RefImpl(this.getBookName(),sheet.getSheetName(), index));
		
		renameSheetFormula(oldName, null, index);
		
		//ZSS-815
		// adjust sheet index
		adjustSheetIndex(bookName, index);
	}

	//ZSS-815
	private void adjustSheetIndex(String bookName, int index) {
		AbstractBookSeriesAdv bs = (AbstractBookSeriesAdv)getBookSeries();
		DependencyTableAdv dt = (DependencyTableAdv) bs.getDependencyTable();
		dt.adjustSheetIndex(bookName, index, -1);
	}
		
	@Override
	public void moveSheetTo(SSheet sheet, int index) {
		checkOwnership(sheet);
		if(index<0|| index>=_sheets.size()){
			throw new InvalidModelOpException("new position out of bound "+_sheets.size() +"<>" +index);
		}
		int oldindex = _sheets.indexOf(sheet);
		if(oldindex==index){
			return;
		}
		
		//ZSS-820
		reorderSheetFormula(getSheet(oldindex).getSheetName(), oldindex, index);
		_sheets.remove(oldindex);
		_sheets.add(index, (AbstractSheetAdv)sheet);
		
		//create formula cache for any sheet, sheet name, position change
		EngineFactory.getInstance().createFormulaEngine().clearCache(new FormulaClearContext(this));

		ModelUpdateUtil.handlePrecedentUpdate(getBookSeries(),new RefImpl(this.getBookName(),sheet.getSheetName(), index));
		
		// adjust sheet index
		moveSheetIndex(getBookName(), oldindex, index);
	}
	//ZSS-820
	private void moveSheetIndex(String bookName, int oldIndex, int newIndex) {
		AbstractBookSeriesAdv bs = (AbstractBookSeriesAdv)getBookSeries();
		DependencyTableAdv dt = (DependencyTableAdv) bs.getDependencyTable();
		dt.moveSheetIndex(bookName, oldIndex, newIndex);
	}

	public void dump(StringBuilder builder) {
		for(AbstractSheetAdv sheet:_sheets){
			if(sheet instanceof SheetImpl){
				((SheetImpl)sheet).dump(builder);
			}else{
				builder.append("\n").append(sheet);
			}
		}
	}

	@Override
	public SCellStyle getDefaultCellStyle() {
		return getDefaultCellStyle(0);
	}

	//ZSS-854
	@Override
	public SCellStyle getDefaultCellStyle(int index) {
		return _defaultCellStyles.get(index);
	}

	@Override
	public void setDefaultCellStyle(SCellStyle cellStyle) {
		if (cellStyle == null) return;
		AbstractCellStyleAdv defaultCellStyle = (AbstractCellStyleAdv) cellStyle;
		_defaultCellStyles.set(0, defaultCellStyle);
		_cellStyles.set(0, defaultCellStyle);
	}

	@Override
	public SCellStyle createCellStyle(boolean inStyleTable) {
		return createCellStyle(null,inStyleTable);
	}

	@Override
	public SCellStyle createCellStyle(SCellStyle src,boolean inStyleTable) {
		if(src!=null){
			Validations.argInstance(src, AbstractCellStyleAdv.class);
		}
		AbstractCellStyleAdv style = new CellStyleImpl(_defaultFont);
		if(src!=null){
			style.copyFrom(src);
		}
		
		if(inStyleTable){
			_cellStyles.add(style);
		}
		
		return style;
	}
	
	@Override
	public SCellStyle searchCellStyle(CellStyleMatcher matcher) {
		for(SCellStyle style:_cellStyles){
			if(matcher.match(style)){
				return style;
			}
		}
		return null;
	}
	
	
	@Override
	public SFont getDefaultFont() {
		return _defaultFont;
	}

	@Override
	public SFont createFont(boolean inFontTable) {
		return createFont(null,inFontTable);
	}

	@Override
	public SFont createFont(SFont src,boolean inFontTable) {
		if(src!=null){
			Validations.argInstance(src, AbstractFontAdv.class);
		}
		AbstractFontAdv font = new FontImpl();
		if(src!=null){
			font.copyFrom(src);
		}
		
		if(inFontTable){
			_fonts.add(font);
		}
		
		return font;
	}
	
	@Override
	public SFont searchFont(FontMatcher matcher) {
		for(SFont font:_fonts){
			if(matcher.match(font)){
				return font;
			}
		}
		return null;
	}
	
	@Override
	public int getMaxRowSize() {
		return _maxRowSize;
	}

	@Override
	public int getMaxColumnSize() {
		return _maxColumnSize;
	}

	@Override
	public void optimizeCellStyle() {
		HashMap<String,SCellStyle> stylePool = new LinkedHashMap<String,SCellStyle>();
		_cellStyles.clear();
		_fonts.clear();
		
		SCellStyle defaultStyle = getDefaultCellStyle();
		SFont defaultFont = getDefaultFont();
		stylePool.put(((AbstractCellStyleAdv)defaultStyle).getStyleKey(), defaultStyle);
		
		for(SSheet sheet:_sheets){
			Iterator<SRow> rowIter = sheet.getRowIterator(); 
			while(rowIter.hasNext()){
				SRow row = rowIter.next();
				
				row.setCellStyle(hitStyle(defaultStyle,row.getCellStyle(),stylePool));
				Iterator<SCell> cellIter = sheet.getCellIterator(row.getIndex());
				while(cellIter.hasNext()){
					SCell cell = cellIter.next();
					cell.setCellStyle(hitStyle(defaultStyle,cell.getCellStyle(),stylePool));
				}
			}
			Iterator<SColumnArray> colIter = sheet.getColumnArrayIterator();
			while(colIter.hasNext()){
				SColumnArray colarr = colIter.next();
				colarr.setCellStyle(hitStyle(defaultStyle,colarr.getCellStyle(),stylePool));
			}
		}
		
		_cellStyles.addAll((Collection)stylePool.values());
		String key;
		HashMap<String,SFont> fontPool = new LinkedHashMap<String,SFont>();
		
		fontPool.put(((AbstractFontAdv)defaultFont).getStyleKey(), defaultFont);
		for(SCellStyle style:_cellStyles){
			SFont font = style.getFont();
			key = ((AbstractFontAdv)font).getStyleKey();
			if(fontPool.get(key)==null){
				fontPool.put(key, font);
			}
		}
		
		_fonts.addAll((Collection)fontPool.values());
		
		_colors.clear();//color is immutable, just clear it.
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SCellStyle> getCellStyleTable(){
		return Collections.unmodifiableList((List)_cellStyles);
	}
	@SuppressWarnings("unchecked")
	public List<SFont> getFontTable(){
		return Collections.unmodifiableList((List)_fonts);
	}
	
	private SCellStyle hitStyle(SCellStyle defaultStyle,SCellStyle currSytle,
			HashMap<String, SCellStyle> stylePool) {
		String key;
		SCellStyle hit;
		if(currSytle==defaultStyle){//quick case for most cell use default style
			return defaultStyle;
		}else{
			key = ((AbstractCellStyleAdv)currSytle).getStyleKey();
			hit = stylePool.get(key);
			if(hit==null){
				stylePool.put(key, hit = currSytle);
			}
		}
		return hit;
	}

	@Override
	public void addEventListener(ModelEventListener listener){
		if(listener instanceof EventQueueModelEventListener){
			if(_queueListeners==null){
				String scope = getShareScope();
				if(scope==null){
					scope = "desktop";//default desktop
				}
				_queueListeners = new EventQueueListenerAdaptor(scope,getId());
			}
			_queueListeners.addEventListener(listener);
		}else{
			if(_listeners==null){
				_listeners = new DirectEventListenerAdaptor();
			}
			_listeners.addEventListener(listener);
		}
	}
	@Override
	public void removeEventListener(ModelEventListener listener){
		if(listener instanceof EventQueueModelEventListener && _queueListeners!=null){
			_queueListeners.removeEventListener(listener);
			if(_queueListeners.size()==0){
				_queueListeners = null;//clean up, so user can change share-scope then.
			}
		}else if(_listeners!=null){
			_listeners.removeEventListener(listener);
		}
	}

	@Override
	public Object getAttribute(String name) {
		return _attributes==null?null:_attributes.get(name);
	}

	@Override
	public Object setAttribute(String name, Object value) {
		if(_attributes==null){
			_attributes = new HashMap<String, Object>();
		}
		return _attributes.put(name, value);
	}

	@Override
	public Map<String, Object> getAttributes() {
		return _attributes==null?Collections.EMPTY_MAP:Collections.unmodifiableMap(_attributes);
	}

	@Override
	public SColor createColor(byte r, byte g, byte b) {
		AbstractColorAdv newcolor = new ColorImpl(r,g,b);
		AbstractColorAdv color = _colors.get(newcolor);//reuse the existed color object
		if(color==null){
			_colors.put(newcolor, color = newcolor);
		}
		return color;
	}

	@Override
	public SColor createColor(String htmlColor) {
		AbstractColorAdv newcolor = new ColorImpl(htmlColor);
		AbstractColorAdv color = _colors.get(newcolor);//reuse the existed color object
		if(color==null){
			_colors.put(newcolor, color = newcolor);
		}
		return color;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SSheet> getSheets() {
		return Collections.unmodifiableList((List)_sheets);
	}
	@Override
	public SName createName(String namename) {
		return createName(namename,null);
	}
	@Override
	public SName createName(String namename,String sheetName) {
		checkLegalNameName(namename,sheetName);

		AbstractNameAdv name = new NameImpl(this,nextObjId("name"),namename,sheetName);
		
		if(_names==null){
			_names = new ArrayList<AbstractNameAdv>();
		}
		
		_names.add(name);
		return name;
	}

	@Override
	public void setNameName(SName name, String newname) {
		setNameName(name,newname,null);
	}
	public void setNameName(SName name, String newname, String sheetName) {
		checkLegalNameName(newname,sheetName);
		checkOwnership(name);
		
		//create formula cache for name, currently, we can just clear all of book.
		EngineFactory.getInstance().createFormulaEngine().clearCache(new FormulaClearContext(this));
		
		//notify the (old) name is change before update name
		ModelUpdateUtil.handlePrecedentUpdate(getBookSeries(),new NameRefImpl((AbstractNameAdv)name));
		
		final String oldName = name.getName(); // ZSS-661
		((AbstractNameAdv)name).setName(newname,sheetName);
		//don't need to notify new name precedent update, since Name handle it itself
		
		//Rename formula that contains this name
		renameNameFormula(name, oldName, newname, sheetName); // ZSS-661
	}
	
	private void renameNameFormula(SName name, String oldName, String newName, String sheetName) {
		AbstractBookSeriesAdv bs = (AbstractBookSeriesAdv)getBookSeries();
		FormulaTunerHelper tuner = new FormulaTunerHelper(bs);
		DependencyTable dt = bs.getDependencyTable();
		final 
		Ref ref = new NameRefImpl(name.getBook().getBookName(),name.getApplyToSheetName(), oldName); //old name
		Set<Ref> dependents = dt.getDirectDependents(ref);
		if(dependents.size()>0){
			//clear the dependents dependency before rename it's Name name
			for(Ref dependent:dependents){
				dt.clearDependents(dependent);
			}
			
			int sheetIndex = sheetName == null ? -1 : getSheetIndex(sheetName);
			//rebuild the the formula by tuner
			tuner.renameName(this, oldName, newName, dependents, sheetIndex);
		}
	}

	@Override
	public void deleteName(SName name) {
		checkOwnership(name);
		
		((AbstractNameAdv)name).destroy();
		
		int index = _names.indexOf(name);
		_names.remove(index);
		
//		sendEvent(ModelEvents.ON_NAME_DELETED, 
//				ModelEvents.PARAM_NAME, sheet,
//				ModelEvents.PARAM_SHEET_OLD_INDEX, index);
	}

	@Override
	public int getNumOfName() {
		return _names==null?0:_names.size();
	}

	@Override
	public SName getName(int idx) {
		if(_names==null){
			throw new ArrayIndexOutOfBoundsException(idx);
		}
		return _names.get(idx);
	}

	@Override
	public SName getNameByName(String namename) {
		return getNameByName(namename,null);
	}
	public SName getNameByName(String namename, String sheetName) {
		if(_names==null)
			return null;
		for(SName name:_names){
			//ZSS-436
			final String scopeSheetName = name.getApplyToSheetName();
			if ((sheetName == scopeSheetName || (sheetName != null && sheetName.equalsIgnoreCase(scopeSheetName)))
					&& name.getName().equalsIgnoreCase(namename)){
				return name;
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<SName> getNames() {
		return _names==null?Collections.EMPTY_LIST:Collections.unmodifiableList((List)_names);
	}

	@Override
	public int getSheetIndex(SSheet sheet) {
		return _sheets.indexOf(sheet);
	}
	
	@Override
	public int getSheetIndex(String sheetName) {
		int i=0;
		for(SSheet sheet:_sheets){
			if(sheet.getSheetName().equals(sheetName)){
				return i;
			}
			i++;
		}
		return -1;
	}

	@Override
	public void setShareScope(String scope) {
		if(!Objects.equals(this._shareScope,scope)){
			
			if("disable".equals(scope)){
				if(_listeners!=null){
					_listeners.clear();
				}
				if(_queueListeners!=null){
					_queueListeners.clear();
				}
				return;
			}
			
			if(_queueListeners!=null && _queueListeners.size()>0){
				throw new IllegalStateException("can't change share scope after registed any queue model event listener");
			}
			
			this._shareScope = scope;
		}
	}

	@Override
	public String getShareScope() {
		return _shareScope;
	}

	@Override
	void setBookSeries(SBookSeries bookSeries) {
		this._bookSeries = bookSeries;
	}

	@Override
	public EvaluationContributor getEvaluationContributor() {
		return _evalContributor;
	}

	@Override
	public void setEvaluationContributor(EvaluationContributor contributor) {
		this._evalContributor = contributor;
	}

	@Override
	public int getMaxRowIndex() {
		return getMaxRowSize()-1;
	}

	@Override
	public int getMaxColumnIndex() {
		return getMaxColumnSize()-1;
	}

	@Override 
	public String getId(){
		return _bookId; 
	}
	
	@Override
	public SPictureData addPictureData(SPicture.Format format, byte[] data) {
		if (_picDatas == null) {
			_picDatas = new ArrayList<SPictureData>(4);
		}
		int index = _picDatas.size();
		SPictureData picData = new PictureDataImpl(index, format, data);
		_picDatas.add(picData);
		return picData;
	}

	@Override
	public SPictureData getPictureData(int index) {
		if (index < 0 || _picDatas == null || index >= _picDatas.size())
			return null;
		return _picDatas.get(index);
	}

	@Override
	public Collection<SPictureData> getPicturesDatas() {
		if (_picDatas == null) return Collections.emptyList();
		
		final List<SPictureData> list = new ArrayList<SPictureData>(_picDatas.size());
		for (SPictureData picData : _picDatas) {
			if (picData != null) {
				list.add(picData);
			}
		}
		return list;
	}

	//ZSS-820
	private void reorderSheetFormula(String sheetName, int oldIndex, int newIndex) {
		AbstractBookSeriesAdv bs = (AbstractBookSeriesAdv)getBookSeries();
		DependencyTable dt = bs.getDependencyTable();
		Set<Ref> dependents = dt.getDirectDependents(new RefImpl(getBookName(),sheetName, oldIndex));
		if(dependents.size()>0){
			final String bookName = getBookName();
			final int low = oldIndex < newIndex ? oldIndex : newIndex;
			final int high = oldIndex < newIndex ? newIndex : oldIndex;

			Set<String> bookNames = new HashSet<String>();
			//filter out dependents that does not need to do reorder
			for(final Iterator<Ref> it = dependents.iterator(); it.hasNext();) {
				Ref dependent = it.next();
				Set<Ref> precedents = ((DependencyTableAdv)dt).getDirectPrecedents(dependent);
				boolean candidate = false;
				for (Ref p : precedents) {
					if (p.getType() != RefType.AREA && p.getType() != RefType.CELL)
						continue;
					final String bookName0 = p.getBookName();
					final String sheet1 = p.getSheetName();
					final String sheet2 = p.getLastSheetName();
					
					final int low0 = getSheetIndex(sheet1);
					int high0 = getSheetIndex(sheet2);
					if (high0 < 0) high0 = low0;
					if (low0 == high0) continue; // single sheet, as is.
							
					// no intersection; as is.
					if (high0 < low || low0 > high) continue;

					if (low0 == oldIndex) {
						if (low0 != high0 && newIndex >= high0) { //2. move beyond original high end
							//must change low end sheet name! (_map & _remap must be remapped)
							candidate = true;
							
							// adjust extern sheet name
							if (!bookNames.contains(bookName0)) {
								ParsingBook parsingBook = new ParsingBook(bs.getBook(bookName0));
								parsingBook.reorderSheet(bookName, oldIndex, newIndex);
								bookNames.add(bookName0);
							}
							break;
						}
					}
					
					if (high0 == oldIndex) {
						if (low0 != high0 && newIndex <= low0) { //4. move beyond original low end
							// high0 index not change but sheet name changed
							candidate = true;
							
							// adjust extern sheet name
							if (!bookNames.contains(bookName0)) {
								ParsingBook parsingBook = new ParsingBook(bs.getBook(bookName0));
								parsingBook.reorderSheet(bookName, oldIndex, newIndex);
								bookNames.add(bookName0);
							}
							break;
						}
					}
				}
				if (!candidate) {
					it.remove();
				}
			}

			if (!dependents.isEmpty()) {
				for (Ref dependent : dependents) {
					dt.clearDependents(dependent);
				}
			
				//rebuild the the formula by tuner
				FormulaTunerHelper tuner = new FormulaTunerHelper(bs);
				tuner.reorderSheet(this, oldIndex, newIndex, dependents);
			}
		}
	}
	
	//ZSS-854
	public SNamedStyle getNamedStyle(String name) {
		return _namedStyles.get(name);
	}

	//ZSS-854
	@Override
	public int addDefaultCellStyle(SCellStyle cellStyle) {
		_defaultCellStyles.add((AbstractCellStyleAdv)cellStyle);
		return _defaultCellStyles.size() - 1;
	}
	
	//ZSS-854
	@Override
	public Collection<SCellStyle> getDefaultCellStyles() {
		return _defaultCellStyles;
	}

	//ZSS-854
	@Override
	public void addNamedCellstyle(SNamedStyle namedStyle) {
		_namedStyles.put(namedStyle.getName(), namedStyle);
	}

	//ZSS-854
	@Override
	public Collection<SNamedStyle> getNamedStyles() {
		return _namedStyles.values();
	}

	//ZSS-854
	@Override
	public void clearDefaultCellStyles() {
		_cellStyles.clear();
		_defaultCellStyles.clear();
	}

	//ZSS-854
	@Override
	public void clearNamedStyles() {
		_namedStyles.clear();		
	}
}
