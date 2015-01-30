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

import org.zkoss.zss.model.SColumn;
import org.zkoss.zss.model.SSheet;
/**
 * 
 * @author dennis
 * @since 3.5.0
 */
public abstract class AbstractSheetAdv implements SSheet,LinkedModelObject,Serializable{
	private static final long serialVersionUID = 1L;
	
	/*package*/ abstract AbstractRowAdv getRow(int rowIdx, boolean proxy);
	/*package*/ abstract AbstractRowAdv getOrCreateRow(int rowIdx);
//	/*package*/ abstract int getRowIndex(AbstractRowAdv row);
	
	/*package*/ abstract SColumn getColumn(int columnIdx, boolean proxy);
	/*package*/ abstract AbstractColumnArrayAdv getOrSplitColumnArray(int index);
	
//	/*package*/ abstract ColumnAdv getOrCreateColumn(int columnIdx);
//	/*package*/ abstract int getColumnIndex(ColumnAdv column);
	
	/*package*/ abstract AbstractCellAdv getCell(int rowIdx, int columnIdx, boolean proxy);
	/*package*/ abstract AbstractCellAdv getOrCreateCell(int rowIdx, int columnIdx);
	
	
	/*package*/ abstract void copyTo(AbstractSheetAdv sheet);
	/*package*/ abstract void setSheetName(String name);
	
//	/*package*/ abstract void onModelInternalEvent(ModelInternalEvent event);
	
}
