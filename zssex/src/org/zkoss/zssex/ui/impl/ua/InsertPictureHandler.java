//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.impl.ua;

import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zss.api.AreaRef;
import org.zkoss.zss.api.Range;
import org.zkoss.zss.api.Ranges;
import org.zkoss.zss.api.SheetOperationUtil;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.ui.UserActionContext;
import org.zkoss.zss.ui.impl.ua.AbstractProtectedHandler;
import org.zkoss.zul.Fileupload;

public class InsertPictureHandler extends AbstractProtectedHandler {
    public InsertPictureHandler() {
    }

    protected boolean processAction(final UserActionContext ctx) {
        Sheet sheet = ctx.getSheet();
        AreaRef selection = ctx.getSelection();
        Range range = Ranges.range(sheet, selection.getRow(), selection.getColumn(), selection.getLastRow(), selection.getLastColumn());
        if(range.isProtected()) {
            this.showProtectMessage();
            return true;
        } else {
            Fileupload.get(1, new EventListener<UploadEvent>() {
                public void onEvent(UploadEvent event) throws Exception {
                    Media media = event.getMedia();
                    InsertPictureHandler.this.doInsertPicture(ctx, media);
                }
            });
            return true;
        }
    }

    protected boolean doInsertPicture(UserActionContext ctx, Media media) {
        if(media == null) {
            return true;
        } else if(media instanceof AImage && SheetOperationUtil.getPictureFormat((AImage)media) != null) {
            Sheet sheet = ctx.getSheet();
            AreaRef selection = ctx.getSelection();
            Range range = Ranges.range(sheet, selection.getRow(), selection.getColumn(), selection.getLastRow(), selection.getLastColumn());
            SheetOperationUtil.addPicture(range, (AImage)media);
            ctx.clearClipboard();
            return true;
        } else {
            this.showWarnMessage(Labels.getLabel("zss.actionhandler.msg.cant_support_file", new Object[]{media == null?"":media.getName()}));
            return true;
        }
    }
}
