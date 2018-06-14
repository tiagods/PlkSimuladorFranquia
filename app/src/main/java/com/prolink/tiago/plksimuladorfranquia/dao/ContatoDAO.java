package com.prolink.tiago.plksimuladorfranquia.dao;

import android.content.Context;
import android.util.Log;

import com.prolink.tiago.plksimuladorfranquia.helper.ContatoOpenHelper;
import com.prolink.tiago.plksimuladorfranquia.model.Contato;
import com.prolink.tiago.plksimuladorfranquia.model.Franquia;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {
    private Context context;
    ContatoOpenHelper db;
    public ContatoDAO(Context context){
        this.context=context;
    }
    public void dropTable(){
        db = new ContatoOpenHelper(this.context);
        db.drop();
        Log.i("MYAPP","Limpando Tabela de Contatos");
        for(Contato c : db.getAll()){
            Log.v("MYAPP",c.getId()+"\t "+c.getNome()+"\t "+c.getTelefone()+"\t "+c.getEmail());
        }
        db.close();
    }
    public Contato cadastrar(String nome, String email, String telefone){
        db = new ContatoOpenHelper(this.context);
        Contato contato = new Contato();
        contato.setNome(nome);
        contato.setTelefone(telefone);
        contato.setEmail(email);
        db.insert(contato);
        contato = db.getLast();
        for(Contato c : db.getAll()){
            Log.v("MYAPP",c.getId()+"\t "+c.getNome()+"\t "+c.getTelefone()+"\t "+c.getEmail());
        }
        db.close();
        return contato;
    }
    public void atualizar(Contato contato){
        db = new ContatoOpenHelper(this.context);
        db.update(contato);
        db.close();
        Log.v("MYAPP",contato.getId()+"\t "+contato.getNome()+"\t "+contato.getTelefone()+"\t "+contato.getEmail());
    }
    public List<Contato> filtrarNaoSincronizados() {
        db = new ContatoOpenHelper(this.context);
        List<Contato> list = db.filtrarNaoSincronizados();
        db.close();
        return list;
    }
}
