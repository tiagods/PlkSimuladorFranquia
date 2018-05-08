package com.prolink.tiago.plksimuladorfranquia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.prolink.tiago.plksimuladorfranquia.MainActivity;
import com.prolink.tiago.plksimuladorfranquia.R;

public class FimActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fim);
        BootstrapButton concluir = (BootstrapButton)findViewById(R.id.buttonConcluir);
        BootstrapButton refazer = (BootstrapButton)findViewById(R.id.buttonRefazer);
        refazer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refazer();
            }
        });
        concluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                concluir();
            }
        });
    }

    public void concluir() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void refazer() {
        Intent intent = new Intent(this,FranquiaEscolhaActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
