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

        btSimular = findViewById(R.id.button);
        nome = findViewById(R.id.txNome);
        telefone = findViewById(R.id.txFone);
        email = findViewById(R.id.txEmail);

        btSimular.setOnClickListener(CadastroActivity.this);

        ContatoOpenHelper a = new ContatoOpenHelper(this);
        a.drop();
        Log.v("MYAPP","Table Contato erase");
        for(Contato c : a.getAll()){
            Log.v("MYAPP",c.getId()+"\t "+c.getNome()+"\t "+c.getTelefone()+"\t "+c.getEmail());
        }
    }
    @Override
    public void onClick(View view){
        ContatoOpenHelper a = new ContatoOpenHelper(this);
        Contato contato = new Contato();
        contato.setNome(nome.getText().toString());
        contato.setTelefone(telefone.getText().toString());
        contato.setEmail(email.getText().toString());
        a.insert(contato);
        for(Contato c : a.getAll()){
            Log.v("MYAPP",c.getId()+"\t "+c.getNome()+"\t "+c.getTelefone()+"\t "+c.getEmail());
        }
        startActivity(new Intent(this,FranquiaEscolhaActivity.class));
    }
}
