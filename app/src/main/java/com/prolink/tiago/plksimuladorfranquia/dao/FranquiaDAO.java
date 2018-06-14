package com.prolink.tiago.plksimuladorfranquia.dao;

import android.content.Context;
import android.util.Log;

import com.prolink.tiago.plksimuladorfranquia.helper.FranquiaOpenHelper;
import com.prolink.tiago.plksimuladorfranquia.model.Franquia;
import com.prolink.tiago.plksimuladorfranquia.model.FranquiaPacote;

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
            Log.v("PACOTE/INSERT", pacote.getNome());
            db.insertPacote(pacote);
        }
        db.close();
    }
    public Set<FranquiaPacote> getPacotes(Franquia franquia){
        db = new FranquiaOpenHelper(this.context);
        Set<FranquiaPacote> pacotes = db.getPacotes(franquia.getId());
        db.close();
//        Set<FranquiaPacote> pacotes = new HashSet<>();
//        FranquiaPacote fp = new FranquiaPacote();
//        fp.setFranquia_id(franquia.getId());
//        fp.setNome("Basic");
//        fp.setCusto(0.00);
//        fp.setInvestimento(200000.00);
//        fp.setPrevisao("2 anos");
//        fp.setFaturamento(50000.00);
//        fp.setIcms(1.00);
//        fp.setValorBaseICMS(50000.00);
//        fp.setProLabore(5000.00);
//        fp.setTipo(franquia.getTipo());
//        pacotes.add(fp);
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
//        List<Franquia> fraquias = new ArrayList<Franquia>();
//        fraquias.add(new Franquia(-1L,"Selecione uma franquia",0));
//        fraquias.add(new Franquia(1L,"Mundo Cheff",1));
//        fraquias.add(new Franquia(2L,"Franquia Ficticia",1));
//        fraquias.add(new Franquia(3L,"Franquia Exemplo",1));
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
