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

        Contato c = (Contato)getIntent().getSerializableExtra("contato");
        if(c!=null)
            Log.v("MYAPP","Recenbendo objeto cadastrado= "+this.getClass().getSimpleName()+"\t"+c.getNome()+"\t "+c.getTelefone()+"\t "+c.getEmail());


        Spinner mySpinner = (Spinner)findViewById(R.id.spinner);

    /*
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.franquias_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0){
                    franquia(adapter.getItem(i));
                }
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
*/

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
                // Here you can do the action you want to...
                Toast.makeText(FranquiaEscolhaActivity.this
                        , "ID: " + franquia.getId() + "\nName: " + franquia.getNome()+" - Abrir ?" +(position!=0?"Sim":"Nao"),
                        Toast.LENGTH_SHORT).show();
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
    private void franquia(CharSequence charSequence){
        Intent intent = new Intent(this,FranquiaDetalhesActivity.class);
        intent.putExtra("franquia",charSequence);
        startActivity(intent);
    }
    private void rejeitar(){
        Intent intent = new Intent(this,SimplesActivity.class);
        startActivity(intent);
    }
}
