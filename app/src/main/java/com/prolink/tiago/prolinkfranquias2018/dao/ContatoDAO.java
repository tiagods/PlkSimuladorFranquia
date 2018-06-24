package com.prolink.tiago.prolinkfranquias2018.dao;

import android.content.Context;
import android.util.Log;

import com.prolink.tiago.prolinkfranquias2018.helper.ContatoOpenHelper;
import com.prolink.tiago.prolinkfranquias2018.model.Contato;
import com.prolink.tiago.prolinkfranquias2018.model.Franquia;

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
        db.close();
        return contato;
    }
    public void atualizar(Contato contato){
        db = new ContatoOpenHelper(this.context);
        db.update(contato);
        db.close();
    }
    public List<Contato> filtrarNaoSincronizados() {
        db = new ContatoOpenHelper(this.context);
        List<Contato> list = db.filtrarNaoSincronizados();
        db.close();
        return list;
    }
}
