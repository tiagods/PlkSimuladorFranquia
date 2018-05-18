package com.prolink.tiago.plksimuladorfranquia.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Contato implements Serializable{
    private int id;
    private String nome;
    private String telefone;
    private String email;
    private int sincronizado=0;
    private BigDecimal faturamento=new BigDecimal(0.00);
    private BigDecimal prolabore=new BigDecimal(0.00);

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

    public int getSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(int sincronizado) {
        this.sincronizado = sincronizado;
    }

    public BigDecimal getFaturamento() {
        return faturamento;
    }

    public void setFaturamento(BigDecimal faturamento) {
        this.faturamento = faturamento;
    }

    public BigDecimal getProlabore() {
        return prolabore;
    }

    public void setProlabore(BigDecimal prolabore) {
        this.prolabore = prolabore;
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
