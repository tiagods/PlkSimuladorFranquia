package com.prolink.tiago.plksimuladorfranquia.dao;

import android.content.Context;
import android.util.Log;

import com.prolink.tiago.plksimuladorfranquia.helper.ContatoOpenHelper;
import com.prolink.tiago.plksimuladorfranquia.model.Contato;

import java.util.List;

public class ContatoDAO {
    private Context context;

    public ContatoDAO(Context context){
        this.context=context;
        ContatoOpenHelper a = new ContatoOpenHelper(this.context);
        a.drop();
        Log.v("MYAPP","Limpando Tabela de Contatos");
        for(Contato c : a.getAll()){
            Log.v("MYAPP",c.getId()+"\t "+c.getNome()+"\t "+c.getTelefone()+"\t "+c.getEmail());
        }
    }
    public Contato cadastrar(String nome, String email, String telefone){
        ContatoOpenHelper a = new ContatoOpenHelper(this.context);
        Contato contato = new Contato();
        contato.setNome(nome);
        contato.setTelefone(telefone);
        contato.setEmail(email);
        a.insert(contato);
        contato = a.getLast();
        for(Contato c : a.getAll()){
            Log.v("MYAPP",c.getId()+"\t "+c.getNome()+"\t "+c.getTelefone()+"\t "+c.getEmail());
        }
        return contato;
    }
    public void atualizar(Contato contato){
        ContatoOpenHelper a = new ContatoOpenHelper(this.context);
        a.update(contato);
        Log.v("MYAPP",contato.getId()+"\t "+contato.getNome()+"\t "+contato.getTelefone()+"\t "+contato.getEmail());
    }

    public List<Contato> filtrarNaoSincronizados() {
        ContatoOpenHelper a = new ContatoOpenHelper(this.context);
        return a.filtrarNaoSincronizados();
    }
}
