package com.prolink.tiago.prolinkfranquias2018.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class LucroPresumido implements Serializable {
    private NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));

    private double icms=0;
    private double totalIcms=0;

    private double iss=0;
    private double totalIss=0;

    private double cofins=0;
    private double totalCofins=0;

    private double pis=0;
    private double totalPis=0;

    private double irpj=0;
    private double totalIrpj=0;

    private double csll=0;
    private double totalCsll=0;

    private double inss=0;
    private double totalInss=0;
    private double gps=0;
    private double totalGps=0;
    private double irrf=0;
    private double totalIrrf=0;

    private double imposto = 0;
    private double totalImposto=0;

    private TipoServico tipo = TipoServico.SERVICO;

    public LucroPresumido(TipoServico tipo, double iss, double cofins, double pis, double irpj, double csll){
        this.tipo=tipo;
        this.iss = iss;
        this.cofins=cofins;
        this.pis=pis;
        this.irpj=irpj;
        this.csll=csll;
    }

    public double getIcms() {
        return icms;
    }

    public void setIcms(double icms) {
        this.icms = icms;
    }

    public String getTotalIcms() {
        return nf.format(totalIcms);
    }

    public void setTotalIcms(double totalIcms) {
        this.totalIcms = totalIcms;
    }

    public double getIss() {
        return iss;
    }

    public void setIss(double iss) {
        this.iss = iss;
    }

    public String getTotalIss() {
        return nf.format(totalIss);
    }

    public void setTotalIss(double totalIss) {
        this.totalIss = totalIss;
    }

    public double getCofins() {
        return cofins;
    }

    public void setCofins(double cofins) {
        this.cofins = cofins;
    }

    public String getTotalCofins() {
        return nf.format(totalCofins);
    }

    public void setTotalCofins(double totalCofins) {
        this.totalCofins = totalCofins;
    }

    public double getPis() {
        return pis;
    }

    public void setPis(double pis) {
        this.pis = pis;
    }

    public String getTotalPis() {
        return nf.format(totalPis);
    }

    public void setTotalPis(double totalPis) {
        this.totalPis = totalPis;
    }

    public double getIrpj() {
        return irpj;
    }

    public void setIrpj(double irpj) {
        this.irpj = irpj;
    }

    public String getTotalIrpj() {
        return nf.format(totalIrpj);
    }

    public void setTotalIrpj(double totalIrpj) {
        this.totalIrpj = totalIrpj;
    }

    public double getCsll() {
        return csll;
    }

    public void setCsll(double csll) {
        this.csll = csll;
    }

    public String getTotalCsll() {
        return nf.format(totalCsll);
    }

    public void setTotalCsll(double totalCsll) {
        this.totalCsll = totalCsll;
    }

    public double getInss() {
        return inss;
    }

    public void setInss(double inss) {
        this.inss = inss;
    }

    public String getTotalInss() {
        return nf.format(totalInss);
    }

    public void setTotalInss(double totalInss) {
        this.totalInss = totalInss;
    }

    public double getGps() {
        return gps;
    }

    public void setGps(double gps) {
        this.gps = gps;
    }

    public String getTotalGps() {
        return nf.format(totalGps);
    }

    public void setTotalGps(double totalGps) {
        this.totalGps = totalGps;
    }

    public double getIrrf() {
        return irrf;
    }

    public void setIrrf(double irrf) {
        this.irrf = irrf;
    }

    public String getTotalIrrf() {
        return nf.format(totalIrrf);
    }

    public void setTotalIrrf(double totalIrrf) {
        this.totalIrrf = totalIrrf;
    }

    public double getImposto() {
        return imposto;
    }

    public void setImposto(double imposto) {
        this.imposto = imposto;
    }

    public String getTotalImposto() {
        return nf.format(totalImposto);
    }

    public void setTotalImposto(double totalImposto) {
        this.totalImposto = totalImposto;
    }

    public TipoServico getTipo() {
        return tipo;
    }

    public void setTipo(TipoServico tipo) {
        this.tipo = tipo;
    }

    public void calculaImposto(double faturamento){
        this.totalIcms = (faturamento * icms)/100;
        this.totalIss = (faturamento * iss)/100;
        this.totalCofins = (faturamento * cofins)/100;
        this.totalPis = (faturamento * pis)/100;
        this.totalIrpj= (faturamento * irpj)/100;
        this.totalCsll = (faturamento * csll)/100;
        this.totalInss = (faturamento * inss)/100;
        this.totalGps = (faturamento * gps)/100;
        this.totalIrrf = (faturamento * irrf)/100;
        this.totalImposto = totalIss+totalIcms+totalCofins+
                totalPis+totalIrpj+totalCsll+totalInss+totalGps+totalIrrf;
        this.imposto = (totalImposto/faturamento)*100;
        this.imposto=new BigDecimal(this.imposto).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
