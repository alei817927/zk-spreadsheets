package com.makenv.sheets.processor;

import com.makenv.sheets.processor.impl.LoginProcessor;
import com.makenv.sheets.processor.impl.SelectProcessor;
import com.makenv.sheets.util.StringUtil;

/**
 * Created by alei on 2015/2/4.
 */
public enum ProcessorType {
    //登录
    LOGIN("login", new LoginProcessor()),
    //选择
    SELECT("select", new SelectProcessor());

    private final String name;
    private final Processor type;

    ProcessorType(String name, Processor type) {
        this.name = name;
        this.type = type;
    }

    public static Processor getProcessor(String name) {
        if (StringUtil.isEmpty(name)) {
            return null;
        }
        for (ProcessorType type : ProcessorType.values()) {
            if (type.getName().equals(name)) {
                return type.getType();
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public Processor getType() {
        return type;
    }
}
