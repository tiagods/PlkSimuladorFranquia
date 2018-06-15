package com.prolink.tiago.plksimuladorfranquia.resources;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.prolink.tiago.plksimuladorfranquia.dao.FranquiaDAO;
import com.prolink.tiago.plksimuladorfranquia.model.Franquia;
import com.prolink.tiago.plksimuladorfranquia.model.FranquiaPacote;
import com.prolink.tiago.plksimuladorfranquia.model.TipoServico;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import cz.msebera.android.httpclient.Header;

import static java.lang.Boolean.TRUE;

public class RegistroRestClientUsage extends RestClient{
    private Context context;
    private int code = 0;
    public RegistroRestClientUsage(Context context){
        this.context=context;
    }
    public void verificarRegistro(String valor) {
        get("licencas/"+valor, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                code=statusCode;
            }
        });
    }
    public int getCode(){
        return code;
    }
}
