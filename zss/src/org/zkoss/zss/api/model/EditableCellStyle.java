/* EditableCellStyle.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2013/6/4 , Created by dennis
}}IS_NOTE

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.zss.api.model;

import org.zkoss.zss.api.Range.CellStyleHelper;

/**
 * This interface allows you to change a cell's style.
 * @author dennis
 * @since 3.0.0
 */
public interface EditableCellStyle extends CellStyle{

	/**
	 * Sets font
	 * @param font the font
	 */
	public void setFont(Font font);
	/**
	 * Sets background-color
	 * @param color background-color
	 * @deprecated since 3.5.0 , use {@link #setFillColor(org.zkoss.zss.api.model.Color)}
	 */
	public void setBackgroundColor(Color color);
	
	public void setFillColor(Color color);

	/**
	 * Sets the fill/background pattern <br/>
	 * Note: Spreadsheet (UI display) supports only {@link org.zkoss.zss.api.model.CellStyle.FillPattern#NONE} and {@link org.zkoss.zss.api.model.CellStyle.FillPattern#SOLID}
	 * (Other pattern will be display as {@link org.zkoss.zss.api.model.CellStyle.FillPattern#SOLID_FOREGROUND),
	 * However you can still set another pattern, the data will still be kept when export. 
	 * @param pattern
	 */
	public void setFillPattern(FillPattern pattern);

	/**
	 * Sets horizontal alignment <br/>
	 * Note: Spreadsheet(UI display) supports only {@link org.zkoss.zss.api.model.CellStyle.Alignment#LEFT}, {@link org.zkoss.zss.api.model.CellStyle.Alignment#CENTER}, {@link org.zkoss.zss.api.model.CellStyle.Alignment#RIGHT}
	 * ({@link org.zkoss.zss.api.model.CellStyle.Alignment#CENTER_SELECTION} will be display as {@link org.zkoss.zss.api.model.CellStyle.Alignment#CENTER}, Other alignment will be display as  {@link org.zkoss.zss.api.model.CellStyle.Alignment#LEFT}),
	 * However you can still set another alignment, the data will still be kept when export.
	 * @param alignment
	 */
	public void setAlignment(Alignment alignment);

	/**
	 * Sets vertical alignment <br/>
	 * Note: Spreadsheet(UI display) supports only {@link org.zkoss.zss.api.model.CellStyle.VerticalAlignment#TOP}, {@link org.zkoss.zss.api.model.CellStyle.VerticalAlignment#CENTER}, {@link org.zkoss.zss.api.model.CellStyle.VerticalAlignment#TOP},
	 * (Other alignment will be display as  {@link org.zkoss.zss.api.model.CellStyle.VerticalAlignment#BOTTOM}),
	 * @param alignment
	 */
	public void setVerticalAlignment(VerticalAlignment alignment);

	/**
	 * Sets wrap-text
	 * @param wraptext wrap-text
	 */
	public void setWrapText(boolean wraptext);

	/**
	 * Sets left border <br/>
	 * Note: Spreadsheet(UI display) only supports {@link org.zkoss.zss.api.model.CellStyle.BorderType#NONE}, {@link org.zkoss.zss.api.model.CellStyle.BorderType#THIN}, {@link org.zkoss.zss.api.model.CellStyle.BorderType#DOTTED} and {@link org.zkoss.zss.api.model.CellStyle.BorderType#HAIR},
	 * ({@link org.zkoss.zss.api.model.CellStyle.BorderType#DASH_DOT} will be display as {@link org.zkoss.zss.api.model.CellStyle.Alignment#DOTTED}, Other alignment will be display as  {@link org.zkoss.zss.api.model.CellStyle.Alignment#THIN}),
	 * However you can still set another alignment, the data will still be kept when export.
	 * @param borderType
	 */
	public void setBorderLeft(BorderType borderType);

	/**
	 * Sets top border <br/>
	 * Note: Spreadsheet(UI display) only supports {@link org.zkoss.zss.api.model.CellStyle.BorderType#NONE}, {@link org.zkoss.zss.api.model.CellStyle.BorderType#THIN}, {@link org.zkoss.zss.api.model.CellStyle.BorderType#DOTTED} and {@link org.zkoss.zss.api.model.CellStyle.BorderType#HAIR},
	 * ({@link org.zkoss.zss.api.model.CellStyle.BorderType#DASH_DOT} will be display as {@link org.zkoss.zss.api.model.CellStyle.Alignment#DOTTED}, Other alignment will be display as  {@link org.zkoss.zss.api.model.CellStyle.Alignment#THIN}),
	 * However you can still set another alignment, the data will still be kept when export. 
	 * @param borderType
	 */
	public void setBorderTop(BorderType borderType);

	/**
	 * Sets right border <br/>
	 * Note: Spreadsheet(UI display) only supports {@link org.zkoss.zss.api.model.CellStyle.BorderType#NONE}, {@link org.zkoss.zss.api.model.CellStyle.BorderType#THIN}, {@link org.zkoss.zss.api.model.CellStyle.BorderType#DOTTED} and {@link org.zkoss.zss.api.model.CellStyle.BorderType#HAIR},
	 * ({@link org.zkoss.zss.api.model.CellStyle.BorderType#DASH_DOT} will be display as {@link org.zkoss.zss.api.model.CellStyle.Alignment#DOTTED}, Other alignment will be display as  {@link org.zkoss.zss.api.model.CellStyle.Alignment#THIN}),
	 * However you can still set another alignment, the data will still be kept when export. 
	 * @param borderType
	 */
	public void setBorderRight(BorderType borderType);

	/**
	 * Sets bottom border <br/>
	 * Note: Spreadsheet(UI display) only supports {@link org.zkoss.zss.api.model.CellStyle.BorderType#NONE}, {@link org.zkoss.zss.api.model.CellStyle.BorderType#THIN}, {@link org.zkoss.zss.api.model.CellStyle.BorderType#DOTTED} and {@link org.zkoss.zss.api.model.CellStyle.BorderType#HAIR},
	 * ({@link org.zkoss.zss.api.model.CellStyle.BorderType#DASH_DOT} will be display as {@link org.zkoss.zss.api.model.CellStyle.Alignment#DOTTED}, Other alignment will be display as  {@link org.zkoss.zss.api.model.CellStyle.Alignment#THIN}),
	 * However you can still set another alignment, the data will still be kept when export. 
	 * @param borderType
	 */
	public void setBorderBottom(BorderType borderType);

	/**
	 * Sets top border color. <br/>
	 * you could use {@link CellStyleHelper#createColorFromHtmlColor(String)} to create a {@link org.zkoss.zss.api.model.Color}
	 * @param color
	 */
	public void setBorderTopColor(Color color);

	/**
	 * Sets left border color<br/>
	 * you could use {@link CellStyleHelper#createColorFromHtmlColor(String)} to create a {@link org.zkoss.zss.api.model.Color}
	 * @param color
	 */
	public void setBorderLeftColor(Color color);

	/**
	 * Sets bottom border color <br/>
	 * you could use {@link CellStyleHelper#createColorFromHtmlColor(String)} to create a {@link org.zkoss.zss.api.model.Color}
	 * @param color
	 */
	public void setBorderBottomColor(Color color);

	/**
	 * Sets right border color<br/>
	 * you could use {@link CellStyleHelper#createColorFromHtmlColor(String)} to create a {@link org.zkoss.zss.api.model.Color}
	 * @param color
	 */
	public void setBorderRightColor(Color color);

	/**
	 * Sets data format
	 * @param format
	 */
	public void setDataFormat(String format);
	
	/**
	 * Sets locked
	 * @param locked
	 */
	public void setLocked(boolean locked);

	/**
	 * Sets hidden
	 * @param hidden
	 */
	public void setHidden(boolean hidden);
	
}
