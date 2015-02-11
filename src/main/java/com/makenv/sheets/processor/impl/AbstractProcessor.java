package com.makenv.sheets.processor.impl;

import com.makenv.sheets.http.ResponseHelper;
import com.makenv.sheets.http.respo.Response;
import com.makenv.sheets.processor.Processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by alei on 2015/2/5.
 */
public abstract class AbstractProcessor implements Processor {
    @Override
    public void doProcess(HttpServletRequest request, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        doProc(request, response);
    }

    protected abstract void doProc(HttpServletRequest request, HttpServletResponse response);

    protected String toJSON(Response response) {
        return ResponseHelper.toJSON(response);
    }
}
