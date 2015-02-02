//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.util;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.zkoss.zss.model.SChart;
import org.zkoss.zssex.util.ZssChartEngine;
import org.zkoss.zul.Chart;

public class PieChartEngine extends ZssChartEngine {
    private static final long serialVersionUID = 201011011426L;

    public PieChartEngine(SChart chartInfo) {
        super(chartInfo);
    }

    public boolean prepareJFreeChart(JFreeChart jfchart, Chart chart) {
        super.prepareJFreeChart(jfchart, chart);
        PiePlot plot = (PiePlot)jfchart.getPlot();
        plot.setLabelGenerator((PieSectionLabelGenerator)null);
        return false;
    }
}
