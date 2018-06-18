package com.prolink.tiago.plksimuladorfranquia.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.prolink.tiago.plksimuladorfranquia.R;
import com.prolink.tiago.plksimuladorfranquia.dao.ContatoDAO;
import com.prolink.tiago.plksimuladorfranquia.model.Contato;
import com.prolink.tiago.plksimuladorfranquia.resources.ContatoRestClientUsage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        String mensagem  = "Os seguintes campos são obrigatórios\n";
        boolean exibirErro = false;
        if(!validarNome(nome.getText().toString())){
            exibirErro=true;
            mensagem+="Nome vazio\n";
        }
        if(!validarTelefone(telefone.getText().toString())){
            exibirErro=true;
            mensagem+="Telefone vazio ou invalido\n";
        }
        if(!validarEmail(email.getText().toString())){
            exibirErro=true;
            mensagem+="Email vazio ou invalido\n";
        }
        if(exibirErro){
            //Cria o gerador do AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //define o titulo
            builder.setTitle("Erro no Campo informado");
            //define a mensagem
            builder.setMessage(mensagem);
            //cria o AlertDialog
            AlertDialog alerta = builder.create();
            alerta.show();
            return;
        }

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

    private boolean validarEmail(String email){
        if(email.trim().equals("")) return false;
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }

    private boolean validarNome(String nome){
        return nome.trim().length()>0;
    }

    private boolean validarTelefone(String telefone){
        return telefone.trim().length()>0;
    }

}
