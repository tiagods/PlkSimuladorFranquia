package com.prolink.tiago.plksimuladorfranquia.model;

import java.io.Serializable;
import java.math.BigDecimal;

public abstract class FaturamentoConsumo implements Serializable{
    public enum Tipo{
        COMERCIO,SERVICO;
    }
    private BigDecimal faturamento=new BigDecimal(0.00);
    private BigDecimal prolabore=new BigDecimal(0.00);
    private double aliquotaICMS=0;
    private Tipo tipo = Tipo.SERVICO;

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

    public double getAliquotaICMS() {
        return aliquotaICMS;
    }

    public void setAliquotaICMS(double aliquotaICMS) {
        this.aliquotaICMS = aliquotaICMS;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
