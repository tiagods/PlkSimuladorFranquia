package com.prolink.tiago.plksimuladorfranquia.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Contato  extends FaturamentoConsumo implements Serializable{
    private int id;
    private String nome;
    private String telefone;
    private String email;
    private int sincronizado=1;
    private int contato_id=0;

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSincronizado() {return sincronizado;}

    public void setSincronizado(int sincronizado) {this.sincronizado = sincronizado;}

    public int getContato_id() {return contato_id;}

    public void setContato_id(int contato_id) {
        this.contato_id = contato_id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contato contato = (Contato) o;
        return id == contato.id &&
                Objects.equals(email, contato.email);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }


}
