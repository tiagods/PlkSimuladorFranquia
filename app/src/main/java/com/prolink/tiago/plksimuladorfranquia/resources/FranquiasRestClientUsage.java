package com.prolink.tiago.plksimuladorfranquia.resources;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.prolink.tiago.plksimuladorfranquia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
public class FranquiasRestClientUsage extends RestClient{
    private Context context;
    public FranquiasRestClientUsage(Context context){
        this.context=context;
    }
    public void getPublic(Long id) throws JSONException {
        String url = "";
        if(id!=null) url = "/"+id;

        get(String.valueOf(R.string.API_CONTATO)+url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                try {
                    JSONArray array = jsonArray;
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        Log.i("JSONResult", jsonObject.getString("nome"));
                    }
                }catch (JSONException e){
                    Log.e("JSONException",e.getMessage());
                }
            }
        });
    }
}
