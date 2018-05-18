package com.prolink.tiago.plksimuladorfranquia.model;


public class Anexo {
    public Anexo(){

    }
    public Anexo(Enquadramento enquadramento,double valorInicial, double valorFinal, double aliquota, double descontoRecolhimento){
        this.enquadramento=enquadramento;
        this.valorInicial=valorInicial;
        this.valorFinal=valorFinal;
        this.aliquota=aliquota;
        this.descontoRecolhimento=descontoRecolhimento;
    }

    public enum Enquadramento{
        ANEXO1,ANEXO2,ANEXO3,ANEXO4,ANEXO5;
    }
    private Enquadramento enquadramento;
    private double valorInicial = 0.00;
    private double valorFinal = 0.00;
    private double aliquota;
    private double descontoRecolhimento = 0.00;

    public double getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(double valorInicial) {
        this.valorInicial = valorInicial;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public Enquadramento getEnquadramento() {
        return enquadramento;
    }

    public void setEnquadramento(Enquadramento enquadramento) {
        this.enquadramento = enquadramento;
    }

    public double getAliquota() {
        return aliquota;
    }

    public void setAliquota(double aliquota) {
        this.aliquota = aliquota;
    }

    public double getDescontoRecolhimento() {
        return descontoRecolhimento;
    }

    public void setDescontoRecolhimento(double descontoRecolhimento) {
        this.descontoRecolhimento = descontoRecolhimento;
    }
}
