package com.prolink.tiago.prolinkfranquias2018.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.prolink.tiago.prolinkfranquias2018.model.Contato;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class UpdateContatoAsyncTask extends AsyncTask<Contato, Void, Void> {
    @Override
    protected Void doInBackground(Contato... contatoes) {
        for(Contato contato : contatoes){
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("nome", contato.getNome());
                jsonObject.put("email", contato.getEmail());
                jsonObject.put("telefone", contato.getTelefone());
                jsonObject.put("contatoTipo", "SONDAGEM");
                //ContatoRestClientUsage rest = new ContatoRestClientUsage(this);
                //rest.setPost(null,contato);
            }catch(JSONException e){
                Log.e("JSONError",e.getMessage());
            }
        }
        return null;
    }
}
