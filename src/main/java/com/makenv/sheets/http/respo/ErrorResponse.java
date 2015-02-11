package com.makenv.sheets.http.respo;

/**
 * Created by alei on 2015/2/5.
 */
public class ErrorResponse extends AbstractResponse {
    private String errorMessage;

    public ErrorResponse(int status, String errorMessage) {
        this.setErrorMessage(errorMessage);
        this.setStatus(status);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
