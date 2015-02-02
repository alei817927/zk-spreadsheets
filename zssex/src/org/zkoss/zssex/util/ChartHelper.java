//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.util;

import java.awt.Font;
import java.io.Serializable;
import java.util.Date;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.zkoss.lang.Library;
import org.zkoss.lang.Strings;
import org.zkoss.util.logging.Log;
import org.zkoss.zss.model.SChart;
import org.zkoss.zss.model.SChart.BarDirection;
import org.zkoss.zss.model.SChart.ChartGrouping;
import org.zkoss.zss.model.chart.SChartData;
import org.zkoss.zss.model.chart.SGeneralChartData;
import org.zkoss.zss.model.chart.SSeries;
import org.zkoss.zss.model.sys.EngineFactory;
import org.zkoss.zssex.util.LineChartEngine;
import org.zkoss.zssex.util.LineChartExportEngine;
import org.zkoss.zssex.util.PieChartEngine;
import org.zkoss.zssex.util.PieChartExportEngine;
import org.zkoss.zssex.util.ZssChartEngine;
import org.zkoss.zssex.util.ZssChartExportEngine;
import org.zkoss.zul.Chart;
import org.zkoss.zul.ChartModel;
import org.zkoss.zul.SimpleCategoryModel;
import org.zkoss.zul.SimpleHiLoModel;
import org.zkoss.zul.SimplePieModel;
import org.zkoss.zul.SimpleXYModel;
import org.zkoss.zul.SimpleXYZModel;
import org.zkoss.zul.XYModel;
import org.zkoss.zul.XYZModel;

public class ChartHelper {
    private static final Log logger = Log.lookup(ChartHelper.class.getName());
    static final String TITLE_FONT_PROPERTY_KEY = "org.zkoss.zss.chart.title.font";
    static final String LEGEND_FONT_PROPERTY_KEY = "org.zkoss.zss.chart.legend.font";
    static final String XAXIS_TICK_FONT_PROPERTY_KEY = "org.zkoss.zss.chart.xAxisTick.font";
    static final String YAXIS_TICK_FONT_PROPERTY_KEY = "org.zkoss.zss.chart.yAxisTick.font";

    public ChartHelper() {
    }

    public static Chart createChart(SChart chartInfo) {
        Chart chart = new Chart();
//        String type = getChartType(chartInfo);
//        if("line".equals(type)) {
//            chart.setEngine(new LineChartEngine(chartInfo));
//        } else if(!"pie".equals(type) && !"ring".equals(type)) {
//            chart.setEngine(new ZssChartEngine(chartInfo));
//        } else {
//            chart.setEngine(new PieChartEngine(chartInfo));
//        }

        return chart;
    }

    public static JFreeChart drawJFreeChart(SChart chartInfo) {
        String type = getChartType(chartInfo);
        Object engine;
        if("line".equals(type)) {
            engine = new LineChartExportEngine(chartInfo);
        } else if(!"pie".equals(type) && !"ring".equals(type)) {
            engine = new ZssChartExportEngine(chartInfo);
        } else {
            engine = new PieChartExportEngine(chartInfo);
        }

        return ((ZssChartExportEngine)engine).drawJFreeChart();
    }

    private static void prepareJFreeChart(JFreeChart jfchart, SChart chartInfo) {
    }

    public static String getChartType(SChart chartInfo) {
//        switch(ChartHelper.SyntheticClass_1.$SwitchMap$org$zkoss$zss$model$SChart$ChartType[chartInfo.getType().ordinal()]) {
//            case 1:
//                return chartInfo.getGrouping() == ChartGrouping.STACKED?"stacked_area":"area";
//            case 2:
//            case 3:
//                return chartInfo.getGrouping() == ChartGrouping.STACKED?"stacked_bar":"bar";
//            case 4:
//                return "bubble";
//            case 5:
//                return "ring";
//            case 6:
//                return "line";
//            case 7:
//                return "ofpie";
//            case 8:
//                return "pie";
//            case 9:
//                return "radar";
//            case 10:
//                return "scatter";
//            case 11:
//                return "candlestick";
//            case 12:
//                return "surface";
//            default:
//                return "";
//        }
        return "";
    }

    public static PlotOrientation getOrientation(SChart chartInfo) {
        return PlotOrientation.VERTICAL;
//        if(chartInfo.getBarDirection() == null) {
//            return PlotOrientation.VERTICAL;
//        } else {
//            switch(ChartHelper.SyntheticClass_1.$SwitchMap$org$zkoss$zss$model$SChart$BarDirection[chartInfo.getBarDirection().ordinal()]) {
//                case 1:
//                    return PlotOrientation.HORIZONTAL;
//                default:
//                    return PlotOrientation.VERTICAL;
//            }
//        }
    }

    public static void drawChart(Chart chart, SChart chartInfo) {
//        Object model = null;
//        switch(ChartHelper.SyntheticClass_1.$SwitchMap$org$zkoss$zss$model$SChart$ChartType[chartInfo.getType().ordinal()]) {
//            case 1:
//            case 2:
//            case 3:
//            case 6:
//                model = prepareCategoryModel(chartInfo.getData());
//                break;
//            case 4:
//                model = prepareXYZModel(chartInfo.getData());
//                break;
//            case 5:
//            case 8:
//                model = preparePieModel(chartInfo.getData());
//            case 7:
//            case 9:
//            case 12:
//            default:
//                break;
//            case 10:
//                model = prepareXYModel(chartInfo.getData());
//                break;
//            case 11:
//                model = prepareHiLoModel(chartInfo.getData());
//        }
//
//        if(model != null) {
//            drawChartInner(chart, (ChartModel)model, chartInfo);
//        }

    }

    public static ChartModel prepareHiLoModel(SChartData chartData) {
        if(!(chartData instanceof SGeneralChartData)) {
            return null;
        } else {
            SimpleHiLoModel model = new SimpleHiLoModel();
            SSeries volumn = null;
            SSeries open = null;
            SSeries high = null;
            SSeries low = null;
            SSeries close = null;
            SGeneralChartData catData = (SGeneralChartData)chartData;
            int s = catData.getNumOfSeries();
            if(s <= 0) {
                return model;
            } else {
                if(s > 4) {
                    close = catData.getSeries(4);
                }

                if(s > 3) {
                    low = catData.getSeries(3);
                }

                if(s > 2) {
                    high = catData.getSeries(2);
                }

                if(s > 1) {
                    open = catData.getSeries(1);
                }

                if(s > 0) {
                    volumn = catData.getSeries(0);
                }

                s = Math.max(catData.getNumOfCategory(), volumn.getNumOfValue());
                s = open == null?s:Math.max(volumn.getNumOfValue(), open.getNumOfValue());
                s = high == null?s:Math.max(open.getNumOfValue(), high.getNumOfValue());
                s = low == null?s:Math.max(high.getNumOfValue(), low.getNumOfValue());
                s = close == null?s:Math.max(low.getNumOfValue(), volumn.getNumOfValue());

                for(int i = 0; i < s; ++i) {
                    Number volumnval = toNumber(volumn == null?Integer.valueOf(0):volumn.getValue(i), 0);
                    Number openval = toNumber(open == null?Integer.valueOf(0):open.getValue(i), 0);
                    Number highval = toNumber(high == null?Integer.valueOf(0):high.getValue(i), 0);
                    Number lowval = toNumber(low == null?Integer.valueOf(0):low.getValue(i), 0);
                    Number closeval = toNumber(close == null?Integer.valueOf(0):close.getValue(i), 0);
                    Object cat = catData.getCategory(i);
                    Date d = toDate(cat);
                    if(d != null) {
                        model.addValue(d, openval, highval, lowval, closeval, volumnval);
                    }
                }

                return model;
            }
        }
    }

    private static Date toDate(Object obj) {
        return obj instanceof Date?(Date)obj:(obj instanceof Number?EngineFactory.getInstance().getCalendarUtil().doubleValueToDate(((Number)obj).doubleValue()):null);
    }

    private static Number toNumber(Object obj, int defaultValue) {
        return (Number)(obj instanceof Number?(Number)obj:Integer.valueOf(defaultValue));
    }

    private static String toSeriesName(String name, int seriesIndex) {
        return name != null && !"".equals(name)?name:"Series" + (seriesIndex + 1);
    }

    public static ChartModel prepareCategoryModel(SChartData chartData) {
        if(!(chartData instanceof SGeneralChartData)) {
            return null;
        } else {
            SimpleCategoryModel model = new SimpleCategoryModel();
            SGeneralChartData catData = (SGeneralChartData)chartData;
            SSeries[] serieses = new SSeries[catData.getNumOfSeries()];
            if(serieses.length == 0) {
                return model;
            } else {
                int s;
                for(s = 0; s < serieses.length; ++s) {
                    serieses[s] = catData.getSeries(s);
                }

                s = Math.max(catData.getNumOfCategory(), serieses[0].getNumOfValue());

                int i;
                for(i = 1; i < serieses.length; ++i) {
                    s = Math.max(s, serieses[i].getNumOfValue());
                }

                for(i = 0; i < s; ++i) {
                    Object cat = catData.getCategory(i);

                    for(int j = 0; j < serieses.length; ++j) {
                        String series = toSeriesName(serieses[j].getName(), j);
                        Number val = toNumber(serieses[j].getValue(i), 0);
                        model.setValue(new ChartModelLabel(j, series), new ChartModelLabel(i, cat), val);
                    }
                }

                return model;
            }
        }
    }

    public static XYModel prepareXYModel(SChartData chartData) {
        if(!(chartData instanceof SGeneralChartData)) {
            return null;
        } else {
            SimpleXYModel model = new SimpleXYModel();
            SGeneralChartData catData = (SGeneralChartData)chartData;
            SSeries[] serieses = new SSeries[catData.getNumOfSeries()];
            if(serieses.length == 0) {
                return model;
            } else {
                int i;
                for(i = 0; i < serieses.length; ++i) {
                    serieses[i] = catData.getSeries(i);
                }

                for(i = 0; i < serieses.length; ++i) {
                    String sname = toSeriesName(serieses[i].getName(), i);
                    int s = Math.min(serieses[i].getNumOfValue(), serieses[i].getNumOfYValue());

                    for(int j = 0; j < s; ++j) {
                        Number xval = toNumber(serieses[i].getValue(j), j + 1);
                        Number yval = toNumber(serieses[i].getYValue(j), 0);
                        model.addValue(new ChartModelLabel(i, sname), xval, yval);
                    }
                }

                return model;
            }
        }
    }

    public static XYZModel prepareXYZModel(SChartData chartData) {
        if(!(chartData instanceof SGeneralChartData)) {
            return null;
        } else {
            SimpleXYZModel model = new SimpleXYZModel();
            SGeneralChartData catData = (SGeneralChartData)chartData;
            SSeries[] serieses = new SSeries[catData.getNumOfSeries()];
            if(serieses.length == 0) {
                return model;
            } else {
                int i;
                for(i = 0; i < serieses.length; ++i) {
                    serieses[i] = catData.getSeries(i);
                }

                for(i = 0; i < serieses.length; ++i) {
                    String sname = toSeriesName(serieses[i].getName(), i);
                    int s = Math.min(serieses[i].getNumOfValue(), serieses[i].getNumOfYValue());

                    for(int j = 0; j < s; ++j) {
                        Number xval = toNumber(serieses[i].getValue(j), j + 1);
                        Number yval = toNumber(serieses[i].getYValue(j), 0);
                        Number zval = toNumber(serieses[i].getZValue(j), 0);
                        model.addValue(new ChartModelLabel(i, sname), xval, yval, zval);
                    }
                }

                return model;
            }
        }
    }

    public static ChartModel preparePieModel(SChartData chartData) {
        if(!(chartData instanceof SGeneralChartData)) {
            return null;
        } else {
            SimplePieModel model = new SimplePieModel();
            SGeneralChartData catData = (SGeneralChartData)chartData;
            SSeries series1 = null;
            if(catData.getNumOfSeries() > 0) {
                series1 = catData.getSeries(0);
            }

            if(series1 == null) {
                return model;
            } else {
                int s = Math.max(catData.getNumOfCategory(), series1.getNumOfValue());

                for(int i = 0; i < s; ++i) {
                    Object label = catData.getCategory(i);
                    Object val = series1.getValue(i);
                    model.setValue(new ChartModelLabel(i, label), (Number)(val instanceof Number?(Number)val:Integer.valueOf(0)));
                }

                return model;
            }
        }
    }

    public static Font getCustomFont(String key) {
        String value = Library.getProperty(key);
        Font font = null;
        if(value != null) {
            try {
                String[] x = value.trim().split(",");
                if(x.length == 3) {
                    String weightString = x[1].trim();
                    boolean weight = false;
                    byte weight1;
                    if("bold".equalsIgnoreCase(weightString)) {
                        weight1 = 1;
                    } else if("plain".equalsIgnoreCase(weightString)) {
                        weight1 = 0;
                    } else {
                        if(!"italic".equalsIgnoreCase(weightString)) {
                            throw new Exception("Unsupprted chart font weight: " + weightString);
                        }

                        weight1 = 2;
                    }

                    font = new Font(x[0].trim(), weight1, Integer.parseInt(x[2].trim()));
                }
            } catch (Exception var6) {
                logger.warning(var6.getMessage());
            }
        }

        return font;
    }

    private static void setFont(Chart chart) {
        Font font = getCustomFont("org.zkoss.zss.chart.title.font");
        if(font != null) {
            chart.setTitleFont(font);
        }

        font = getCustomFont("org.zkoss.zss.chart.legend.font");
        if(font != null) {
            chart.setLegendFont(font);
        }

        font = getCustomFont("org.zkoss.zss.chart.xAxisTick.font");
        if(font != null) {
            chart.setXAxisTickFont(font);
        }

        font = getCustomFont("org.zkoss.zss.chart.yAxisTick.font");
        if(font != null) {
            chart.setYAxisTickFont(font);
        }

    }

    private static void drawChartInner(Chart chart, ChartModel model, SChart chartInfo) {
        setFont(chart);
        chart.setType(getChartType(chartInfo));
        chart.setTitle(getChartTitle(chartInfo));
        chart.setThreeD(chartInfo.isThreeD());
        chart.setFgAlpha(128);
        if(chartInfo.getBarDirection() != null) {
            chart.setOrient(chartInfo.getBarDirection() == BarDirection.HORIZONTAL?"horizontal":"vertical");
        }

        chart.setModel(model);
    }

    private static String getChartTitle(SChart chartInfo) {
        String title = chartInfo.getTitle();
        return !Strings.isEmpty(title)?title:"";
    }

    private static String getFirstSeriesTitle(SChart chartInfo) {
//        switch(ChartHelper.SyntheticClass_1.$SwitchMap$org$zkoss$zss$model$SChart$ChartType[chartInfo.getType().ordinal()]) {
//            case 5:
//            case 8:
//                SGeneralChartData cd = (SGeneralChartData)chartInfo.getData();
//                if(cd.getNumOfSeries() > 0) {
//                    return cd.getSeries(0).getName();
//                }
//            default:
//                return "";
//        }
        return "";
    }

    static class ChartModelLabel implements Serializable, Comparable<Integer> {
        private static final long serialVersionUID = 1L;
        private final Integer index;
        private final Object label;

        ChartModelLabel(int index, Object label) {
            this.index = Integer.valueOf(index);
            this.label = label;
        }

        public String toString() {
            return this.label == null?"":this.label.toString();
        }

        public int compareTo(Integer o) {
            return this.index.compareTo(o);
        }

        public int hashCode() {
            boolean prime = true;
            byte result = 1;
            int result1 = 31 * result + (this.index == null?0:this.index.hashCode());
            return result1;
        }

        public boolean equals(Object obj) {
            if(this == obj) {
                return true;
            } else if(obj == null) {
                return false;
            } else if(this.getClass() != obj.getClass()) {
                return false;
            } else {
                ChartModelLabel other = (ChartModelLabel)obj;
                if(this.index == null) {
                    if(other.index != null) {
                        return false;
                    }
                } else if(!this.index.equals(other.index)) {
                    return false;
                }

                return true;
            }
        }
    }
}
