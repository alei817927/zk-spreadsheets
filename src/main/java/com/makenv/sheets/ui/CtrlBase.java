package com.makenv.sheets.ui;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alei on 2015/1/27.
 */
public class CtrlBase<T extends Component> extends SelectorComposer<T> {
    private static final long serialVersionUID = 1L;
    protected static final String APPCOMP = "$ZSSAPP$";
    private boolean listenZssAppEvent;
    EventListener<Event> desktopEventDispatcher;

    public CtrlBase(boolean listenZssAppEvent) {
        this.listenZssAppEvent = listenZssAppEvent;
    }

    public void doAfterCompose(T comp) throws Exception {
        super.doAfterCompose(comp);

        if(listenZssAppEvent){
            desktopEventDispatcher = new EventListener<Event>(){
                public void onEvent(Event event) throws Exception {
                    AppEvent appevt = (AppEvent)event.getData();
                    if(appevt.ignoreSelf && appevt.from==CtrlBase.this){
                        return;
                    }
                    onAppEvent(appevt.getName(),appevt.getData());
                }
            };

            getAppComp().addEventListener("onAppEvent", desktopEventDispatcher);
        }
    }

    static protected Entry newEntry(String name,Object value){
        Entry arg = new Entry(name,value);
        return arg;
    }

    static protected Map<String,Object> newMap(Entry... args){
        Map<String,Object> argm = new HashMap<String,Object>();
        for(Entry arg:args){
            argm.put(arg.name,arg.value);
        }
        return argm;
    }
    protected void pushAppEvent(String event){
        pushAppEvent(event,true,null);
    }
    protected void pushAppEvent(String event,Object data){
        pushAppEvent(event,true,data);
    }
    protected void pushAppEvent(String event,boolean ignoreSelf,Object data){

        Component comp = getAppComp();
        Event evt = new Event("onAppEvent",comp,new AppEvent(event, this, ignoreSelf, data));
        Events.postEvent(evt);
    }

    protected Component getAppComp(){
        Component comp = (Component)getSelf().getAttribute(APPCOMP, true);
        if(comp==null){
            throw new NullPointerException("zssapp component not found");
        }
        return comp;
    }
    protected void onAppEvent(String event,Object data){
        //default do nothing.
    }
    static class AppEvent extends Event{
        private static final long serialVersionUID = 1L;
        Object from;
        boolean ignoreSelf;

        public AppEvent(String name, Object from, boolean ignoreSelf, Object data) {
            super(name, null, data);
            this.from = from;
            this.ignoreSelf = ignoreSelf;
        }
    }
    static protected class Entry {
        final String name;
        final Object value;
        public Entry(String name,Object value){
            this.name = name;
            this.value = value;
        }
    }

}
