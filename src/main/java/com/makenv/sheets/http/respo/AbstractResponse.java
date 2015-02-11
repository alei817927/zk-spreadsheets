package com.makenv.sheets.http.respo;

/**
 * Created by alei on 2015/2/5.
 */
public abstract class AbstractResponse implements Response {
    private int status;

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }
}
