//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.dialog;

import java.util.LinkedList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zssex.ui.dialog.FormulaMetaInfo;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.impl.InputElement;

public class ComposeFormulaCtrl extends SelectorComposer<Component> {
    private static final long serialVersionUID = 1L;
    @Wire
    private Label formulaStart;
    @Wire
    private Textbox composeFormulaTextbox;
    @Wire
    private Listbox argsListbox;
    @Wire
    private Label description;
    private FormulaMetaInfo metaInfo;
    private int focusToIndex = -1;
    private List<ArgWrapper> args;
    private List<InputElement> inputs = new LinkedList();
    private boolean movedToNext = false;
    private InputElement focusComponent;

    public ComposeFormulaCtrl() {
    }

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        this.composeFormulaTextbox.addEventListener("onChange", new EventListener() {
            public void onEvent(Event event) throws Exception {
                ComposeFormulaCtrl.this.decomposeFormula();
            }
        });
        this.argsListbox.setItemRenderer(new ListitemRenderer() {
            public void render(Listitem item, Object data) throws Exception {
                final ArgWrapper arg = (ArgWrapper)data;
                item.setValue(arg);
                item.appendChild(new Listcell(arg.getName()));
                final Textbox tb = new Textbox(arg.getValue());
                ComposeFormulaCtrl.this.inputs.add(tb);
                tb.addEventListener("onChange", new EventListener() {
                    public void onEvent(Event event) throws Exception {
                        arg.setValue(tb.getValue());
                        ComposeFormulaCtrl.this.composeFormula();
                        ComposeFormulaCtrl.this.moveFocusToNext(tb);
                        ComposeFormulaCtrl.this.movedToNext = false;
                    }
                });
                tb.addEventListener("onFocus", new EventListener() {
                    public void onEvent(Event event) throws Exception {
                        ArgWrapper last = (ArgWrapper)ComposeFormulaCtrl.this.args.get(ComposeFormulaCtrl.this.args.size() - 1);
                        if(last.equals(arg) && ComposeFormulaCtrl.this.metaInfo.isMultipleParameter()) {
                            ComposeFormulaCtrl.this.focusToIndex = ComposeFormulaCtrl.this.args.size() - 1;
                            ComposeFormulaCtrl.this.args.add(ComposeFormulaCtrl.this.createNextArg());
                            ComposeFormulaCtrl.this.argsListbox.setModel(ComposeFormulaCtrl.this.newListModelInstance(ComposeFormulaCtrl.this.args));
                        } else {
                            ComposeFormulaCtrl.this.focusComponent = tb;
                        }

                        if(ComposeFormulaCtrl.this.movedToNext && ComposeFormulaCtrl.this.focusComponent != null) {
                            ComposeFormulaCtrl.this.focusComponent.focus();
                        }

                        ComposeFormulaCtrl.this.movedToNext = false;
                    }
                });
                Listcell cell = new Listcell();
                cell.appendChild(tb);
                item.appendChild(cell);
            }

            public void render(Listitem item, Object data, int index) throws Exception {
                this.render(item, data);
            }
        });
        this.argsListbox.addEventListener("onAfterRender", new EventListener() {
            public void onEvent(Event event) throws Exception {
                int focusIdx = -1;
                if(ComposeFormulaCtrl.this.focusToIndex >= 0 && ComposeFormulaCtrl.this.focusToIndex < ComposeFormulaCtrl.this.inputs.size()) {
                    focusIdx = ComposeFormulaCtrl.this.focusToIndex;
                } else if(ComposeFormulaCtrl.this.inputs.size() > 1) {
                    focusIdx = 0;
                }

                if(focusIdx >= 0) {
                    ((InputElement)ComposeFormulaCtrl.this.inputs.get(focusIdx)).focus();
                    ComposeFormulaCtrl.this.focusComponent = (InputElement)ComposeFormulaCtrl.this.inputs.get(focusIdx);
                }

            }
        });
    }

    @Listen("onOpen = #_composeFormulaDialog")
    public void onOpen$_composeFormulaDialog(Event evt) {
        this.metaInfo = (FormulaMetaInfo)evt.getData();
        this.formulaStart.setValue("=" + this.metaInfo.getFunction() + "(");
        this.description.setValue(this.metaInfo.getDescription());
        this.composeFormulaTextbox.setText((String)null);
        this.args = this.createArgs(this.metaInfo.getRequiredParameter(), this.metaInfo.getParameterNames());
        this.argsListbox.setModel(this.newListModelInstance(this.args));
        this.composeFormulaTextbox.focus();
    }

    private SimpleListModel<ArgWrapper> newListModelInstance(List<ArgWrapper> functionArguments) {
        this.inputs.clear();
        this.focusComponent = null;
        return new SimpleListModel(functionArguments);
    }

    private void moveFocusToNext(HtmlBasedComponent current) {
        for(int i = 0; i < this.inputs.size(); ++i) {
            InputElement c = (InputElement)this.inputs.get(i);
            if(c == current && i + 1 < this.inputs.size()) {
                InputElement next = (InputElement)this.inputs.get(i + 1);
                next.focus();
                this.focusComponent = next;
                this.movedToNext = true;
                break;
            }
        }

    }

    private void composeFormula() {
        StringBuilder strBuilder = new StringBuilder();
        boolean first = true;

        for(int i = 0; i < this.args.size(); ++i) {
            ArgWrapper curArg = (ArgWrapper)this.args.get(i);
            String arg = curArg.getValue();
            if(first) {
                first = false;
            } else if(!first) {
                if(!"".equals(arg)) {
                    strBuilder.append(",");
                } else {
                    for(int j = i + 1; j < this.args.size(); ++j) {
                        String val = ((ArgWrapper)this.args.get(j)).getValue();
                        if(!"".equals(val)) {
                            strBuilder.append(",");
                            break;
                        }
                    }
                }
            }

            if(!"".equals(arg)) {
                strBuilder.append(arg);
            }
        }

        this.composeFormulaTextbox.setText(strBuilder.toString());
    }

    private ArgWrapper createNextArg() {
        Integer num = null;
        String argName = this.metaInfo.getMultipleParameter();
        ArgWrapper last = (ArgWrapper)this.args.get(this.args.size() - 1);

        try {
            num = Integer.valueOf(Integer.parseInt(last.getName().replace(argName, "")));
            num = Integer.valueOf(num.intValue() + 1);
        } catch (NumberFormatException var6) {
            ;
        }

        return new ArgWrapper(Integer.valueOf(this.args.size()), this.metaInfo.getMultipleParameter() + (num != null?num:""), "");
    }

    private void decomposeFormula() {
        String input = this.composeFormulaTextbox.getText();
        String[] arg = input.split(",");
        int i;
        if(arg.length > this.args.size()) {
            if(!this.metaInfo.isMultipleParameter()) {
                Messagebox.show("You\'ve entered too many arguments for this function");
                return;
            }

            i = arg.length - this.args.size();

            for(int val = 0; val < i; ++val) {
                this.args.add(this.createNextArg());
            }
        }

        for(i = 0; i < this.args.size() && i < arg.length; ++i) {
            String var5 = arg[i].trim();
            ((ArgWrapper)this.args.get(i)).setValue(var5);
        }

        this.argsListbox.setModel(this.newListModelInstance(this.args));
    }

    private List<ArgWrapper> createArgs(int numArg, String[] argNames) {
        LinkedList ary = new LinkedList();

        for(int i = 0; i < numArg; ++i) {
            ary.add(new ArgWrapper(Integer.valueOf(i), argNames[i], ""));
        }

        return ary;
    }

    private class ArgWrapper {
        Integer index;
        String name;
        String value;

        public ArgWrapper(Integer index, String name, String value) {
            this.index = index;
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getIndex() {
            return this.index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
