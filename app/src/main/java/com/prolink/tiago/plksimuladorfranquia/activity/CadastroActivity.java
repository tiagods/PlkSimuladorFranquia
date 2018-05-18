package com.prolink.tiago.plksimuladorfranquia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.prolink.tiago.plksimuladorfranquia.R;
import com.prolink.tiago.plksimuladorfranquia.dao.ContatoDAO;
import com.prolink.tiago.plksimuladorfranquia.helper.ContatoOpenHelper;
import com.prolink.tiago.plksimuladorfranquia.model.Contato;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener{

    private BootstrapButton btSimular;
    private BootstrapEditText nome;
    private BootstrapEditText telefone;
    private BootstrapEditText email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        btSimular = (BootstrapButton)findViewById(R.id.button);
        nome = (BootstrapEditText)findViewById(R.id.txNome);
        telefone = (BootstrapEditText)findViewById(R.id.txFone);
        email = (BootstrapEditText)findViewById(R.id.txEmail);

        btSimular.setOnClickListener(CadastroActivity.this);
    }
    @Override
    public void onClick(View view){
        Intent intent = new Intent(this,FranquiaEscolhaActivity.class);

        ContatoDAO dao = new ContatoDAO(this);
        Contato contato = dao.cadastrar(nome.getText().toString(),email.getText().toString(),telefone.getText().toString());
        if(contato!=null) {
            intent.putExtra("contato", contato);
        }
        startActivity(intent);
    }
}
