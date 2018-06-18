package com.prolink.tiago.plksimuladorfranquia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.prolink.tiago.plksimuladorfranquia.R;
import com.prolink.tiago.plksimuladorfranquia.dao.FranquiaDAO;
import com.prolink.tiago.plksimuladorfranquia.model.Contato;
import com.prolink.tiago.plksimuladorfranquia.model.Franquia;
import com.prolink.tiago.plksimuladorfranquia.util.FranquiaSpinAdapter;

import java.util.ArrayList;
import java.util.List;

public class FranquiaEscolhaActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franquia);

        Spinner mySpinner = (Spinner)findViewById(R.id.spinner);

        FranquiaDAO franquiaDAO = new FranquiaDAO(this);
        List<Franquia> franquias = franquiaDAO.listarAtivos();
        // And finally send the Users array (Your data)
        final FranquiaSpinAdapter adapter = new FranquiaSpinAdapter(this,
                android.R.layout.simple_spinner_item,
                franquias);
        mySpinner.setAdapter(adapter); // Set the custom adapter to the spinner
        // You can create an anonymous listener to handle the event when is selected an spinner item
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Franquia franquia = adapter.getItem(position);
                if(position!=0){
                    franquia(franquia);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });

        BootstrapButton button = (BootstrapButton)findViewById(R.id.buttonRejeitar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejeitar();
            }
        });

    }
    private void franquia(Franquia franquia){
        Intent intent = new Intent(this,FranquiaDetalhesActivity.class);
        intent.putExtra("franquia",franquia);
        startActivity(intent);
    }
    private void rejeitar(){
        Intent intent = new Intent(this,SimplesActivity.class);
        startActivity(intent);
    }
}
