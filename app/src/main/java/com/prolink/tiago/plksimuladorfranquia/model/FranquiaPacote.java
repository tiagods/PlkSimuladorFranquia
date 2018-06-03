package com.prolink.tiago.plksimuladorfranquia.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public class FranquiaPacote implements Serializable{
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nome;
    private BigDecimal investimento = new BigDecimal(0.00);
    private BigDecimal custo = new BigDecimal(0.00);
    private String previsao;
    private BigDecimal faturamento = new BigDecimal(0.00);
    private double icms = 0.00;
    private Calendar lastUpdate;
    private Calendar criadoEm;
    private Franquia franquia;


}
