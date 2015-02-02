//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.dialog;

import java.util.Map;
import org.zkoss.lang.Strings;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zss.api.model.Hyperlink.HyperlinkType;
import org.zkoss.zssex.ui.dialog.impl.DialogCallbackEvent;
import org.zkoss.zssex.ui.dialog.impl.DialogCtrlBase;
import org.zkoss.zssex.ui.dialog.impl.DialogCtrlBase.Entry;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class InsertHyperlinkCtrl extends DialogCtrlBase {
    private static final String URI = "~./zssex/dlg/insertHyperlink.zul";
    public static final String ARG_DISPLAY = "headerSize";
    public static final String ARG_LINKTYPE = "linktype";
    public static final String ARG_ADDRESS = "address";
    @Wire
    private Button webBtn;
    @Wire
    private Button mailBtn;
    @Wire
    private Textbox displayBox;
    @Wire
    private Component webContent;
    @Wire
    private Component mailContent;
    private static final String WEBLINK_CONTENT_URI = "~./zssex/dlg/hyperlink/webLink.zul";
    private static final String MAILLINK_CONTENT_URI = "~./zssex/dlg/hyperlink/mailLink.zul";
    private String linkDisplay;
    private String linkAddress;
    private HyperlinkType linkType;
    private boolean autoDisplay = true;

    public InsertHyperlinkCtrl() {
    }

    public static void show(EventListener<DialogCallbackEvent> callback, HyperlinkType type, String address, String display) {
        Map arg = newArg(callback);
        arg.put("headerSize", display);
        arg.put("address", address);
        arg.put("linktype", type);
        Window comp = (Window)Executions.createComponents("~./zssex/dlg/insertHyperlink.zul", (Component)null, arg);
        comp.doModal();
    }

    public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);
        Map arg = Executions.getCurrent().getArg();
        this.linkDisplay = (String)arg.get("headerSize");
        this.linkAddress = (String)arg.get("address");
        this.linkType = (HyperlinkType)arg.get("linktype");
        if(!Strings.isBlank(this.linkDisplay)) {
            this.displayBox.setValue(this.linkDisplay);
            this.autoDisplay = false;
        }

        if(this.linkType == HyperlinkType.EMAIL) {
            this.setLinkType(HyperlinkType.EMAIL);
        } else {
            this.setLinkType(HyperlinkType.URL);
        }

    }

    @Listen("onChange=#displayBox")
    public void onChange$displayBox() {
        this.autoDisplay = false;
    }

    @Listen("onClick=#webBtn")
    public void onClick$webBtn() {
        this.setLinkType(HyperlinkType.URL);
    }

    @Listen("onClick=#mailBtn")
    public void onClick$mailBtn() {
        this.setLinkType(HyperlinkType.EMAIL);
    }

    @Listen("onOK=window; onClick=#okBtn;")
    public void onClick$okBtn() {
        String addr = this.getAddress();
        if(Strings.isBlank(addr)) {
            Messagebox.show(Labels.getLabel("zssex.dlg.hyperlink.input_address"));
        } else {
            String display = this.displayBox.getValue();
            if(Strings.isBlank(display)) {
                display = addr;
            }

            this.detach();
            this.postCallback("onOK", newMap(new Entry[]{newEntry("headerSize", display), newEntry("address", addr), newEntry("linktype", this.linkType)}));
        }
    }

    @Listen("onCancel=window; onClick=#cancelBtn")
    public void onCancel() {
        this.detach();
        this.postCallback("onCancel", (Map)null);
    }

    private String getAddress() {
        return this.linkType == HyperlinkType.EMAIL?this.getMailAddress():this.getWebAddress();
    }

    private void setLinkType(HyperlinkType type) {
        this.linkType = type;
        this.mailBtn.setDisabled(false);
        this.mailContent.setVisible(false);
        this.webBtn.setDisabled(false);
        this.webContent.setVisible(false);
        if(this.linkType == HyperlinkType.EMAIL) {
            this.initMail();
        } else {
            this.initWeb();
        }

    }

    private void initMail() {
        this.mailContent.setVisible(true);
        this.mailBtn.setDisabled(true);
        final Textbox mailAddr = (Textbox)this.mailContent.getFellow("mailAddr");
        final Textbox mailSubject = (Textbox)this.mailContent.getFellow("mailSubject");
        String preAppend = "mailto:";
        if(this.autoDisplay) {
            mailAddr.addEventListener("onChanging", new EventListener() {
                public void onEvent(Event evt) throws Exception {
                    if(InsertHyperlinkCtrl.this.autoDisplay) {
                        String mail = mailSubject.getValue();
                        String val = "mailto:" + ((InputEvent)evt).getValue() + (mail != null && mail != ""?"?subject=" + mailSubject.getValue():"");
                        InsertHyperlinkCtrl.this.displayBox.setValue(val);
                    }
                }
            });
            mailSubject.addEventListener("onChanging", new EventListener() {
                public void onEvent(Event evt) throws Exception {
                    if(InsertHyperlinkCtrl.this.autoDisplay) {
                        String mail = mailAddr.getValue();
                        if(mail != null && mail != "") {
                            InsertHyperlinkCtrl.this.displayBox.setValue("mailto:" + mailAddr.getValue() + "?subject=" + ((InputEvent)evt).getValue());
                        }

                    }
                }
            });
        }

        mailAddr.focus();
    }

    private void initWeb() {
        this.webContent.setVisible(true);
        this.webBtn.setDisabled(true);
        final Combobox addr = (Combobox)this.webContent.getFellow("addrCombobox");
        if(this.autoDisplay) {
            addr.addEventListener("onChanging", new EventListener() {
                public void onEvent(Event evt) {
                    if(InsertHyperlinkCtrl.this.autoDisplay) {
                        InsertHyperlinkCtrl.this.displayBox.setValue(((InputEvent)evt).getValue());
                    }
                }
            });
            addr.addEventListener("onSelect", new EventListener() {
                public void onEvent(Event evt) throws Exception {
                    if(InsertHyperlinkCtrl.this.autoDisplay) {
                        Comboitem seld = addr.getSelectedItem();
                        if(seld != null) {
                            InsertHyperlinkCtrl.this.displayBox.setValue(seld.getLabel());
                        }

                    }
                }
            });
        }

        if(Strings.isBlank(addr.getValue())) {
            addr.setValue(this.linkAddress);
        }

        addr.focus();
    }

    private String getWebAddress() {
        Combobox address = (Combobox)this.webContent.getFellow("addrCombobox");
        String val = address.getValue();
        return val == null?"":val;
    }

    private String getMailAddress() {
        String mailAddr = ((Textbox)this.mailContent.getFellow("mailAddr")).getValue();
        if("".equals(mailAddr)) {
            return "";
        } else {
            String subject = ((Textbox)this.mailContent.getFellow("mailSubject")).getValue();
            return "mailto:" + mailAddr + ("".equals(subject)?"":"?subject=" + subject);
        }
    }
}
