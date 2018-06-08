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
        db = new FranquiaOpenHelper(this.context);
        //a.drop();
        //Log.v("MYAPP","Limpando Tabela de Contatos");
        //for(Franquia c : a.getAll()){
        //}
        db.close();
    }

    public Franquia cadastrar(Long id, String nome, int ativo){
        db = new FranquiaOpenHelper(this.context);
        Franquia franquia = new Franquia();
        franquia.setId(id);
        franquia.setNome(nome);
        franquia.setAtivo(ativo);
        boolean existe = db.verificarSeExiste(franquia);
        if(existe){
            db.update(franquia);
        }
        else
            db.insert(franquia);
        for(Franquia c : db.getAll()){
            Log.v("MYAPP",c.getId()+"\t "+c.getNome()+"\t "+c.getAtivo());
        }
        db.close();
        return franquia;
    }
    public Set<FranquiaPacote> getPacotes(Franquia franquia){
//        db = new FranquiaOpenHelper(this.context);
//        Set<FranquiaPacote> pacotes = db.getPacotes(franquia.getId());
//        db.close();
        Set<FranquiaPacote> pacotes = new HashSet<>();
        FranquiaPacote fp = new FranquiaPacote();
        fp.setFranquia_id(franquia.getId());
        fp.setNome("Basic");
        fp.setCusto(0.00);
        fp.setInvestimento(200000.00);
        fp.setPrevisao("2 anos");
        fp.setFaturamento(50000.00);
        fp.setIcms(1.00);
        fp.setValorBaseICMS(50000.00);
        fp.setProLabore(5000.00);
        fp.setTipo(franquia.getTipo());
        pacotes.add(fp);
        return pacotes;
    }

    public void atualizar(Franquia franquia){
        db = new FranquiaOpenHelper(this.context);
        db.update(franquia);
        db.close();
        //Log.v("MYAPP",contato.getId()+"\t "+contato.getNome()+"\t "+contato.getTelefone()+"\t "+contato.getEmail());
    }
    public List<Franquia> listarAtivos() {
        //db = new FranquiaOpenHelper(this.context);
        //db.receberAtivos();
        List<Franquia> fraquias = new ArrayList<Franquia>();
        fraquias.add(new Franquia(-1L,"Selecione uma franquia",0));
        fraquias.add(new Franquia(1L,"Mundo Cheff",1));
        fraquias.add(new Franquia(2L,"Franquia Ficticia",1));
        fraquias.add(new Franquia(3L,"Franquia Exemplo",1));
        return fraquias;
    }
}
