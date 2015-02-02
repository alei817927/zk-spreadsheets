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
import org.zkoss.zss.model.chart.SGeneralChartData;
import org.zkoss.zssex.util.ZssChartExportEngine;

public class LineChartExportEngine extends ZssChartExportEngine {
    private static final BasicStroke STROKE = new BasicStroke((float)(new Integer(2)).intValue());

    public LineChartExportEngine(SChart chartInfo) {
        super(chartInfo);
    }

    protected boolean prepareJFreeChart(JFreeChart jfchart) {
        super.prepareJFreeChart(jfchart);
        LineAndShapeRenderer renderer = (LineAndShapeRenderer)((CategoryPlot)jfchart.getPlot()).getRenderer();
        int count = ((SGeneralChartData)this._chartInfo.getData()).getNumOfSeries();

        for(int j = 0; j < count; ++j) {
            renderer.setSeriesStroke(j, STROKE);
            renderer.setSeriesShapesVisible(j, true);
        }

        return false;
    }
}
