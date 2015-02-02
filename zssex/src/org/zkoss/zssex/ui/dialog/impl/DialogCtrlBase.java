//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.dialog.impl;

import java.util.HashMap;
import java.util.Map;
import org.zkoss.lang.Objects;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zssex.ui.dialog.impl.DialogCallbackEvent;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;
import org.zkoss.zul.ext.Selectable;

public class DialogCtrlBase extends SelectorComposer<Window> {
    private static final String ARG_CALLBACK = "callback";
    protected EventListener callback;
    public static final String ON_OK = "onOK";
    public static final String ON_CANCEL = "onCancel";

    public DialogCtrlBase() {
    }

    public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);
        this.callback = (EventListener)Executions.getCurrent().getArg().get("callback");
        if(this.callback == null) {
            throw new UiException("callback for dialog not found");
        } else {
            comp.addEventListener("onCallback", new EventListener() {
                public void onEvent(Event event) throws Exception {
                    Object[] data = (Object[])((Object[])event.getData());
                    DialogCallbackEvent evt = new DialogCallbackEvent((String)data[0], (Map)data[1]);
                    DialogCtrlBase.this.callback.onEvent(evt);
                }
            });
        }
    }

    protected Component getFellow(String compId) {
        return ((Window)this.getSelf()).getFellow(compId);
    }

    protected static Map newArg(EventListener<DialogCallbackEvent> callback) {
        HashMap arg = new HashMap();
        arg.put("callback", callback);
        return arg;
    }

    protected void postCallback(String eventName, Map<String, Object> data) {
        Events.postEvent("onCallback", this.getSelf(), new Object[]{eventName, data});
    }

    protected void sendCallback(String eventName, Map<String, Object> data) {
        Events.sendEvent("onCallback", this.getSelf(), new Object[]{eventName, data});
    }

    protected void detach() {
        ((Window)this.getSelf()).detach();
    }

    protected static Entry newEntry(String name, Object value) {
        Entry arg = new Entry(name, value);
        return arg;
    }

    protected static Map<String, Object> newMap(Entry... args) {
        HashMap argm = new HashMap();
        Entry[] arr$ = args;
        int len$ = args.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Entry arg = arr$[i$];
            argm.put(arg.name, arg.value);
        }

        return argm;
    }

    protected static Object getSingleSelection(ListModel model) {
        Selectable sel = model instanceof Selectable?(Selectable)model:null;
        return sel != null && sel.getSelection().size() > 0?sel.getSelection().iterator().next():null;
    }

    protected static int getSingleSelectionIndex(ListModel model) {
        Selectable sel = model instanceof Selectable?(Selectable)model:null;
        if(sel != null && sel.getSelection().size() > 0) {
            Object obj = sel.getSelection().iterator().next();
            if(model instanceof ListModelList) {
                return ((ListModelList)model).indexOf(obj);
            }

            int s = model.getSize();

            for(int i = 0; i < s; ++i) {
                if(Objects.equals(obj, model.getElementAt(i))) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static class Entry {
        final String name;
        final Object value;

        public Entry(String name, Object value) {
            this.name = name;
            this.value = value;
        }
    }
}
