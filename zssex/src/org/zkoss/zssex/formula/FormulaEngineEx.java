//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.formula;

import java.util.Locale;
import org.zkoss.poi.ss.formula.FormulaParser;
import org.zkoss.poi.ss.formula.FormulaParsingWorkbook;
import org.zkoss.poi.ss.formula.WorkbookEvaluator;
import org.zkoss.poi.ss.formula.eval.ValueEval;
import org.zkoss.poi.ss.formula.ptg.Ptg;
import org.zkoss.poi.ss.usermodel.ZssContext;
import org.zkoss.xel.XelContext;
import org.zkoss.zss.model.SBook;
import org.zkoss.zss.model.SSheet;
import org.zkoss.zss.model.SheetRegion;
import org.zkoss.zss.model.impl.sys.formula.FormulaEngineImpl;
import org.zkoss.zss.model.impl.sys.formula.ParsingBook;
import org.zkoss.zss.model.sys.dependency.Ref;
import org.zkoss.zss.model.sys.dependency.Ref.RefType;
import org.zkoss.zss.model.sys.formula.FormulaEngine;
import org.zkoss.zss.model.sys.formula.FormulaExpression;
import org.zkoss.zss.model.sys.formula.FormulaParseContext;
import org.zkoss.zssex.formula.XelContextHolder;

public class FormulaEngineEx extends FormulaEngineImpl implements FormulaEngine {
    public FormulaEngineEx() {
    }

    protected Object getXelContext() {
        return XelContextHolder.getXelContext();
    }

    protected void setXelContext(Object ctx) {
        XelContextHolder.setXelContext((XelContext)ctx);
    }

    protected String renderFormula(ParsingBook parsingBook, String formula, Ptg[] tokens, boolean always) {
        ZssContext old = ZssContext.getThreadLocal();

        String var7;
        try {
            ZssContext zssContext = old == null?new ZssContext(Locale.US, -1):new ZssContext(Locale.US, old.getTwoDigitYearUpperBound());
            ZssContext.setThreadLocal(zssContext);
            var7 = super.renderFormula(parsingBook, formula, tokens, always);
        } finally {
            ZssContext.setThreadLocal(old);
        }

        return var7;
    }

    protected Ptg[] parse(String formula, FormulaParsingWorkbook book, int sheetIndex, FormulaParseContext context) {
        return FormulaParser.parse(formula, book, 0, sheetIndex, context.getLocale());
    }

    public FormulaExpression movePtgs(FormulaExpression fe, SheetRegion region, int rowOffset, int columnOffset, FormulaParseContext context) {
        FormulaAdjuster shiftAdjuster = this.getMoveAdjuster(region, rowOffset, columnOffset);
        return this.adjustPtgs(fe, context, shiftAdjuster);
    }

    public FormulaExpression shrinkPtgs(FormulaExpression fe, SheetRegion srcRegion, boolean horizontal, FormulaParseContext context) {
        SSheet sheet = srcRegion.getSheet();
        int rowOffset = 0;
        int colOffset = 0;
        SheetRegion neighbor;
        int row;
        int lastRow;
        int col;
        int lastCol;
        if(horizontal) {
            colOffset = -srcRegion.getColumnCount();
            row = srcRegion.getLastColumn() + 1;
            lastRow = sheet.getBook().getMaxColumnIndex();
            col = srcRegion.getRow();
            lastCol = srcRegion.getLastRow();
            neighbor = new SheetRegion(sheet, col, row, lastCol, lastRow);
        } else {
            rowOffset = -srcRegion.getRowCount();
            row = srcRegion.getLastRow() + 1;
            lastRow = sheet.getBook().getMaxRowIndex();
            col = srcRegion.getColumn();
            lastCol = srcRegion.getLastColumn();
            neighbor = new SheetRegion(sheet, row, col, lastRow, lastCol);
        }

        return this.movePtgs(fe, neighbor, rowOffset, colOffset, context);
    }

    public FormulaExpression extendPtgs(FormulaExpression fe, SheetRegion srcRegion, boolean horizontal, FormulaParseContext context) {
        SSheet sheet = srcRegion.getSheet();
        int rowOffset = 0;
        int colOffset = 0;
        SheetRegion neighbor;
        int row;
        int lastRow;
        int col;
        int lastCol;
        if(horizontal) {
            colOffset = srcRegion.getColumnCount();
            row = srcRegion.getColumn();
            lastRow = sheet.getBook().getMaxColumnIndex();
            col = srcRegion.getRow();
            lastCol = srcRegion.getLastRow();
            neighbor = new SheetRegion(sheet, col, row, lastCol, lastRow);
        } else {
            rowOffset = srcRegion.getRowCount();
            row = srcRegion.getRow();
            lastRow = sheet.getBook().getMaxRowIndex();
            col = srcRegion.getColumn();
            lastCol = srcRegion.getLastColumn();
            neighbor = new SheetRegion(sheet, row, col, lastRow, lastCol);
        }

        return this.movePtgs(fe, neighbor, rowOffset, colOffset, context);
    }

    public FormulaExpression shiftPtgs(FormulaExpression fe, int rowOffset, int columnOffset, FormulaParseContext context) {
        FormulaAdjuster shiftAdjuster = this.getShiftAdjuster(rowOffset, columnOffset);
        return this.adjustPtgs(fe, context, shiftAdjuster);
    }

    public FormulaExpression transposePtgs(FormulaExpression fe, int rowOrigin, int columnOrigin, FormulaParseContext context) {
        FormulaAdjuster shiftAdjuster = this.getTransposeAdjuster(rowOrigin, columnOrigin);
        return this.adjustPtgs(fe, context, shiftAdjuster);
    }

    public FormulaExpression renameSheetPtgs(FormulaExpression fe, SBook targetBook, String oldSheetName, String newSheetName, FormulaParseContext context) {
        FormulaAdjuster shiftAdjuster = this.getRenameSheetAdjuster(targetBook, oldSheetName, newSheetName);
        return this.adjustPtgs(fe, context, shiftAdjuster);
    }

    public FormulaExpression renameNamePtgs(FormulaExpression fe, SBook targetBook, int sheetIndex, String oldName, String newName, FormulaParseContext context) {
        FormulaAdjuster shiftAdjuster = this.getRenameNameAdjuster(sheetIndex, oldName, newName);
        return this.adjustPtgs(fe, context, shiftAdjuster);
    }

    private FormulaExpression adjustPtgs(FormulaExpression fe, FormulaParseContext context, FormulaAdjuster adjuster) {
        if(fe.hasError()) {
            return fe;
        } else {
            SBook book = context.getBook();
            ParsingBook parsingBook = new ParsingBook(book);
            String sheetName = context.getSheetName();
            int contextSheetIndex = parsingBook.getExternalSheetIndex((String)null, sheetName);
            Ptg[] tokens = fe.getPtgs();
            boolean modified = adjuster.process(contextSheetIndex, tokens, parsingBook, context);
            //TODO
            Ref singleRef = tokens.length == 1?this.toDenpendRef(context, parsingBook, tokens[0],0):null;
            Ref[] refs = singleRef == null?null:(singleRef.getType() != RefType.AREA && singleRef.getType() != RefType.CELL?null:new Ref[]{singleRef});
            if(modified) {
                String formula = this.renderFormula(parsingBook, (String)null, tokens, true);
                return new FormulaExpressionImpl(formula, tokens, refs, false, (String)null, fe.isMultipleAreaFormula());
            } else {
                return fe;
            }
        }
    }

    protected ValueEval evaluateFormulaExpression(WorkbookEvaluator evaluator, int sheetIndex, FormulaExpression expr, boolean ignoreDereference) {
        //TODO
        return evaluator.evaluatePtgs(sheetIndex, expr.getPtgs(), ignoreDereference,null);
    }
}
