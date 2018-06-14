package com.prolink.tiago.plksimuladorfranquia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.prolink.tiago.plksimuladorfranquia.activity.CadastroActivity;
import com.prolink.tiago.plksimuladorfranquia.dao.ContatoDAO;
import com.prolink.tiago.plksimuladorfranquia.dao.FranquiaDAO;
import com.prolink.tiago.plksimuladorfranquia.model.Contato;
import com.prolink.tiago.plksimuladorfranquia.model.Franquia;
import com.prolink.tiago.plksimuladorfranquia.resources.ContatoRestClientUsage;
import com.prolink.tiago.plksimuladorfranquia.resources.FranquiasRestClientUsage;

import java.text.SimpleDateFormat;
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
        if (!contatos.isEmpty()) {
            ContatoRestClientUsage rest = new ContatoRestClientUsage(this);
            rest.enviar(contatos);
        }
        //verifica a ultima franquia cadastrada
        FranquiaDAO franquiaDAO = new FranquiaDAO(this);
        franquiaDAO.dropTable();
        for(Franquia f : franquiaDAO.getAll()){
            Log.v("FRANQUIA", new SimpleDateFormat("dd//MM/yyyy HH:mm:ss").format(f.getLastUpdate().getTime()));
        }
        Franquia franquia = franquiaDAO.getUltimaVersao();
        FranquiasRestClientUsage restClientUsage = new FranquiasRestClientUsage(this);
        if(franquia!=null)
            restClientUsage.getPublic(franquia.getLastUpdate());
        else
            restClientUsage.getPublic(null);
    }
    @Override
    public void onClick(View view){
        startActivity(new Intent(this,CadastroActivity.class));
    }
}
