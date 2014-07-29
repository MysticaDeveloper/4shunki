package com.mystica.shishunki.api;

import java.nio.charset.Charset;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

abstract class ApiManager<E> {
    protected static final Charset CHARSET = Charset.forName("UTF-8");
    protected static String URL = "https://ajax.googleapis.com/ajax/services/search/images/";
    
    protected ApiManager() {
    }

    public E request(Map<String, Object> params) throws ForbiddenException {
        String response;
        Log.d(getTag(), params.toString());
        try {
            response = Request.post(URL, params);
        } catch (ForbiddenException e) {
            throw new ForbiddenException("エラーが発生しました。url = " + URL, e);
        }

        Log.d(getTag(), response);
        try {
            JSONObject map = new JSONObject(response);
            return createReponse(map);
        } catch (JSONException e) {
            throw new ForbiddenException("エラーが発生しました。url = " + URL + " response : " + response, e);
        }
    }
    
    protected String getTag() {
    	return getClass().toString();
    }
    
    abstract protected E createReponse(JSONObject map) throws JSONException;
    
}
