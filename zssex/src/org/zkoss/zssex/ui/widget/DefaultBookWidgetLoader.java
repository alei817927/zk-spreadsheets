//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.widget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.UiException.Aide;
import org.zkoss.zss.model.SChart;
import org.zkoss.zss.model.SPicture;
import org.zkoss.zss.model.SSheet;
import org.zkoss.zss.model.ViewAnchor;
import org.zkoss.zss.ui.Spreadsheet;
import org.zkoss.zss.ui.Widget;
import org.zkoss.zss.ui.sys.SpreadsheetCtrl;
import org.zkoss.zss.ui.sys.WidgetLoader;
import org.zkoss.zssex.ui.widget.ChartWidget;
import org.zkoss.zssex.ui.widget.ImageWidget;

public class DefaultBookWidgetLoader implements WidgetLoader {
    private static final Log log = Log.lookup(DefaultBookWidgetLoader.class);
    private Spreadsheet _spreadsheet;
    static final int ZINDEX_START = 200;
    Map<String, Map<String, List<Widget>>> _widgetMap = new HashMap();

    public DefaultBookWidgetLoader() {
    }

    public void init(Spreadsheet spreadsheet) {
        this._spreadsheet = spreadsheet;
    }

    public void invalidate() {
    }

    public void onSheetClean(SSheet sheet) {
        String key = sheet.getId();
        Map list = (Map)this._widgetMap.get(key);
        if(list != null) {
            Iterator iter = list.values().iterator();

            while(iter.hasNext()) {
                Iterator i$ = ((List)iter.next()).iterator();

                while(i$.hasNext()) {
                    Widget widget = (Widget)i$.next();
                    ((SpreadsheetCtrl)this._spreadsheet.getExtraCtrl()).removeWidget(widget);
                }
            }

            this._widgetMap.remove(key);
        }

    }

    public void onSheetSelected(SSheet sheet) {
        String key = sheet.getId();
        LinkedHashMap list = new LinkedHashMap();
        this.preparePictureWidgets(sheet, list);
        this.prepareChartWidgets(sheet, list);
        if(list != null && list.size() > 0) {
            this._widgetMap.put(key, list);
        }

    }

    public void onSheetFreeze(SSheet sheet) {
        List pictures = sheet.getPictures();
        if(pictures != null) {
            Iterator charts = pictures.iterator();

            while(charts.hasNext()) {
                SPicture i$ = (SPicture)charts.next();
                this.updatePictureWidget(sheet, i$);
            }
        }

        List charts1 = sheet.getCharts();
        if(charts1 != null) {
            Iterator i$1 = charts1.iterator();

            while(i$1.hasNext()) {
                SChart chart = (SChart)i$1.next();
                this.updateChartWidget(sheet, chart);
            }
        }

    }

    public void onColumnChange(SSheet sheet, int left, int right) {
        List pictures = sheet.getPictures();
        if(pictures != null) {
            Iterator charts = pictures.iterator();

            while(charts.hasNext()) {
                SPicture i$ = (SPicture)charts.next();
                ViewAnchor chartX = i$.getAnchor();
                if(chartX.getColumnIndex() >= left) {
                    this.updatePictureWidget(sheet, i$);
                }
            }
        }

        List charts1 = sheet.getCharts();
        if(charts1 != null) {
            Iterator i$1 = charts1.iterator();

            while(i$1.hasNext()) {
                SChart chartX1 = (SChart)i$1.next();
                ViewAnchor anchor = chartX1.getAnchor();
                if(anchor.getColumnIndex() >= left) {
                    this.updateChartWidget(sheet, chartX1);
                }
            }
        }

    }

    public void onRowChange(SSheet sheet, int top, int bottom) {
        List pictures = sheet.getPictures();
        if(pictures != null) {
            Iterator charts = pictures.iterator();

            while(charts.hasNext()) {
                SPicture i$ = (SPicture)charts.next();
                ViewAnchor chartX = i$.getAnchor();
                if(chartX.getRowIndex() >= top) {
                    this.updatePictureWidget(sheet, i$);
                }
            }
        }

        List charts1 = sheet.getCharts();
        if(charts1 != null) {
            Iterator i$1 = charts1.iterator();

            while(i$1.hasNext()) {
                SChart chartX1 = (SChart)i$1.next();
                ViewAnchor anchor = chartX1.getAnchor();
                if(anchor.getRowIndex() >= top) {
                    this.updateChartWidget(sheet, chartX1);
                }
            }
        }

    }

    private void preparePictureWidgets(SSheet sheet, Map<String, List<Widget>> widgets) {
        List pictures = sheet.getPictures();
        if(pictures != null && pictures.size() != 0) {
            int zindex = 200 + widgets.size();
            Iterator i$ = pictures.iterator();

            while(i$.hasNext()) {
                SPicture picture = (SPicture)i$.next();
                List imagewgts = this.newImageWidgetGroup(sheet, picture, zindex++);
                if(imagewgts != null && imagewgts.size() > 0) {
                    widgets.put(picture.getId(), imagewgts);
                    Iterator i$1 = imagewgts.iterator();

                    while(i$1.hasNext()) {
                        Widget w = (Widget)i$1.next();
                        ((SpreadsheetCtrl)this._spreadsheet.getExtraCtrl()).addWidget(w);
                    }
                }
            }

        }
    }

    private void prepareChartWidgets(SSheet sheet, Map<String, List<Widget>> widgets) {
        List charts = sheet.getCharts();
        if(charts != null && charts.size() != 0) {
            int zindex = 200 + widgets.size();
            Iterator i$ = charts.iterator();

            while(i$.hasNext()) {
                SChart chartX = (SChart)i$.next();

                try {
                    List e = this.newChartWidgetGroup(sheet, chartX, zindex++);
                    if(e != null && e.size() > 0) {
                        widgets.put(chartX.getId(), e);
                        Iterator i$1 = e.iterator();

                        while(i$1.hasNext()) {
                            Widget w = (Widget)i$1.next();
                            ((SpreadsheetCtrl)this._spreadsheet.getExtraCtrl()).addWidget(w);
                        }
                    }
                } catch (IOException var10) {
                    throw Aide.wrap(var10);
                }
            }

        }
    }

    public void addChartWidget(SSheet sheet, SChart chart) {
        String key = sheet.getId();
        Map widgets = (Map)this._widgetMap.get(key);
        if(widgets == null) {
            widgets = new LinkedHashMap();
        }

        int zindex = 200 + ((Map)widgets).size();

        try {
            List e = this.newChartWidgetGroup(sheet, chart, zindex);
            if(e != null && e.size() > 0) {
                ((Map)widgets).put(chart.getId(), e);
                Iterator i$ = e.iterator();

                while(i$.hasNext()) {
                    Widget w = (Widget)i$.next();
                    ((SpreadsheetCtrl)this._spreadsheet.getExtraCtrl()).addWidget(w);
                }
            }
        } catch (IOException var9) {
            throw Aide.wrap(var9);
        }

        if(widgets != null && ((Map)widgets).size() > 0) {
            this._widgetMap.put(key, widgets);
        }

    }

    public void addPictureWidget(SSheet sheet, SPicture picture) {
        String key = sheet.getId();
        Map widgets = (Map)this._widgetMap.get(key);
        if(widgets == null) {
            widgets = new LinkedHashMap();
        }

        int zindex = 200 + ((Map)widgets).size();
        List imagewgts = this.newImageWidgetGroup(sheet, picture, zindex);
        if(imagewgts != null && imagewgts.size() > 0) {
            ((Map)widgets).put(picture.getId(), imagewgts);
            Iterator i$ = imagewgts.iterator();

            while(i$.hasNext()) {
                Widget w = (Widget)i$.next();
                ((SpreadsheetCtrl)this._spreadsheet.getExtraCtrl()).addWidget(w);
            }
        }

        if(widgets != null && ((Map)widgets).size() > 0) {
            this._widgetMap.put(key, widgets);
        }

    }

    public void deletePictureWidget(SSheet sheet, String pictureId) {
        String key = sheet.getId();
        Map widgets = (Map)this._widgetMap.get(key);
        if(widgets != null) {
            List imagewgts = (List)widgets.remove(pictureId);
            if(imagewgts != null) {
                Iterator i$ = imagewgts.iterator();

                while(i$.hasNext()) {
                    Widget w = (Widget)i$.next();
                    ((SpreadsheetCtrl)this._spreadsheet.getExtraCtrl()).removeWidget(w);
                }
            }

        }
    }

    private List<String> getEffectedPanels(SSheet sheet, ViewAnchor anchor) {
        int fr = sheet.getViewInfo().getNumOfRowFreeze();
        int fc = sheet.getViewInfo().getNumOfColumnFreeze();
        ArrayList panels = new ArrayList();
        panels.add("default");
        if(fr > 0 && anchor.getRowIndex() < fr) {
            panels.add("top");
        }

        if(fc > 0 && anchor.getColumnIndex() < fc) {
            panels.add("left");
        }

        if(fc > 0 && fc > 0 && anchor.getRowIndex() < fr && anchor.getColumnIndex() < fc) {
            panels.add("corner");
        }

        return panels;
    }

    public void updatePictureWidget(SSheet sheet, SPicture picture) {
        String key = sheet.getId();
        Map widgets = (Map)this._widgetMap.get(key);
        if(widgets != null) {
            List imagewgts = (List)widgets.get(picture.getId());
            if(imagewgts != null) {
                ViewAnchor anchor = picture.getAnchor();
                Image pimage = null;
                int zindex = 200 + widgets.size();
                List panels = this.getEffectedPanels(sheet, anchor);
                ArrayList newadd = new ArrayList(panels);
                Iterator i$ = (new ArrayList(imagewgts)).iterator();

                while(i$.hasNext()) {
                    Widget p = (Widget)i$.next();
                    if(panels.contains(p.getPanel())) {
                        pimage = ((ImageWidget)p).getContent();
                        this.setupImageWigetAnchor(sheet, (ImageWidget)p, anchor, zindex = p.getZindex());
                        newadd.remove(p.getPanel());
                    } else {
                        imagewgts.remove(p);
                        ((SpreadsheetCtrl)this._spreadsheet.getExtraCtrl()).removeWidget(p);
                    }
                }

                i$ = newadd.iterator();

                while(i$.hasNext()) {
                    String p1 = (String)i$.next();
                    Widget w = this.newImageWidget(sheet, picture, pimage, p1, zindex);
                    imagewgts.add(w);
                    ((SpreadsheetCtrl)this._spreadsheet.getExtraCtrl()).addWidget(w);
                }

            }
        }
    }

    private void setupImageWigetAnchor(SSheet sheet, ImageWidget imagewgt, ViewAnchor anchor, int zindex) {
        if(anchor != null) {
            int row = anchor.getRowIndex();
            int col = anchor.getColumnIndex();
            int height = anchor.getHeight();
            int width = anchor.getWidth();
            int left = anchor.getXOffset();
            int top = anchor.getYOffset();
            imagewgt.setRow(row);
            imagewgt.setColumn(col);
            imagewgt.setZindex(zindex);
            imagewgt.setWidth(width + "px");
            imagewgt.setHeight(height + "px");
            imagewgt.setLeft(left - 1);
            imagewgt.setTop(top - 1);
            imagewgt.adjustLocation();
        }

    }

    private void setupChartWigetAnchor(SSheet sheet, ChartWidget chartwgt, ViewAnchor anchor, int zindex) {
        if(anchor != null) {
            int row = anchor.getRowIndex();
            int col = anchor.getColumnIndex();
            int height = anchor.getHeight();
            int width = anchor.getWidth();
            int left = anchor.getXOffset();
            int top = anchor.getYOffset();
            chartwgt.setRow(row);
            chartwgt.setColumn(col);
            chartwgt.setZindex(zindex);
            chartwgt.setWidth(width + "px");
            chartwgt.setHeight(height + "px");
            chartwgt.setLeft(left - 1);
            chartwgt.setTop(top - 1);
            chartwgt.adjustLocation();
        }

    }

    protected List<Widget> newImageWidgetGroup(SSheet sheet, SPicture picture, int zindex) {
        byte[] picdata = picture.getData();
        if(picdata == null) {
            return null;
        } else {
            ViewAnchor anchor = picture.getAnchor();
            List panels = this.getEffectedPanels(sheet, anchor);
            Image pimage = null;
            ArrayList widgets = new ArrayList(panels.size());

            Widget imagewgt;
            for(Iterator i$ = panels.iterator(); i$.hasNext(); pimage = ((ImageWidget)imagewgt).getContent()) {
                String p = (String)i$.next();
                imagewgt = this.newImageWidget(sheet, picture, pimage, p, zindex);
                widgets.add(imagewgt);
            }

            return widgets;
        }
    }

    protected Widget newImageWidget(SSheet sheet, SPicture picture, Image image, String panel, int zindex) {
        byte[] picdata = picture.getData();
        if(picdata == null && image == null) {
            return null;
        } else {
            ViewAnchor anchor = picture.getAnchor();
            ImageWidget imagewgt = new ImageWidget(sheet, picture, panel);
            Object img = image != null?image:this.getAImage(sheet, picture);
            imagewgt.setContent((Image)img);
            this.setupImageWigetAnchor(sheet, imagewgt, anchor, zindex);
            return imagewgt;
        }
    }

    private AImage getAImage(SSheet sheet, SPicture picture) {
        try {
            String e = picture.getId() + '.' + picture.getFormat().getFileExtension();
            return new AImage(e, picture.getData());
        } catch (IOException var4) {
            log.warning(var4);
            return null;
        }
    }

    protected List<Widget> newChartWidgetGroup(SSheet sheet, SChart chart, int zindex) throws IOException {
        List panels = this.getEffectedPanels(sheet, chart.getAnchor());
        ArrayList widgets = new ArrayList(panels.size());
        Iterator i$ = panels.iterator();

        while(i$.hasNext()) {
            String p = (String)i$.next();
            Widget chartwgt = this.newChartWidget(sheet, chart, p, zindex);
            widgets.add(chartwgt);
        }

        return widgets;
    }

    protected Widget newChartWidget(SSheet sheet, SChart chart, String panel, int zindex) {
        ChartWidget widget = new ChartWidget(sheet, chart, panel, zindex);
        ViewAnchor anchor = chart.getAnchor();
        this.setupChartWigetAnchor(sheet, widget, anchor, zindex);
        return widget;
    }

    public void updateChartWidget(SSheet sheet, SChart chart) {
        String key = sheet.getId();
        Map widgets = (Map)this._widgetMap.get(key);
        if(widgets != null) {
            List chartwgts = (List)widgets.get(chart.getId());
            if(chartwgts != null) {
                ViewAnchor anchor = chart.getAnchor();
                int zindex = 200 + widgets.size();
                List panels = this.getEffectedPanels(sheet, anchor);
                ArrayList newadd = new ArrayList(panels);
                Iterator i$ = (new ArrayList(chartwgts)).iterator();

                while(i$.hasNext()) {
                    Widget p = (Widget)i$.next();
                    if(panels.contains(p.getPanel())) {
                        this.setupChartWigetAnchor(sheet, (ChartWidget)p, anchor, p.getZindex());
                        newadd.remove(p.getPanel());
                    } else {
                        chartwgts.remove(p);
                        ((SpreadsheetCtrl)this._spreadsheet.getExtraCtrl()).removeWidget(p);
                    }
                }

                i$ = newadd.iterator();

                while(i$.hasNext()) {
                    String p1 = (String)i$.next();
                    Widget w = this.newChartWidget(sheet, chart, p1, zindex);
                    chartwgts.add(w);
                    ((SpreadsheetCtrl)this._spreadsheet.getExtraCtrl()).addWidget(w);
                }

            }
        }
    }

    public void deleteChartWidget(SSheet sheet, String chartId) {
        String key = sheet.getId();
        Map widgets = (Map)this._widgetMap.get(key);
        if(widgets != null) {
            List chartwgts = (List)widgets.remove(chartId);
            if(chartwgts != null) {
                Iterator i$ = chartwgts.iterator();

                while(i$.hasNext()) {
                    Widget w = (Widget)i$.next();
                    ((SpreadsheetCtrl)this._spreadsheet.getExtraCtrl()).removeWidget(w);
                }

            }
        }
    }
}
