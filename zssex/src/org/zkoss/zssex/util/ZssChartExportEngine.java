//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.util;

import java.awt.Color;
import java.awt.Font;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.RectangleEdge;
import org.zkoss.zss.model.SChart;
import org.zkoss.zssex.util.ChartHelper;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.ChartModel;
import org.zkoss.zul.HiLoModel;
import org.zkoss.zul.PieModel;
import org.zkoss.zul.XYModel;
import org.zkoss.zul.XYZModel;

public class ZssChartExportEngine {
    protected final SChart _chartInfo;
    private static final String DEFAULT_HI_LO_SERIES = "High Low Data";
    static final int POS_B = 1;
    static final int POS_TR = 2;
    static final int POS_L = 3;
    static final int POS_R = 4;
    static final int POS_T = 5;

    public ZssChartExportEngine(SChart chartInfo) {
        this._chartInfo = chartInfo;
    }

    public JFreeChart drawJFreeChart() {
        if(this._chartInfo == null) {
            return null;
        } else {
            JFreeChart jfchart = this.createJFreeChart();
            if(jfchart == null) {
                return null;
            } else {
                if(!this.prepareJFreeChart(jfchart)) {
                    short fgAlpha = 128;
                    int[] bgRGB = new int[]{255, 255, 255};
                    short bgAlpha = 255;
                    Plot plot = jfchart.getPlot();
                    float alpha = (float)fgAlpha / 255.0F;
                    plot.setForegroundAlpha(alpha);
                    alpha = (float)bgAlpha / 255.0F;
                    plot.setBackgroundAlpha(alpha);
                    plot.setBackgroundPaint(new Color(bgRGB[0], bgRGB[1], bgRGB[2], bgAlpha));
                    jfchart.setBackgroundPaint(Color.WHITE);
                    Font tfont = ChartHelper.getCustomFont("org.zkoss.zss.chart.title.font");
                    if(tfont != null) {
                        jfchart.getTitle().setFont(tfont);
                    }

                    Font lfont = ChartHelper.getCustomFont("org.zkoss.zss.chart.legend.font");
                    if(lfont != null) {
                        jfchart.getLegend().setItemFont(lfont);
                    }

                    Font xlbfont;
                    if(plot instanceof PiePlot) {
                        PiePlot pplot = (PiePlot)plot;
                        xlbfont = ChartHelper.getCustomFont("org.zkoss.zss.chart.xAxisTick.font");
                        if(xlbfont != null) {
                            pplot.setLabelFont(xlbfont);
                        }
                    } else {
                        Font xtkfont;
                        Font ylbfont;
                        Font ytkfont;
                        if(plot instanceof CategoryPlot) {
                            CategoryPlot pplot1 = (CategoryPlot)plot;
                            pplot1.setRangeGridlinePaint(new Color(192, 192, 192));
                            xlbfont = ChartHelper.getCustomFont("org.zkoss.zss.chart.xAxisTick.font");
                            xtkfont = ChartHelper.getCustomFont("org.zkoss.zss.chart.xAxisTick.font");
                            if(xlbfont != null) {
                                pplot1.getDomainAxis().setLabelFont(xlbfont);
                            }

                            if(xtkfont != null) {
                                pplot1.getDomainAxis().setTickLabelFont(xtkfont);
                            }

                            ylbfont = ChartHelper.getCustomFont("org.zkoss.zss.chart.yAxisTick.font");
                            ytkfont = ChartHelper.getCustomFont("org.zkoss.zss.chart.yAxisTick.font");
                            if(ylbfont != null) {
                                pplot1.getRangeAxis().setLabelFont(ylbfont);
                            }

                            if(ytkfont != null) {
                                pplot1.getRangeAxis().setTickLabelFont(ytkfont);
                            }
                        } else if(plot instanceof XYPlot) {
                            XYPlot pplot2 = (XYPlot)plot;
                            pplot2.setRangeGridlinePaint(Color.LIGHT_GRAY);
                            pplot2.setDomainGridlinePaint(Color.LIGHT_GRAY);
                            xlbfont = ChartHelper.getCustomFont("org.zkoss.zss.chart.xAxisTick.font");
                            xtkfont = ChartHelper.getCustomFont("org.zkoss.zss.chart.xAxisTick.font");
                            if(xlbfont != null) {
                                pplot2.getDomainAxis().setLabelFont(xlbfont);
                            }

                            if(xtkfont != null) {
                                pplot2.getDomainAxis().setTickLabelFont(xtkfont);
                            }

                            ylbfont = ChartHelper.getCustomFont("org.zkoss.zss.chart.yAxisTick.font");
                            ytkfont = ChartHelper.getCustomFont("org.zkoss.zss.chart.yAxisTick.font");
                            if(ylbfont != null) {
                                pplot2.getRangeAxis().setLabelFont(ylbfont);
                            }

                            if(ytkfont != null) {
                                pplot2.getRangeAxis().setTickLabelFont(ytkfont);
                            }
                        } else if(plot instanceof PolarPlot) {
                            PolarPlot pplot3 = (PolarPlot)plot;
                            pplot3.setAngleGridlinePaint(Color.LIGHT_GRAY);
                            pplot3.setRadiusGridlinePaint(Color.LIGHT_GRAY);
                            xlbfont = ChartHelper.getCustomFont("org.zkoss.zss.chart.xAxisTick.font");
                            if(xlbfont != null) {
                                pplot3.setAngleLabelFont(xlbfont);
                            }
                        }
                    }
                }

                return jfchart;
            }
        }
    }

    protected boolean prepareJFreeChart(JFreeChart jfchart) {
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

    protected JFreeChart createJFreeChart() {
        JFreeChart jfchart = null;
        String type = ChartHelper.getChartType(this._chartInfo);
        ChartModel model;
        if(type.equals("area")) {
            model = ChartHelper.prepareCategoryModel(this._chartInfo.getData());
            jfchart = ChartFactory.createAreaChart(this._chartInfo.getTitle(), this._chartInfo.getXAxisTitle(), this._chartInfo.getYAxisTitle(), this.CategoryModelToCategoryDataset((CategoryModel)model), ChartHelper.getOrientation(this._chartInfo), true, true, true);
        } else if(type.equals("stacked_area")) {
            model = ChartHelper.prepareCategoryModel(this._chartInfo.getData());
            jfchart = ChartFactory.createStackedAreaChart(this._chartInfo.getTitle(), this._chartInfo.getXAxisTitle(), this._chartInfo.getYAxisTitle(), this.CategoryModelToCategoryDataset((CategoryModel)model), ChartHelper.getOrientation(this._chartInfo), true, true, true);
        } else if(type.equals("stacked_bar")) {
            model = ChartHelper.prepareCategoryModel(this._chartInfo.getData());
            if(this._chartInfo.isThreeD()) {
                jfchart = ChartFactory.createStackedBarChart3D(this._chartInfo.getTitle(), this._chartInfo.getXAxisTitle(), this._chartInfo.getYAxisTitle(), this.CategoryModelToCategoryDataset((CategoryModel)model), ChartHelper.getOrientation(this._chartInfo), true, true, true);
            } else {
                jfchart = ChartFactory.createStackedBarChart(this._chartInfo.getTitle(), this._chartInfo.getXAxisTitle(), this._chartInfo.getYAxisTitle(), this.CategoryModelToCategoryDataset((CategoryModel)model), ChartHelper.getOrientation(this._chartInfo), true, true, true);
            }
        } else if(type.equals("bar")) {
            model = ChartHelper.prepareCategoryModel(this._chartInfo.getData());
            if(this._chartInfo.isThreeD()) {
                jfchart = ChartFactory.createBarChart3D(this._chartInfo.getTitle(), this._chartInfo.getXAxisTitle(), this._chartInfo.getYAxisTitle(), this.CategoryModelToCategoryDataset((CategoryModel)model), ChartHelper.getOrientation(this._chartInfo), true, true, true);
            } else {
                jfchart = ChartFactory.createBarChart(this._chartInfo.getTitle(), this._chartInfo.getXAxisTitle(), this._chartInfo.getYAxisTitle(), this.CategoryModelToCategoryDataset((CategoryModel)model), ChartHelper.getOrientation(this._chartInfo), true, true, true);
            }
        } else if(type.equals("line")) {
            model = ChartHelper.prepareCategoryModel(this._chartInfo.getData());
            if(this._chartInfo.isThreeD()) {
                jfchart = ChartFactory.createLineChart3D(this._chartInfo.getTitle(), this._chartInfo.getXAxisTitle(), this._chartInfo.getYAxisTitle(), this.CategoryModelToCategoryDataset((CategoryModel)model), ChartHelper.getOrientation(this._chartInfo), true, true, true);
            } else {
                jfchart = ChartFactory.createLineChart(this._chartInfo.getTitle(), this._chartInfo.getXAxisTitle(), this._chartInfo.getYAxisTitle(), this.CategoryModelToCategoryDataset((CategoryModel)model), ChartHelper.getOrientation(this._chartInfo), true, true, true);
            }
        } else if(type.equals("pie")) {
            model = ChartHelper.preparePieModel(this._chartInfo.getData());
            if(this._chartInfo.isThreeD()) {
                jfchart = ChartFactory.createPieChart3D(this._chartInfo.getTitle(), this.PieModelToPieDataset((PieModel)model), true, true, true);
            } else {
                jfchart = ChartFactory.createPieChart(this._chartInfo.getTitle(), this.PieModelToPieDataset((PieModel)model), true, true, true);
            }
        } else if(type.equals("scatter")) {
            XYModel model1 = ChartHelper.prepareXYModel(this._chartInfo.getData());
            jfchart = ChartFactory.createScatterPlot(this._chartInfo.getTitle(), this._chartInfo.getXAxisTitle(), this._chartInfo.getYAxisTitle(), this.XYModelToXYDataset((XYModel)model1), ChartHelper.getOrientation(this._chartInfo), true, true, true);
        } else if(!type.equals("candlestick")) {
            if(type.equals("bubble")) {
                XYZModel model2 = ChartHelper.prepareXYZModel(this._chartInfo.getData());
                jfchart = ChartFactory.createBubbleChart(this._chartInfo.getTitle(), this._chartInfo.getXAxisTitle(), this._chartInfo.getYAxisTitle(), this.XYZModelToXYZDataset((XYZModel)model2), ChartHelper.getOrientation(this._chartInfo), true, true, true);
            } else if(type.equals("highlow")) {
                model = ChartHelper.prepareHiLoModel(this._chartInfo.getData());
                jfchart = ChartFactory.createHighLowChart(this._chartInfo.getTitle(), this._chartInfo.getXAxisTitle(), this._chartInfo.getYAxisTitle(), this.HiLoModelToOHLCDataset((HiLoModel)model), true);
            } else if(type.equals("ring")) {
                model = ChartHelper.preparePieModel(this._chartInfo.getData());
                jfchart = ChartFactory.createRingChart(this._chartInfo.getTitle(), this.PieModelToPieDataset((PieModel)model), true, true, true);
            }
        }

        return jfchart;
    }

    protected PieDataset PieModelToPieDataset(PieModel model) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Iterator it = model.getCategories().iterator();

        while(it.hasNext()) {
            Comparable category = (Comparable)it.next();
            Number value = model.getValue(category);
            dataset.setValue(category, value);
        }

        return dataset;
    }

    protected CategoryDataset CategoryModelToCategoryDataset(CategoryModel model) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Iterator it = model.getKeys().iterator();

        while(it.hasNext()) {
            List key = (List)it.next();
            Comparable series = (Comparable)key.get(0);
            Comparable category = (Comparable)key.get(1);
            Number value = model.getValue(series, category);
            dataset.setValue(value, series, category);
        }

        return dataset;
    }

    protected XYDataset XYModelToXYDataset(XYModel model) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        Iterator it = model.getSeries().iterator();

        while(it.hasNext()) {
            Comparable series = (Comparable)it.next();
            XYSeries xyser = new XYSeries(series, model.isAutoSort());
            int size = model.getDataCount(series);

            for(int j = 0; j < size; ++j) {
                xyser.add(model.getX(series, j), model.getY(series, j), false);
            }

            dataset.addSeries(xyser);
        }

        return dataset;
    }

    protected XYZDataset XYZModelToXYZDataset(XYZModel model) {
        DefaultXYZDataset dataset = new DefaultXYZDataset();
        Iterator it = model.getSeries().iterator();

        while(it.hasNext()) {
            Comparable seriesKey = (Comparable)it.next();
            int size = model.getDataCount(seriesKey);
            double[][] data = new double[3][size];

            for(int j = 0; j < size; ++j) {
                data[0][j] = model.getX(seriesKey, j).doubleValue();
                data[1][j] = model.getY(seriesKey, j).doubleValue();
                data[2][j] = model.getZ(seriesKey, j).doubleValue();
            }

            dataset.addSeries(seriesKey, data);
        }

        return dataset;
    }

    protected OHLCDataset HiLoModelToOHLCDataset(HiLoModel model) {
        int size = model.getDataCount();
        OHLCDataItem[] items = new OHLCDataItem[size];

        for(int series = 0; series < size; ++series) {
            Date date = model.getDate(series);
            Number open = model.getOpen(series);
            Number high = model.getHigh(series);
            Number low = model.getLow(series);
            Number close = model.getClose(series);
            Number volume = model.getVolume(series);
            OHLCDataItem item = new OHLCDataItem(date, this.doubleValue(open), this.doubleValue(high), this.doubleValue(low), this.doubleValue(close), this.doubleValue(volume));
            items[series] = item;
        }

        Object var12 = model.getSeries();
        if(var12 == null) {
            var12 = "High Low Data";
        }

        return new DefaultOHLCDataset((Comparable)var12, items);
    }

    protected double doubleValue(Number n) {
        return n == null?0.0D:n.doubleValue();
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
