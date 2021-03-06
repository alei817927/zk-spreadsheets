/* MultipleStyleCellLoader.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2013/8/7 , Created by dennis
}}IS_NOTE

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.zss.ui.sys;

import org.zkoss.zss.model.SSheet;

/**
 * @author dennis
 * @since 3.0.0
 */
public interface CellDisplayLoader {

	/**
	 * return the html text for this cell
	 * @return the html text or null if the cell is not support to show it.
	 */
	public String getCellHtmlText(SSheet sheet, int row, int column);
}
