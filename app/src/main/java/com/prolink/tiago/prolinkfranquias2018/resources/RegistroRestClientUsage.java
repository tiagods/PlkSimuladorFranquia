package com.prolink.tiago.prolinkfranquias2018.resources;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.prolink.tiago.prolinkfranquias2018.activity.CadastroActivity;
import com.prolink.tiago.prolinkfranquias2018.activity.FranquiaEscolhaActivity;
import com.prolink.tiago.prolinkfranquias2018.dao.FranquiaDAO;
import com.prolink.tiago.prolinkfranquias2018.model.Franquia;
import com.prolink.tiago.prolinkfranquias2018.model.FranquiaPacote;
import com.prolink.tiago.prolinkfranquias2018.model.TipoServico;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpResponseException;

import static java.lang.Boolean.TRUE;

public class RegistroRestClientUsage extends RestClient{
    private Context context;
    private Class entity = FranquiaEscolhaActivity.class;
    public RegistroRestClientUsage(Context context){
        this.context=context;
    }
    public void verificarRegistro(String key, String valor){
        final String ky = key;
        final String value = valor;
        get(API_LICENCAS+"/"+valor, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if(statusCode==200) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
                    editor.putBoolean(ky, true);
                    editor.apply();
                    entity = CadastroActivity.class;
                }
            }
        });
    }

    public Class getEntity() {
        return entity;
    }
}
