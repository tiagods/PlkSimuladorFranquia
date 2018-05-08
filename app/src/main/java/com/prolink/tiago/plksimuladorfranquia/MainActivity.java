package com.prolink.tiago.plksimuladorfranquia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.prolink.tiago.plksimuladorfranquia.R;
import com.prolink.tiago.plksimuladorfranquia.activity.CadastroActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private BootstrapButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (BootstrapButton)findViewById(R.id.button);
        button.setOnClickListener(MainActivity.this);
    }
    @Override
    public void onClick(View view){
        startActivity(new Intent(this,CadastroActivity.class));
    }
}
