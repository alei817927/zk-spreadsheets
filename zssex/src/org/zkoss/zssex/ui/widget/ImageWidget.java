//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.widget;

import org.zkoss.zk.ui.Component;
import org.zkoss.zss.model.SPicture;
import org.zkoss.zss.model.SSheet;
import org.zkoss.zssex.ui.widget.BaseWidget;
import org.zkoss.zssex.ui.widget.WidgetCtrl;
import org.zkoss.zul.Image;

public class ImageWidget extends BaseWidget {
    Image _imageComp;

    private synchronized Image inner() {
        if(this._imageComp == null) {
            this._imageComp = new Image();
            this._imageComp.setSclass("zswidget-img");
        }

        return this._imageComp;
    }

    public ImageWidget(SSheet sheet, SPicture pic, String panel) {
        super(panel);
        this.setId(pic.getId());
        this.setSizable(true);
        this.setMovable(true);
        this.setCtrlKeys("#del");
    }

    protected Component getInnerComponent() {
        return this.inner();
    }

    public String getSrc() {
        return this.inner().getSrc();
    }

    public void setSrc(String src) {
        this.inner().setSrc(src);
    }

    public org.zkoss.image.Image getContent() {
        return this.inner().getContent();
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

    public void setBorder(String border) {
        this.inner().setBorder(border);
    }

    public String getBorder() {
        return this.inner().getBorder();
    }

    public void setContent(org.zkoss.image.Image image) {
        this.inner().setContent(image);
    }

    protected WidgetCtrl newCtrl() {
        WidgetCtrl ctrl = super.newCtrl();
        return ctrl;
    }

    void setInClient(boolean inClient) {
        super.setInClient(inClient);
        if(inClient) {
            this.inner().setParent(this.getCtrl());
        } else if(!inClient && this._imageComp != null) {
            this._imageComp.setParent((Component)null);
        }

    }

    public void invalidate() {
        this.inner().invalidate();
    }

    public String getWidgetType() {
        return "image";
    }
}
