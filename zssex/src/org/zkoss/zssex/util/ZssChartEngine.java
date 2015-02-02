//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.util;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.ui.RectangleEdge;
import org.zkoss.zss.model.SChart;
import org.zkoss.zul.Chart;

public class ZssChartEngine{
    protected final SChart _chartInfo;
    private static final long serialVersionUID = 201011021111L;
    static final int POS_B = 1;
    static final int POS_TR = 2;
    static final int POS_L = 3;
    static final int POS_R = 4;
    static final int POS_T = 5;

    public ZssChartEngine(SChart chartInfo) {
        this._chartInfo = chartInfo;
    }

    public boolean prepareJFreeChart(JFreeChart jfchart, Chart chart) {
        chart.setPaneColor("#FFFFFF");
        jfchart.getPlot().setOutlineVisible(false);
        jfchart.getLegend().setFrame(BlockBorder.NONE);
        if(this.hasLegend()) {
            RectangleEdge edge = null;
            switch(this.getLegendPos()) {
                case 1:
                    edge = RectangleEdge.BOTTOM;
                    break;
                case 2:
                case 4:
                default:
                    edge = RectangleEdge.RIGHT;
                    break;
                case 3:
                    edge = RectangleEdge.LEFT;
                    break;
                case 5:
                    edge = RectangleEdge.TOP;
            }

            jfchart.getLegend().setPosition(edge);
        } else {
            jfchart.getLegend().setVisible(false);
        }

        return false;
    }

    private boolean hasLegend() {
        return this._chartInfo.getLegendPosition() != null;
    }

    private int getLegendPos() {
        switch(this._chartInfo.getLegendPosition().ordinal()) {
            case 1:
                return 1;
            case 2:
                return 3;
            case 3:
                return 5;
            case 4:
                return 2;
            case 5:
            default:
                return 4;
        }
    }
}
