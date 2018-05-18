package com.prolink.tiago.plksimuladorfranquia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.prolink.tiago.plksimuladorfranquia.R;
import com.prolink.tiago.plksimuladorfranquia.dao.ContatoDAO;

public class SimplesActivity extends AppCompatActivity implements View.OnClickListener{

    private BootstrapEditText faturamento;
    private BootstrapEditText prolabore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simples);
        //btSimular = (BootstrapButton)findViewById(R.id.button);
        faturamento = (BootstrapEditText)findViewById(R.id.txFaturamento);
        prolabore = (BootstrapEditText)findViewById(R.id.txProlabore);
    }
    @Override
    public void onClick(View view){
        Intent intent = new Intent(this,ResultadoActivity.class);
        //intent.putExtra("contato",contato);
        startActivity(intent);
    }
}
