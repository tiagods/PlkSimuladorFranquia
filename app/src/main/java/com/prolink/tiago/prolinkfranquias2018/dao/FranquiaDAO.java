package com.prolink.tiago.prolinkfranquias2018.dao;

import android.content.Context;
import android.util.Log;

import com.prolink.tiago.prolinkfranquias2018.helper.FranquiaOpenHelper;
import com.prolink.tiago.prolinkfranquias2018.model.Franquia;
import com.prolink.tiago.prolinkfranquias2018.model.FranquiaPacote;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FranquiaDAO {
    private Context context;
    private FranquiaOpenHelper db;

    public FranquiaDAO(Context context){
        this.context=context;
    }
    public void dropTable(){
        db = new FranquiaOpenHelper(this.context);
        db.drop();
        db.close();
    }
    public void cadastrar(Franquia franquia){
        db = new FranquiaOpenHelper(this.context);
        boolean existe = db.verificarSeExiste(franquia);
        boolean ehigual = db.verificarSeIgual(franquia);
        if(existe && !ehigual) {
            db.update(franquia);
            db.deletePacote(franquia);
        }
        else if(ehigual){
            db.close();
            return;
        }
        else
            db.insert(franquia);
        for(FranquiaPacote pacote : franquia.getPacotes()){
            db.insertPacote(pacote);
        }
        db.close();
    }
    public Set<FranquiaPacote> getPacotes(Franquia franquia){
        db = new FranquiaOpenHelper(this.context);
        Set<FranquiaPacote> pacotes = db.getPacotes(franquia.getId());
        db.close();
        return pacotes;
    }
    public void atualizar(Franquia franquia){
        db = new FranquiaOpenHelper(this.context);
        db.update(franquia);
        db.close();
    }
    public List<Franquia> listarAtivos() {
        db = new FranquiaOpenHelper(this.context);
        List<Franquia> ativos = new ArrayList<>();
        Franquia f = new Franquia(-1L,"Selecione uma franquia",1);
        ativos.add(f);
        ativos.addAll(db.receberAtivos());
        db.close();
        return ativos;
    }
    public Franquia getUltimaVersao(){
        db = new FranquiaOpenHelper(this.context);
        Franquia franquia = db.pegarMaisNovo();
        db.close();
        return franquia;
    }

    public List<Franquia> getAll(){
        db = new FranquiaOpenHelper(this.context);
        List<Franquia> franquias = new ArrayList<>();
        franquias.addAll(db.getAll());
        db.close();
        return franquias;
    }
}
