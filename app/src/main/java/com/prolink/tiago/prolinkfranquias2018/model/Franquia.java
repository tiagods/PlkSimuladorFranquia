package com.prolink.tiago.prolinkfranquias2018.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Franquia implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nome;
    private int ativo=1;
    private Calendar lastUpdate;
    private Calendar criadoEm;
    private Set<FranquiaPacote> pacotes= new HashSet<>();

    private TipoServico tipo = TipoServico.SERVICO;

    public Franquia(){}

    public Franquia(String nome){this.nome=nome;}

    public Franquia(Long id,String nome, int ativo){
        this.id = id;
        this.nome=nome;
        this.ativo=ativo;
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Franquia franquia = (Franquia) o;
        return id == franquia.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Calendar getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Calendar criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Set<FranquiaPacote> getPacotes() {
        return pacotes;
    }

    public void setPacotes(Set<FranquiaPacote> pacotes) {
        this.pacotes = pacotes;
    }

    public TipoServico getTipo() {
        return tipo;
    }

    public void setTipo(TipoServico tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
