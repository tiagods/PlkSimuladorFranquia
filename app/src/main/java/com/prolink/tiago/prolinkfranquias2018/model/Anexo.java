package com.prolink.tiago.prolinkfranquias2018.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class Anexo implements Serializable{
    private NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
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
        ANEXO1(1,"Anexo I"),ANEXO2(2,"Anexo II"),ANEXO3(3,"Anexo III"),ANEXO4(4,"Anexo IV"),ANEXO5(5,"Anexo V");
        private int ordenacao;
        private String descricao;
        Enquadramento(int ordenacao,String descricao){
            this.ordenacao = ordenacao;
            this.descricao=descricao;
        }
        @Override
        public String toString() {
            return this.descricao+" do Simples Nacional";
        }
        public int getOrdenacao() {
            return ordenacao;
        }
    }
    private Enquadramento enquadramento;
    private double valorInicial = 0.00;
    private double valorFinal = 0.00;
    private double aliquota;
    private double descontoRecolhimento = 0.00;
    private double aliquotaFinal=0.00;

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

    public boolean estaDentroDoLimite(double faturamento){
        return (faturamento >= valorInicial && faturamento<=valorFinal);
    }
    public String getValorImposto(double faturamento){
        return nf.format((faturamento*aliquotaFinal)/100);
    }
    public void calcularAliquota(double faturamento){
        double faturamentoAnual = 12 * faturamento;
        if(faturamentoAnual>180000.00){
            double alFn = (faturamentoAnual*aliquota)/100;
            double valor = (alFn-descontoRecolhimento)/faturamentoAnual;
            aliquotaFinal = (valor*100);
        }
        else
            aliquotaFinal = aliquota;
    }

    public double getAliquotaFinal() {
        return new BigDecimal(aliquotaFinal).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public String toString() {
        return "Anexo{" +
                "enquadramento=" + enquadramento +
                ", valorInicial=" + valorInicial +
                ", valorFinal=" + valorFinal +
                ", aliquota=" + aliquota +
                ", descontoRecolhimento=" + descontoRecolhimento +
                '}';
    }
}
