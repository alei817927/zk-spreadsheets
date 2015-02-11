package com.makenv.sheets.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makenv.sheets.http.respo.Response;

/**
 * Created by alei on 2015/2/4.
 */
public class ResponseHelper {
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public static String toJSON(Response response) {
        String ret = null;
        try {
            ret = mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
