package com.prolink.tiago.plksimuladorfranquia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.prolink.tiago.plksimuladorfranquia.activity.CadastroActivity;
import com.prolink.tiago.plksimuladorfranquia.dao.ContatoDAO;
import com.prolink.tiago.plksimuladorfranquia.model.Contato;
import com.prolink.tiago.plksimuladorfranquia.resources.ContatoRestClientUsage;

import java.util.List;

import cz.msebera.android.httpclient.conn.ConnectTimeoutException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private BootstrapButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (BootstrapButton)findViewById(R.id.button);
        button.setOnClickListener(MainActivity.this);

        //verificar se existe algum registro desincronizado e atualiza
        ContatoDAO dao = new ContatoDAO(this);
        List<Contato> contatos = dao.filtrarNaoSincronizados();
        if (contatos.isEmpty()) {
            ContatoRestClientUsage rest = new ContatoRestClientUsage(this);
            rest.enviar(contatos);
        }
    }
    @Override
    public void onClick(View view){
        startActivity(new Intent(this,CadastroActivity.class));
    }
}
