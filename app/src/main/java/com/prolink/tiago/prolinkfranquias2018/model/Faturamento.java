package com.prolink.tiago.prolinkfranquias2018.model;

import java.io.Serializable;

public abstract class Faturamento implements Serializable {
    private static final long serialVersionUID = 1L;
    private double faturamento = 0.00;
    private double icms = 0.00;
    private double proLabore =0.00;
    private double valorBaseICMS=0.00;
    private TipoServico tipo = TipoServico.SERVICO;

    public double getFaturamento() {
        return faturamento;
    }

    public void setFaturamento(double faturamento) {
        this.faturamento = faturamento;
    }

    public double getIcms() {
        return icms;
    }

    public void setIcms(double icms) {
        this.icms = icms;
    }

    public double getProLabore() {
        return proLabore;
    }

    public void setProLabore(double proLabore) {
        this.proLabore = proLabore;
    }

    public double getValorBaseICMS() {
        return valorBaseICMS;
    }

    public void setValorBaseICMS(double valorBaseICMS) {
        this.valorBaseICMS = valorBaseICMS;
    }

    public TipoServico getTipo() {
        return tipo;
    }

    public void setTipo(TipoServico tipo) {
        this.tipo = tipo;
    }
}
