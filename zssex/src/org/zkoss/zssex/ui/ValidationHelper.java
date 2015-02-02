//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException.Aide;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.api.model.impl.SheetImpl;
import org.zkoss.zss.model.SDataValidation;
import org.zkoss.zss.model.SDataValidation.ErrorStyle;
import org.zkoss.zss.range.SRange;
import org.zkoss.zss.range.SRanges;
import org.zkoss.zul.Messagebox;

public class ValidationHelper {
    public ValidationHelper() {
    }

    private boolean isEventThreadEnabled() {
        return Executions.getCurrent().getDesktop().getWebApp().getConfiguration().isEventThreadEnabled();
    }

    public boolean validate(Sheet sheet, int row, int col, String editText, final EventListener callback) {
        SRange rng = SRanges.range(((SheetImpl)sheet).getNative(), row, col);
        SDataValidation dv = rng.validate(editText);
        if(dv == null) {
            return true;
        } else {
            if(dv.isShowErrorBox() && callback != null) {
                String errTitle = dv.getErrorBoxTitle();
                String errText = dv.getErrorBoxText();
                if(errTitle == null) {
                    errTitle = Labels.getLabel("zss.validation.warn_title");
                }

                if(errText == null) {
                    errText = Labels.getLabel("zss.validation.msg.invalid_value");
                }

                ErrorStyle errStyle = dv.getErrorStyle();
                int btn;
                switch(errStyle.ordinal()) {
                    case 1:
                        Messagebox.show(errText, errTitle, 514, "z-messagebox-icon z-messagebox-error", 512, new EventListener() {
                            public void onEvent(Event event) throws Exception {
                                String evtname = event.getName();
                                if("onRetry".equals(evtname)) {
                                    ValidationHelper.this.retry(callback);
                                } else if("onCancel".equals(evtname)) {
                                    ValidationHelper.this.cancel(callback);
                                }

                            }
                        });
                        break;
                    case 2:
                        errText = errText + "\n\nContinue?";
                        btn = Messagebox.show(errText, errTitle, 50, "z-messagebox-icon z-messagebox-exclamation", 32, new EventListener() {
                            public void onEvent(Event event) throws Exception {
                                String evtname = event.getName();
                                if("onNo".equals(evtname)) {
                                    ValidationHelper.this.retry(callback);
                                } else if("onCancel".equals(evtname)) {
                                    ValidationHelper.this.cancel(callback);
                                } else if("onYes".equals(evtname)) {
                                    ValidationHelper.this.ok(callback);
                                }

                            }
                        });
                        if(this.isEventThreadEnabled() && btn == 16) {
                            return true;
                        }
                        break;
                    case 3:
                        btn = Messagebox.show(errText, errTitle, 3, "z-messagebox-icon z-messagebox-information", 1, new EventListener() {
                            public void onEvent(Event event) throws Exception {
                                String evtname = event.getName();
                                if("onCancel".equals(evtname)) {
                                    ValidationHelper.this.cancel(callback);
                                } else if("onOK".equals(evtname)) {
                                    ValidationHelper.this.ok(callback);
                                }

                            }
                        });
                        if(this.isEventThreadEnabled() && btn == 1) {
                            return true;
                        }
                }
            } else if(callback != null) {
                this.cancel(callback);
            }

            return false;
        }
    }

    private void errorBoxCallback(EventListener callback, String eventname) {
        if(!this.isEventThreadEnabled() && callback != null) {
            try {
                callback.onEvent(new Event(eventname, (Component)null));
            } catch (Exception var4) {
                throw Aide.wrap(var4);
            }
        }

    }

    private void ok(EventListener callback) {
        this.errorBoxCallback(callback, "onOK");
    }

    private void retry(EventListener callback) {
        this.errorBoxCallback(callback, "onRetry");
    }

    private void cancel(EventListener callback) {
        this.errorBoxCallback(callback, "onCancel");
    }
}
