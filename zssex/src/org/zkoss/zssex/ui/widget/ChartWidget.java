//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.widget;

import org.zkoss.zk.ui.Component;
import org.zkoss.zss.model.SChart;
import org.zkoss.zss.model.SSheet;
import org.zkoss.zssex.ui.widget.BaseWidget;
import org.zkoss.zssex.util.ChartHelper;
import org.zkoss.zul.Chart;

public class ChartWidget extends BaseWidget {
    private SSheet _sheet;
    private int _zindex;
    private SChart _chartInfo;
    private Chart _chartComp;

    private Chart inner() {
        return this._chartComp;
    }

    public ChartWidget(SSheet sheet, SChart chartInfo, String panel, int zindex) {
        super(panel);
        this._sheet = sheet;
        this._chartInfo = chartInfo;
        this._zindex = zindex;
        this._chartComp = this.newChart0();
        if(this._chartComp != null) {
            this.initChart();
        }

        this.setId(this._chartInfo.getId());
        this.setSizable(true);
        this.setMovable(true);
        this.setCtrlKeys("#del");
    }

    protected Component getInnerComponent() {
        return this.inner();
    }

    void setInClient(boolean inClient) {
        super.setInClient(inClient);
        if(inClient) {
            this._chartComp.setParent(this.getCtrl());
        } else {
            this._chartComp.setParent((Component)null);
        }

    }

    public String getType() {
        return this.inner().getType();
    }

    public void setType(String type) {
        this.inner().setType(type);
    }

    public String getTitle() {
        return this.inner().getTitle();
    }

    public void setTitle(String title) {
        this.inner().setTitle(title);
    }

    public boolean isThreeD() {
        return this.inner().isThreeD();
    }

    public void setThreeD(boolean b) {
        this.inner().setThreeD(b);
    }

    public void setWidth(String w) {
        this.inner().setWidth(w);
    }

    public String getWidth() {
        return this.inner().getWidth();
    }

    public void setHeight(String h) {
        this.inner().setHeight(h);
    }

    public String getHeight() {
        return this.inner().getHeight();
    }

    private Chart newChart0() {
        Chart chart = this.createChart(this._chartInfo);
        return chart;
    }

    public void invalidate() {
        if(this._chartComp != null) {
            this.initChart();
        }

    }

    private void initChart() {
        ChartHelper.drawChart(this._chartComp, this._chartInfo);
        this._chartComp.setSclass("zswidget-chart");
    }

    public Chart createChart(SChart chartInfo) {
        return ChartHelper.createChart(chartInfo);
    }

    public String getWidgetType() {
        return "chart";
    }
}
