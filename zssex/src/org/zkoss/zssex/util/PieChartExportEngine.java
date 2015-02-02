//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.util;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.zkoss.zss.model.SChart;
import org.zkoss.zssex.util.ZssChartExportEngine;

public class PieChartExportEngine extends ZssChartExportEngine {
    public PieChartExportEngine(SChart chartInfo) {
        super(chartInfo);
    }

    protected boolean prepareJFreeChart(JFreeChart jfchart) {
        super.prepareJFreeChart(jfchart);
        PiePlot plot = (PiePlot)jfchart.getPlot();
        plot.setLabelGenerator((PieSectionLabelGenerator)null);
        return false;
    }
}
