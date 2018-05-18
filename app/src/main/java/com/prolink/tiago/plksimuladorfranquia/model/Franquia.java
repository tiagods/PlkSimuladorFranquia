package com.prolink.tiago.plksimuladorfranquia.model;

import java.io.Serializable;
import java.util.Objects;

public class Franquia implements Serializable{
    private int id;
    private String nome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

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
}