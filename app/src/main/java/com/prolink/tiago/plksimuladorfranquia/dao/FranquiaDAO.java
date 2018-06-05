package com.prolink.tiago.plksimuladorfranquia.dao;

import android.content.Context;
import android.util.Log;

import com.prolink.tiago.plksimuladorfranquia.helper.FranquiaOpenHelper;
import com.prolink.tiago.plksimuladorfranquia.model.Contato;
import com.prolink.tiago.plksimuladorfranquia.model.Franquia;
import com.prolink.tiago.plksimuladorfranquia.model.FranquiaPacote;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FranquiaDAO {
    private Context context;
    public FranquiaDAO(Context context){
        this.context=context;
        FranquiaOpenHelper a = new FranquiaOpenHelper(this.context);
        //a.drop();
        //Log.v("MYAPP","Limpando Tabela de Contatos");
        //for(Franquia c : a.getAll()){
        //}
    }

    public Franquia cadastrar(Long id, String nome, int ativo){
        FranquiaOpenHelper a = new FranquiaOpenHelper(this.context);
        Franquia franquia = new Franquia();
        franquia.setId(id);
        franquia.setNome(nome);
        franquia.setAtivo(ativo);
        boolean existe = a.verificarSeExiste(franquia);
        if(existe){
            a.update(franquia);
        }
        else
            a.insert(franquia);
        for(Franquia c : a.getAll()){
            Log.v("MYAPP",c.getId()+"\t "+c.getNome()+"\t "+c.getAtivo());
        }
        return franquia;
    }
    public Set<FranquiaPacote> getPacotes(Franquia franquia){
        FranquiaOpenHelper a = new FranquiaOpenHelper(this.context);
        return a.getPacotes(franquia.getId());
    }

    public void atualizar(Franquia franquia){
        FranquiaOpenHelper a = new FranquiaOpenHelper(this.context);
        a.update(franquia);
        //Log.v("MYAPP",contato.getId()+"\t "+contato.getNome()+"\t "+contato.getTelefone()+"\t "+contato.getEmail());
    }
    public List<Franquia> listarAtivos() {
        //ContatoOpenHelper a = new ContatoOpenHelper(this.context);
        //a.receberAtivos()
        List<Franquia> fraquias = new ArrayList<Franquia>();
        fraquias.add(new Franquia(-1L,"Selecione uma franquia",0));
        fraquias.add(new Franquia(1L,"Mundo Cheff",1));
        fraquias.add(new Franquia(2L,"Franquia 2",1));
        fraquias.add(new Franquia(3L,"Franquia 3",1));
        return fraquias;
    }
}
