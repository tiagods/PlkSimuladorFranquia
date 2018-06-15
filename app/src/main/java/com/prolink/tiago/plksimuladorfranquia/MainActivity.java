package com.prolink.tiago.plksimuladorfranquia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.prolink.tiago.plksimuladorfranquia.activity.CadastroActivity;
import com.prolink.tiago.plksimuladorfranquia.activity.FranquiaEscolhaActivity;
import com.prolink.tiago.plksimuladorfranquia.dao.ContatoDAO;
import com.prolink.tiago.plksimuladorfranquia.dao.FranquiaDAO;
import com.prolink.tiago.plksimuladorfranquia.model.Contato;
import com.prolink.tiago.plksimuladorfranquia.model.Franquia;
import com.prolink.tiago.plksimuladorfranquia.resources.ContatoRestClientUsage;
import com.prolink.tiago.plksimuladorfranquia.resources.FranquiasRestClientUsage;
import com.prolink.tiago.plksimuladorfranquia.resources.RegistroRestClientUsage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private BootstrapButton button;
    private TextView txVersao;

    private Class entity = FranquiaEscolhaActivity.class;
    private String APP_REGISTRADO="app_registro";
    private String APP_COMERCIAL = "app_comercial";

    private String APP_CHAVE = "app_chave";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (BootstrapButton)findViewById(R.id.button);
        txVersao = findViewById(R.id.txVersao);

        button.setOnClickListener(MainActivity.this);

        executarAtualizacao();
        executarVerificacao();
    }

    void adicionar(String value,boolean result){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putBoolean(value,result);
        editor.apply();
    }
    void adicionarChave(String value,String result){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString(value,result);
        editor.apply();
    }
    @Override
    public void onClick(View view){
        //CadastroActivity.class
        startActivity(new Intent(this,entity));
    }
    private void executarAtualizacao(){
        //verificar se existe algum registro desincronizado e atualiza
        ContatoDAO dao = new ContatoDAO(this);
        List<Contato> contatos = dao.filtrarNaoSincronizados();
        if (!contatos.isEmpty()) {
            ContatoRestClientUsage rest = new ContatoRestClientUsage(this);
            rest.enviar(contatos);
        }
        //verifica a ultima franquia cadastrada
        FranquiaDAO franquiaDAO = new FranquiaDAO(this);
        Franquia franquia = franquiaDAO.getUltimaVersao();
        FranquiasRestClientUsage restClientUsage = new FranquiasRestClientUsage(this);
        if(franquia!=null)
            restClientUsage.getPublic(franquia.getLastUpdate());
        else
            restClientUsage.getPublic(null);
    }
    private void executarVerificacao(){
        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        if(!preferences1.contains(APP_CHAVE)){
            adicionarChave(APP_CHAVE, gerarSerial());
        }
        if(!preferences1.contains(APP_COMERCIAL)){
            register(preferences1);
            Log.i("COMERCIAL","SETANDO COMERCIAL...");
        }
        else if(preferences1.contains(APP_COMERCIAL)){
            entity = CadastroActivity.class;
            Log.i("COMERCIAL","COMERCIAL ATIVO...");
        }
        else{
            entity = FranquiaEscolhaActivity.class;
            Log.i("FRANQUIA","SETANDO FRANQUIA..."+preferences1.getString(APP_CHAVE,"defaultStringIfNothingFound"));
        }
        txVersao.setText(preferences1.getString(APP_CHAVE,"defaultStringIfNothingFound"));
    }
    void register(SharedPreferences preferences1){
        RegistroRestClientUsage registro = new RegistroRestClientUsage(this);
        registro.verificarRegistro(preferences1.getString(APP_CHAVE,"defaultStringIfNothingFound"));
        int code = registro.getCode();
        if(code==404 || code == 204){
            adicionar(APP_REGISTRADO,true);
            Log.i("REGISTRO","app ,setado true");
            if(code == 204){
                adicionar(APP_COMERCIAL,true);
                Log.i("REGISTRO","Comercial setado true");
            }
        }
    }
    public String gerarSerial(){
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
        String data = sdf.format(Calendar.getInstance().getTime());
        int tamanhoDefinido = 32-data.length();//10 representa o tamanho da data
        String alfabeto = "abcdefghijklmnopqrstuvxiz";
        String maiuscula = alfabeto.toUpperCase();
        String numeros = "0123456789";
        String expressao = alfabeto+maiuscula+numeros;
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for(int i = 0 ; i <tamanhoDefinido; i++){
            int valor = random.nextInt(expressao.length());
            builder.append(expressao.substring(valor, valor+1));
        }
        return builder.toString()+data;
    }
}
