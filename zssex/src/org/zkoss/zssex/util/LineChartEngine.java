//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.util;

import java.awt.BasicStroke;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.zkoss.zss.model.SChart;
import org.zkoss.zssex.util.ZssChartEngine;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.Chart;

public class LineChartEngine extends ZssChartEngine {
    private static final long serialVersionUID = 201011011508L;
    private static final BasicStroke STROKE = new BasicStroke((float)(new Integer(2)).intValue());

    public LineChartEngine(SChart chartInfo) {
        super(chartInfo);
    }

    public boolean prepareJFreeChart(JFreeChart jfchart, Chart chart) {
        super.prepareJFreeChart(jfchart, chart);
        LineAndShapeRenderer renderer = (LineAndShapeRenderer)((CategoryPlot)jfchart.getPlot()).getRenderer();
        int count = ((CategoryModel)chart.getModel()).getSeries().size();

        for(int j = 0; j < count; ++j) {
            renderer.setSeriesStroke(j, STROKE);
            renderer.setSeriesShapesVisible(j, true);
        }

        return false;
    }
}
