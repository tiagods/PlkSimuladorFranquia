package com.prolink.tiago.prolinkfranquias2018.model;

public enum TipoServico{
    COMERCIO("Comercio"),SERVICO("Servico");
    private String descricao;
    TipoServico(String descricao){
        this.descricao=descricao;
    }
    public String getDescricao() {
        return descricao;
    }
}
