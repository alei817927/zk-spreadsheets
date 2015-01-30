/*
{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Oct 13, 2014, Created by henri
}}IS_NOTE

Copyright (C) 2014 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/

package org.zkoss.poi.ss.formula.ptg;

import org.zkoss.poi.ss.formula.FormulaRenderingWorkbook;
import org.zkoss.poi.ss.formula.WorkbookDependentFormula;
import org.zkoss.poi.util.LittleEndianOutput;

/**
 * TODO: XSSF only
 * @author henri
 *
 * @since 3.9.5
 */
public class TableNamePtg extends NamePtg {
	final private String _tableName;
	final private String _columnName;
	final private boolean _inTable;
	public TableNamePtg(int index, String tableName, String columnName, boolean inTable) {
		super(index);
		_tableName = tableName;
		_columnName = columnName;
		_inTable = inTable;
	}
	
	public String getTableName() {
		return _tableName;
	}

	public void write(LittleEndianOutput out) {
		throw new UnsupportedOperationException();
//		out.writeByte(sid + getPtgClass());
//		out.writeShort(field_1_label_index);
//		out.writeShort(field_2_zero);
	}

	public int getSize() {
		return 0;
	}

	public String toFormulaString(FormulaRenderingWorkbook book) {
		return "_zssTable." + (_inTable ? "" : _tableName) + '[' + _columnName + ']'; //use _zss_ to indicate a TableName function
	}

	public String toFormulaString() {
		throw new RuntimeException("3D references need a workbook to determine formula text");
	}

	public byte getDefaultOperandClass() {
		return Ptg.CLASS_REF;
	}
}
