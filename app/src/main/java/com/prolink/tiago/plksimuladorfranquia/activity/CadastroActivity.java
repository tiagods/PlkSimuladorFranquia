package com.prolink.tiago.plksimuladorfranquia.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.prolink.tiago.plksimuladorfranquia.R;
import com.prolink.tiago.plksimuladorfranquia.config.HostConfig;
import com.prolink.tiago.plksimuladorfranquia.dao.ContatoDAO;
import com.prolink.tiago.plksimuladorfranquia.helper.ContatoOpenHelper;
import com.prolink.tiago.plksimuladorfranquia.model.Contato;
import com.prolink.tiago.plksimuladorfranquia.task.ContatoRestClientUsage;
import com.prolink.tiago.plksimuladorfranquia.task.ContatosRestClient;
import com.prolink.tiago.plksimuladorfranquia.task.UpdateContatoAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener {

    private BootstrapButton btSimular;
    private BootstrapEditText nome;
    private BootstrapEditText telefone;
    private BootstrapEditText email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        btSimular = (BootstrapButton) findViewById(R.id.button);
        nome = (BootstrapEditText) findViewById(R.id.txNome);
        telefone = (BootstrapEditText) findViewById(R.id.txFone);
        email = (BootstrapEditText) findViewById(R.id.txEmail);

        btSimular.setOnClickListener(CadastroActivity.this);
    }

    @Override
    public void onClick(View view) {
        final Intent intent = new Intent(this, FranquiaEscolhaActivity.class);

        ContatoDAO dao = new ContatoDAO(this);
        final Contato contato = dao.cadastrar(nome.getText().toString(), email.getText().toString(), telefone.getText().toString());
        if (contato != null) {
            List<Contato> list = new ArrayList<>();
            list.add(contato);

            ContatoRestClientUsage rest = new ContatoRestClientUsage(this);
            rest.enviar(list);

            intent.putExtra("contato", contato);
        }
        startActivity(intent);
    }
}
