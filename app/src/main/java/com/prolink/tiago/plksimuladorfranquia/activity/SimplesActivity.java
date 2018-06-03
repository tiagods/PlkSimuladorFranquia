package com.prolink.tiago.plksimuladorfranquia.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.prolink.tiago.plksimuladorfranquia.MainActivity;
import com.prolink.tiago.plksimuladorfranquia.R;
import com.prolink.tiago.plksimuladorfranquia.dao.ContatoDAO;
import com.prolink.tiago.plksimuladorfranquia.util.MoneyTextWatcher;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

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

        faturamento.addTextChangedListener(new MoneyTextWatcher(faturamento));
        prolabore.addTextChangedListener(new MoneyTextWatcher(prolabore));

        faturamento.setText("0");
        prolabore.setText("0");
    }
    @Override
    public void onClick(View view){
        if(!validar())
            return;
        Intent intent = new Intent(this,ResultadoActivity.class);
        //intent.putExtra("contato",contato);
        startActivity(intent);
    }
    private boolean validar(){
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        Number fat = null;
        try {
            fat = nf.parse(faturamento.getText().toString());
            Number pro = nf.parse(faturamento.getText().toString());
        }catch (ParseException e){
            return false;
        }
        if(fat.doubleValue()<=4800000) return true;//limite

        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Impossivel avançar");
        //define a mensagem
        builder.setMessage("O faturamento não pode ser superior a 4.800.000,00 por ano!");
        //define um botão como positivo
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(SimplesActivity.this, "Ok=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //cria o AlertDialog
        AlertDialog alerta = builder.create();
        alerta.show();
        return false;
    }
}
