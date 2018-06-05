package com.prolink.tiago.plksimuladorfranquia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.prolink.tiago.plksimuladorfranquia.MainActivity;
import com.prolink.tiago.plksimuladorfranquia.R;

public class FimActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fim);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    Intent intent = new Intent(FimActivity.this,MainActivity.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        new Thread(runnable).start();
    }

    public void concluir() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void refazer() {
        Intent intent = new Intent(this,FranquiaEscolhaActivity.class);
        startActivity(intent);
    }
}
