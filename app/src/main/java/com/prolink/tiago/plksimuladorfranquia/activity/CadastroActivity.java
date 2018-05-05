package com.prolink.tiago.plksimuladorfranquia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.prolink.tiago.plksimuladorfranquia.R;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener{

    private BootstrapButton btSimular;
    private BootstrapEditText nome;
    private BootstrapEditText telefone;
    private BootstrapEditText email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        btSimular = findViewById(R.id.button);
        nome = findViewById(R.id.txNome);
        telefone = findViewById(R.id.txFone);
        email = findViewById(R.id.txEmail);

        btSimular.setOnClickListener(CadastroActivity.this);
    }
    @Override
    public void onClick(View view){
        startActivity(new Intent(this,FranquiaEscolhaActivity.class));
    }
}
