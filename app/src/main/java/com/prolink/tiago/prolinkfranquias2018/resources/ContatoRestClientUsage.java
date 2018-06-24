package com.prolink.tiago.prolinkfranquias2018.resources;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.*;
import com.loopj.android.http.*;
import com.prolink.tiago.prolinkfranquias2018.R;
import com.prolink.tiago.prolinkfranquias2018.dao.ContatoDAO;
import com.prolink.tiago.prolinkfranquias2018.model.Contato;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.conn.ConnectTimeoutException;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class ContatoRestClientUsage extends RestClient{
    private Context context;
    public ContatoRestClientUsage(Context context){
        this.context=context;
    }
    public void enviar(final List<Contato> contato){
        Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for(Contato c : contato) {
                    try {
                        setPost(context, c);
                    } catch (JSONException e) {
                    } catch (UnsupportedEncodingException e) {
                    }
                }
            }
        };
        mainHandler.post(runnable);
    }
    public void setPost(final Context context, final Contato contato) throws JSONException,UnsupportedEncodingException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nome", contato.getNome());
        jsonObject.put("email", contato.getEmail());
        jsonObject.put("telefone", contato.getTelefone());
        jsonObject.put("contatoTipo", "SONDAGEM");
        StringEntity entity = new StringEntity(jsonObject.toString());
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        post(context, API_CONTATO, entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 201) {//status created
                    for (Header header : headers) {
                        if(header.getName().equals("Location")){
                            String value = header.getValue().substring(header.getValue().lastIndexOf("/")+1);
                            Log.i("HEADER", header.getName() + "\t" + header.getValue()+"\t="+value);
                            Contato c = contato;
                            c.setSincronizado(1);
                            c.setContato_id(Integer.parseInt(value));
                            ContatoDAO dao = new ContatoDAO(context);
                            dao.atualizar(c);
                        }
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }
}
