package com.makenv.sheets.ui.dlg;

import com.makenv.sheets.ui.CtrlBase;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Window;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alei on 2015/1/27.
 */
public class DlgCtrlBase extends CtrlBase<Window> {
    private final static String ARG_CALLBACK = "callback";
    protected EventListener callback;

    public DlgCtrlBase() {
        super(false);
    }

    protected static Map newArg(EventListener<DlgCallbackEvent> callback) {
        Map arg = new HashMap();
        arg.put(ARG_CALLBACK, callback);
        return arg;
    }

    @Override
    public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);

        callback = (EventListener) Executions.getCurrent().getArg().get(ARG_CALLBACK);
        if(callback==null){
            throw new UiException("callback for dialog not found");
        }

        comp.addEventListener("onCallback", new EventListener<Event>() {
            public void onEvent(Event event) throws Exception {
                Object[] data = (Object[])event.getData();
                DlgCallbackEvent evt = new DlgCallbackEvent((String)data[0],(Map<String,Object>)data[1]);
                callback.onEvent(evt);
            }
        });
    }

    protected void postCallback(String eventName,Map<String,Object> data){
        Events.postEvent("onCallback", getSelf(), new Object[]{eventName, data});

    }
    protected void sendCallback(String eventName,Map<String,Object> data){
        Events.sendEvent("onCallback",getSelf(),new Object[]{eventName,data});
    }

    protected void detach(){
        getSelf().detach();
    }
}