/* ParsingBook.java

	Purpose:
		
	Description:
		
	History:
		Dec 13, 2013 Created by Pao Wang

Copyright (C) 2013 Potix Corporation. All Rights Reserved.
 */
package org.zkoss.zss.model.impl.sys.formula;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.poi.ss.SpreadsheetVersion;
import org.zkoss.poi.ss.formula.EvaluationName;
import org.zkoss.poi.ss.formula.EvaluationWorkbook.ExternalSheet;
import org.zkoss.poi.ss.formula.FormulaParser;
import org.zkoss.poi.ss.formula.FormulaParsingWorkbook;
import org.zkoss.poi.ss.formula.FormulaRenderingWorkbook;
import org.zkoss.poi.ss.formula.FormulaType;
import org.zkoss.poi.ss.formula.ptg.NamePtg;
import org.zkoss.poi.ss.formula.ptg.NameXPtg;
import org.zkoss.poi.ss.formula.ptg.Ptg;
import org.zkoss.poi.ss.formula.ptg.TableNamePtg;
import org.zkoss.util.logging.Log;
import org.zkoss.zss.model.SBook;
import org.zkoss.zss.model.sys.formula.FormulaEngine;

/**
 * A pseudo formula parsing workbook for parsing only.
 * @author Pao
 * @since 3.5.0
 */
public class ParsingBook implements FormulaParsingWorkbook, FormulaRenderingWorkbook {
	private static final Log logger = Log.lookup(ParsingBook.class.getName());

	private SBook book;
	// ZSS-747
	private SheetIndexes _indexes;

	public ParsingBook(SBook book) {
		this.book = book;
		//ZSS-747
		synchronized (book) {
			this._indexes = (SheetIndexes) book.getAttribute(FormulaEngine.KEY_SHEET_INDEXES);
			if (this._indexes == null) {
				this._indexes = new SheetIndexes();
				book.setAttribute(FormulaEngine.KEY_SHEET_INDEXES, this._indexes);
			}
		}
	}

	// ZSS-661
	public void renameName(int sheetIndex, String oldName, String newName) {
		final String sidx = String.valueOf(sheetIndex);
		String oldkey = toKey(sidx, oldName);
		//ZSS-747
		synchronized (_indexes) {
			final Integer index = _indexes.name2index.remove(oldkey);
			if (index != null) {
				String key = toKey(sidx, newName);
				_indexes.name2index.put(key, index);
				_indexes.index2name.set(index, new Object[] {Integer.valueOf(sheetIndex), newName});
			}
		}
	}
	
	@Override
	public EvaluationName getName(String name, int sheetIndex) {
		return getOrCreateName(name, sheetIndex);
	}

	@Override
	public NameXPtg getNameXPtg(String name) {
		String key = toKey("", name);
		//ZSS-747
		synchronized (_indexes) {
			Integer index = _indexes.name2index.get(key);
			if(index == null) {
				// formula function name
				index = _indexes.index2name.size();
				_indexes.index2name.add(new Object[] {Integer.valueOf(-1), name});
				_indexes.name2index.put(key, index);
			}
			return new NameXPtg(0, index);
		}
	}

	@Override
	public int getExternalSheetIndex(String sheetName) {
		return getExternalSheetIndex(null, sheetName);
	}

	//ZSS-781
	private String[] splitSheetName(String sheetName) {
		int p = sheetName.indexOf(':');
		String name = p < 0 ? sheetName : sheetName.substring(0, p);
		String lastName = p < 0 ? sheetName : sheetName.substring(p+1);
		return name.equalsIgnoreCase(lastName) ? new String[] {name} : new String[] {name, lastName};  
	}
	
	@Override
	public int getExternalSheetIndex(String workbookName, String sheetName) {
		String[] names = splitSheetName(sheetName);
		String name;
		String lastName;
		if (names.length == 1) {
			sheetName = name = lastName = names[0];
		} else {
			name = names[0];
			lastName = names[1];
		}
		
		// directly get index if existed
		String key = toKey(workbookName, sheetName);
		//ZSS-747
		synchronized (_indexes) {
			Integer index = _indexes.sheetName2index.get(key);
			if(index == null) {
				// create new index and check sheet name is 3D or not
				index = _indexes.index2sheet.size();
				_indexes.index2sheet.add(new ExternalSheet(workbookName, name, lastName));
				_indexes.sheetName2index.put(key, index);
			}
			return index;
		}
	}
	
	/**
	 * @param sheetName sheet name or 3D sheet name (e.g "Sheet1:Sheet3")
	 * @return the external sheet index or -1 if not found
	 */
	public int findExternalSheetIndex(String sheetName) {
		return findExternalSheetIndex(null, sheetName);
	}

	private String normalizeSheetName(String sheetName) {
		String[] names = splitSheetName(sheetName);
		return names.length == 1 ? names[0] : sheetName;
	}
	
	/**
	 * @param workbookName book name or null
	 * @param sheetName sheet name or 3D sheet name (e.g "Sheet1:Sheet3")
	 * @return the external sheet index or -1 if not found
	 */
	public int findExternalSheetIndex(String workbookName, String sheetName) {
		sheetName = normalizeSheetName(sheetName);
		//ZSS-747
		synchronized(_indexes) {
			Integer index = _indexes.sheetName2index.get(toKey(workbookName, sheetName));
			return index != null ? index : -1;
		}
	}

	private String toKey(String... strings) {
		return Arrays.toString(strings);
	}

	@Override
	public SpreadsheetVersion getSpreadsheetVersion() {
		// TODO zss 3.5
		return SpreadsheetVersion.EXCEL2007;
	}

	@Override
	public String getBookNameFromExternalLinkIndex(String externalLinkIndex) {

		try {
			// if external link index is really a index, convert it and find name from records
			int index = Integer.parseInt(externalLinkIndex) - 1; // zero based
			List<?> names = (List<?>)book.getAttribute(FormulaEngine.KEY_EXTERNAL_BOOK_NAMES);
			if(names != null) {
				return names.get(index).toString();
			}
		} catch(NumberFormatException e) {
			// do nothing
		} catch(IndexOutOfBoundsException e) {
			logger.warning(e.getMessage(), e);
		}

		// otherwise, it should be a book name already and just return itself.
		return externalLinkIndex;
	}

	@Override
	public EvaluationName getOrCreateName(String name, int sheetIndex) {
		String key = toKey(String.valueOf(sheetIndex), name);
		//ZSS-747
		synchronized (_indexes) {
			Integer index = _indexes.name2index.get(key);
			if(index == null) {
				index = _indexes.index2name.size();
				_indexes.index2name.add(new Object[] {Integer.valueOf(sheetIndex), name});
				_indexes.name2index.put(key, index);
			}
			EvaluationName n = new SimpleName(name, index, sheetIndex);
			return n;
		}
	}
	
	/* FormulaRenderingWorkbook */

	@Override
	public String getNameText(NamePtg namePtg) {
		return (String) getNameInfo(namePtg)[1];
	}
	
	@Override
	public String getFullNameText(NamePtg namePtg) {
		Object[] info = getNameInfo(namePtg);
		String nameName = (String) info[1];
		int sheetIndex = ((Integer)info[0]).intValue();
		return sheetIndex < 0 ? nameName : (book.getSheet(sheetIndex).getSheetName() + '!' + nameName);
	}
	
	public Object[] getNameInfo(NamePtg namePtg) {
		//ZSS-747
		synchronized (_indexes) {
			return _indexes.index2name.get(namePtg.getIndex());
		}
	}
	
	@Override
	public String resolveNameXText(NameXPtg nameXPtg) {
		return (String) resolveNameXInfo(nameXPtg)[1];
	}
	
	public Object[] resolveNameXInfo(NameXPtg nameXPtg) {
		//ZSS-747
		synchronized (_indexes) {
			return _indexes.index2name.get(nameXPtg.getNameIndex());
		}
	}
	
	/**
	 * @return internal or external sheet.
	 */
	public ExternalSheet getAnyExternalSheet(int externSheetIndex) {
		//ZSS-747
		synchronized (_indexes) {
			return _indexes.index2sheet.get(externSheetIndex);
		}
	}
	
	@Override
	public ExternalSheet getExternalSheet(int externSheetIndex) {
		// return external sheet object if only if the sheet is exact external
		ExternalSheet externalSheet = getAnyExternalSheet(externSheetIndex);
		return externalSheet.getWorkbookName() != null ? externalSheet : null;
	}
	
	@Override
	public String getSheetNameByExternSheet(int externSheetIndex) {
		// get sheet no matter external or internal, and covert to 3D ref. if any
		ExternalSheet sheet = getAnyExternalSheet(externSheetIndex);
		String name = sheet.getSheetName();
		String lastName = sheet.getLastSheetName();
		return name.equals(lastName) ? name : name + ":" + lastName;
	}

	@Override
	public String getExternalLinkIndexFromBookName(String bookname) {
		return bookname;
	}
	
	/**
	 * rename a sheet in this parsing book directly.
	 * if it can't find a sheet with old name, it won't create a sheet for the new name.
	 */
	public void renameSheet(String bookName, String oldName, String newName) {
		
		// null as current book
		if(book.getBookName().equals(bookName)) {
			bookName = null;
		}
		
		// check every external sheet data and rename sheet if necessary
		// rename as replacing by new external sheets (Note, the index should not be changed)
		//ZSS-747
		synchronized (_indexes) {
			Map<String, Integer> sheetName2index = _indexes.sheetName2index;
			List<ExternalSheet> index2sheet = _indexes.index2sheet;

			List<ExternalSheet> temp = new ArrayList<ExternalSheet>(index2sheet.size()); 
			for(ExternalSheet extSheet : index2sheet) {
				if((bookName == null && extSheet.getWorkbookName() == null)
					|| (bookName != null && bookName.equals(extSheet.getWorkbookName()))) {
					String sheet1 = oldName.equals(extSheet.getSheetName()) ? newName : extSheet.getSheetName();
					String sheet2 = oldName.equals(extSheet.getLastSheetName()) ? newName : extSheet.getLastSheetName();
					temp.add(new ExternalSheet(extSheet.getWorkbookName(), sheet1, sheet2));
				} else {
					temp.add(extSheet);
				}
			}
			_indexes.index2sheet = temp;
		
			// clear the map of external sheet name to index and rebuild it
			sheetName2index.clear();
			for(int i = 0, len = temp.size(); i < len; ++i) {
				ExternalSheet esheet = temp.get(i); 
				String book = esheet.getWorkbookName();
				String sheet1 = esheet.getSheetName();
				String sheet2 = esheet.getLastSheetName();
				String key = toKey(book, !sheet1.equalsIgnoreCase(sheet2) ? (sheet1 + ":" + sheet2) : sheet1);
				sheetName2index.put(key, i);
			}
		}
	}

	/**
	 * name to represent named range
	 * @author Pao
	 */
	private class SimpleName implements EvaluationName {

		private final String name;
		private final int nameIndex;
		private int sheetIndex;

		/**
		 * @param sheetIndex sheet index; if -1, indicates whole book.
		 */
		public SimpleName(String name, int nameIndex, int sheetIndex) {
			this.name = name;
			this.nameIndex = nameIndex;
			this.sheetIndex = sheetIndex;
		}

		public Ptg[] getNameDefinition() {
			return FormulaParser.parse(name, ParsingBook.this, FormulaType.NAMEDRANGE, sheetIndex);
		}

		public String getNameText() {
			return name;
		}

		public boolean hasFormula() {
			return false;
		}

		public boolean isFunctionName() {
			return false;
		}

		public boolean isRange() {
			return true;//false;
		}

		public NamePtg createPtg() {
			return new NamePtg(nameIndex);
		}
	}
	
	//ZSS-796
	/**
	 * table name to represent named range
	 * @since 3.6.0
	 */
	private class TableName implements EvaluationName {

		private final String tableName;
		private final String columnName;
		private final int nameIndex;
		private final boolean inTable;

		/**
		 * @param sheetIndex sheet index; if -1, indicates whole book.
		 */
		public TableName(String tableName, int nameIndex, String columnName, boolean inTable) {
			this.tableName = tableName;
			this.nameIndex = nameIndex;
			this.columnName = columnName;
			this.inTable = inTable;
		}

		public Ptg[] getNameDefinition() {
			return FormulaParser.parse(tableName, ParsingBook.this, FormulaType.NAMEDRANGE, -1); //  ?FormulaType.ARRAY
		}

		public String getNameText() {
			return tableName;
		}

		public boolean hasFormula() {
			return false;
		}

		public boolean isFunctionName() {
			return true;
		}

		public boolean isRange() {
			return true;
		}

		public TableNamePtg createPtg() {
			return new TableNamePtg(nameIndex, tableName, columnName, inTable);
		}
	}

	
	//to compatible with zss-575 in 3.0
	@Override
	public boolean isAllowedDeferredNamePtg() {
		return true;
	}
	
	//ZSS-747
	private static class SheetIndexes implements Serializable {
		private static final long serialVersionUID = 1L;
		// defined names
		private List<Object[]> index2name = new ArrayList<Object[]>(); // [0]: sheetIndex, [1]: nameName  
		private Map<String, Integer> name2index = new HashMap<String, Integer>();
		// sheets
		private List<ExternalSheet> index2sheet = new ArrayList<ExternalSheet>();
		private Map<String, Integer> sheetName2index = new HashMap<String, Integer>(); // the name combine names of book, sheet 1 and sheet 2
	}
	
	//ZSS-790
	@Override
	public EvaluationName getName(String name, String sheetName) {
		int sheetIndex = book.getSheetIndex(sheetName);
		return getName(name, sheetIndex);
	}

	//ZSS-796
	@Override
	public EvaluationName getTableName(String tableName, String columnName, int sheetIndex, int rowIdx, int colIdx) {
		//TODO: find the real table name from book, sheetIdx, rowIdx, and colIdx
		String tableName0 = tableName == null ? "" : tableName;
		String key = toKey(String.valueOf(-1), tableName0);
		//ZSS-747
		synchronized (_indexes) {
			Integer index = _indexes.name2index.get(key);
			if(index == null) {
				index = _indexes.index2name.size();
				_indexes.index2name.add(new Object[] {Integer.valueOf(-1), tableName0});
				_indexes.name2index.put(key, index);
			}
			EvaluationName n = new TableName(tableName0, index, columnName, tableName == null);
			return n;
		}
	}

	//ZSS-820
	/**
	 * reorder a sheet in this parsing book directly.
	 */
	public void reorderSheet(String bookName, int oldIndex, int newIndex) {
		
		// null as current book
		if(book.getBookName().equals(bookName)) {
			bookName = null;
		}
		
		// check every external sheet data and reorder sheet if necessary
		// reorder as replacing by new external sheets (Note, the index should not be changed)
		//ZSS-747
		synchronized (_indexes) {
			final int offset = oldIndex < newIndex ? -1 : 1;
			final int low = oldIndex < newIndex ? oldIndex : newIndex;
			final int high = oldIndex < newIndex ? newIndex : oldIndex;
			
			Map<String, Integer> sheetName2index = _indexes.sheetName2index;
			List<ExternalSheet> index2sheet = _indexes.index2sheet;

			List<ExternalSheet> temp = new ArrayList<ExternalSheet>(index2sheet.size()); 
			for(ExternalSheet extSheet : index2sheet) {
				if((bookName == null && extSheet.getWorkbookName() == null)
					|| (bookName != null && bookName.equals(extSheet.getWorkbookName()))) {
					final int low0 = book.getSheetIndex(extSheet.getSheetName());
					int high0 = book.getSheetIndex(extSheet.getLastSheetName());
					if (high0 < 0) high0 = low0;
					
					// no intersection; as is.
					if (high0 < low || low0 > high) {
						temp.add(extSheet);
						continue; 
					}

					String sheet1 = extSheet.getSheetName();
					String sheet2 = extSheet.getLastSheetName();
					if (low0 == oldIndex) {
						if (low0 != high0 && newIndex >= high0) { //2. move beyond original high end
							//must change low end sheet name! (_map & _remap must be remapped)
							sheet1 = book.getSheet(low0 - offset).getSheetName();
						}
					}
					
					if (high0 == oldIndex) {
						if (low0 != high0 && newIndex <= low0) { //4. move beyond original low end
							// high0 index not change but sheet name changed
							sheet2 = book.getSheet(high0 - offset).getSheetName();
						}
					}
					temp.add(new ExternalSheet(extSheet.getWorkbookName(), sheet1, sheet2));
				} else {
					temp.add(extSheet);
				}
			}
			_indexes.index2sheet = temp;
		
			// clear the map of external sheet name to index and rebuild it
			sheetName2index.clear();
			for(int i = 0, len = temp.size(); i < len; ++i) {
				ExternalSheet esheet = temp.get(i); 
				String book = esheet.getWorkbookName();
				String sheet1 = esheet.getSheetName();
				String sheet2 = esheet.getLastSheetName();
				String key = toKey(book, !sheet1.equalsIgnoreCase(sheet2) ? (sheet1 + ":" + sheet2) : sheet1);
				sheetName2index.put(key, i);
			}
		}
	}
}


