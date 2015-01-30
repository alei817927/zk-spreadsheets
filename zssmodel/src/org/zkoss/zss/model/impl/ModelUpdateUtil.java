/*

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		
}}IS_NOTE

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.zss.model.impl;

import java.util.Set;

import org.zkoss.zss.model.CellRegion;
import org.zkoss.zss.model.SBookSeries;
import org.zkoss.zss.model.SCell;
import org.zkoss.zss.model.SSheet;
import org.zkoss.zss.model.sys.dependency.DependencyTable;
import org.zkoss.zss.model.sys.dependency.Ref;
import org.zkoss.zss.range.impl.ModelUpdateCollector;

/**
 * 
 * @author Dennis
 * @since 3.5.0
 */
/*package*/ class ModelUpdateUtil {

	
	/*package*/ static void handlePrecedentUpdate(SBookSeries bookSeries, Ref precedent){
		//clear formula cache (that reval the unexisted sheet before
		FormulaCacheCleaner clearer = FormulaCacheCleaner.getCurrent();
		ModelUpdateCollector collector = ModelUpdateCollector.getCurrent();
		Set<Ref> dependents = null; 
		//get table when collector and clearer is not ignored (in import case, we should ignore clear cahche)
		if(collector!=null || clearer!=null || bookSeries.isAutoFormulaCacheClean()){
			DependencyTable table = ((AbstractBookSeriesAdv)bookSeries).getDependencyTable();
			dependents = table.getEvaluatedDependents(precedent); 
		}
		addRefUpdate(precedent);
		if(dependents!=null && dependents.size()>0){
			if(clearer!=null){
				clearer.clear(dependents);
			}else if(bookSeries.isAutoFormulaCacheClean()){
				new FormulaCacheClearHelper(bookSeries).clear(dependents);
			}
			if(collector!=null){
				collector.addRefs(dependents);
			}
		}
	}

	/*package*/ static void addRefUpdate(Ref ref) {
		ModelUpdateCollector collector = ModelUpdateCollector.getCurrent();
		if(collector!=null){
			collector.addRef(ref);
		}
	}
	/*package*/ static void addCellUpdate(SSheet sheet,SCell cell){
		addCellUpdate(sheet,cell.getRowIndex(),cell.getColumnIndex());
	}
	/*package*/ static void addCellUpdate(SSheet sheet,int row,int column){
		addCellUpdate(sheet,row,column,row,column);
	}
	/*package*/ static void addCellUpdate(SSheet sheet,int row,int column, int lastRow, int lastColumn){
		ModelUpdateCollector collector = ModelUpdateCollector.getCurrent();
		if(collector!=null){
			collector.addCellUpdate(sheet,row, column,lastRow,lastColumn);
		}
	}
	
	/*package*/ static void addMergeUpdate(SSheet sheet,CellRegion original,CellRegion changeTo){
		ModelUpdateCollector collector = ModelUpdateCollector.getCurrent();
		if(collector!=null){
			collector.addMergeChange(sheet,original,changeTo);
		}
	}

	/*package*/static void addInsertDeleteUpdate(SSheet sheet, boolean inserted, boolean isRow, int index, int lastIndex) {
		ModelUpdateCollector collector = ModelUpdateCollector.getCurrent();
		if(collector != null) {
			collector.addInsertDeleteUpdate(sheet, inserted, isRow, index, lastIndex);
		}
	}
}
