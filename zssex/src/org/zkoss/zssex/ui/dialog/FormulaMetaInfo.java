//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.dialog;

public class FormulaMetaInfo {
    private String category;
    private String function;
    private String expression;
    private String description;
    private int requiredParameter;
    private String multipleParameter;
    private int rowIndex;
    private int colIndex;

    public FormulaMetaInfo(String category, String function, String expression, String description, int requiredParameter, String multipleParameterName) {
        this.category = category;
        this.function = function;
        this.expression = expression;
        this.description = description;
        this.requiredParameter = requiredParameter;
        this.multipleParameter = multipleParameterName;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFunction() {
        return this.function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRequiredParameter() {
        return this.requiredParameter;
    }

    public void setRequiredParameter(int requiredParameter) {
        this.requiredParameter = requiredParameter;
    }

    public boolean isMultipleParameter() {
        return this.multipleParameter != null;
    }

    public void setMultipleParameter(String multipleParameterName) {
        this.multipleParameter = multipleParameterName;
    }

    public String getMultipleParameter() {
        return this.multipleParameter;
    }

    public String[] getParameterNames() {
        String arg = this.expression.substring(this.expression.indexOf("(") + 1, this.expression.lastIndexOf(")"));
        String[] args = arg.split(",");

        for(int i = 0; i < args.length; ++i) {
            args[i] = args[i].trim();
        }

        return args;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColIndex() {
        return this.colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }
}
