package com.prolink.tiago.plksimuladorfranquia.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.prolink.tiago.plksimuladorfranquia.R;
import com.prolink.tiago.plksimuladorfranquia.model.FaturamentoConsumo;
import com.prolink.tiago.plksimuladorfranquia.util.MoneyTextWatcher;

import java.text.NumberFormat;
import java.text.ParseException;

public class SimplesActivity extends AppCompatActivity implements View.OnClickListener{

    private NumberFormat nf = NumberFormat.getCurrencyInstance();
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
        Number fat = null;
        Number pro = null;
        try {
            fat = nf.parse(faturamento.getText().toString());
            pro = nf.parse(prolabore.getText().toString());
        }catch (ParseException e){

        }
        Intent intent = new Intent(this,ResultadoActivity.class);
        FaturamentoConsumo faturamentoConsumo = new FaturamentoConsumo();
        faturamentoConsumo.setFaturamento(fat.doubleValue());
        faturamentoConsumo.setProLabore(pro.doubleValue());
        intent.putExtra("faturamento",faturamentoConsumo);
        startActivity(intent);
    }
    private boolean validar(){
        try {
            Number fat = nf.parse(faturamento.getText().toString());

            if(fat.doubleValue()>4800000.00){
                exibirAlerta("O faturamento não pode ser superir a R$4.800.000,00 por ano!\nEntre em contato com um de nossos colaboradores!");
                return false;
            }
            else if(fat.doubleValue()==0){
                exibirAlerta("Faturamento informado é inválido!");
                return false;
            }
            else return true;

        }catch (ParseException e){
            return false;
        }
    }
    private void exibirAlerta(String mensagem){

        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Impossivel avançar");
        //define a mensagem
        builder.setMessage(mensagem);
        //define um botão como positivo
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(SimplesActivity.this, "Ok=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //cria o AlertDialog
        AlertDialog alerta = builder.create();
        alerta.show();
    }

}
