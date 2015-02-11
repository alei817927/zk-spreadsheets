package com.makenv.sheets.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by alei on 2015/2/4.
 */
public interface Processor {
    public void doProcess(HttpServletRequest request, HttpServletResponse response);
}
