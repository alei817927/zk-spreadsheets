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

import org.zkoss.zss.model.SCellStyle;
import org.zkoss.zss.model.SColumn;
import org.zkoss.zss.model.SSheet;
import org.zkoss.zss.model.util.Validations;
/**
 * 
 * @author dennis
 * @since 3.5.0
 */
public class ColumnArrayImpl extends AbstractColumnArrayAdv {
	private static final long serialVersionUID = 1L;

	private AbstractSheetAdv _sheet;
	private AbstractCellStyleAdv _cellStyle;
	
	private Integer _width;
	private boolean _hidden = false;
	private boolean _customWidth = false;

	int _index;
	int _lastIndex;
	
	public ColumnArrayImpl(AbstractSheetAdv sheet, int index, int lastIndex) {
		this._sheet = sheet;
		this._index = index;
		this._lastIndex = lastIndex;
	}

	@Override
	public int getIndex() {
		checkOrphan();
		return _index;
	}


	@Override
	public void checkOrphan() {
		if (_sheet == null) {
			throw new IllegalStateException("doesn't connect to parent");
		}
	}

	@Override
	public void destroy() {
		checkOrphan();
		_sheet = null;
	}

	@Override
	public SSheet getSheet() {
		checkOrphan();
		return _sheet;
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
		return _sheet.getBook().getDefaultCellStyle();
	}

	@Override
	public void setCellStyle(SCellStyle cellStyle) {
		Validations.argInstance(cellStyle, AbstractCellStyleAdv.class);
		this._cellStyle = (AbstractCellStyleAdv) cellStyle;
	}

	@Override
	public int getWidth() {
		if(_width!=null){
			return _width.intValue();
		}
		checkOrphan();
		return getSheet().getDefaultColumnWidth();
	}

	@Override
	public boolean isHidden() {
		return _hidden;
	}

	@Override
	public void setWidth(int width) {
		this._width = Integer.valueOf(width);
	}

	@Override
	public void setHidden(boolean hidden) {
		this._hidden = hidden;
	}

	@Override
	public int getLastIndex() {
		return _lastIndex;
	}

	@Override
	void setIndex(int index) {
		this._index = index;
	}

	@Override
	void setLastIndex(int lastIndex) {
		this._lastIndex = lastIndex;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append("[").append(getIndex()).append("-").append(getLastIndex()).append("]");
		return sb.toString();
	}

	@Override
	public boolean isCustomWidth() {
		return _customWidth;
	}

	@Override
	public void setCustomWidth(boolean custom) {
		_customWidth = custom;
	}

	//ZSS-688
	//@since 3.6.0
	/*package*/ ColumnArrayImpl cloneColumnArrayImpl(AbstractSheetAdv sheet) {
		final ColumnArrayImpl tgt = new ColumnArrayImpl(sheet, this._index, this._lastIndex);
		tgt._width = this._width;
		tgt._hidden = this._hidden;
		tgt._customWidth = this._customWidth;
		tgt._cellStyle = this._cellStyle;
		return tgt;
	}
}
