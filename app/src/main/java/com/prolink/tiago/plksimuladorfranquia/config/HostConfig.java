package com.prolink.tiago.plksimuladorfranquia.config;

public class HostConfig {
    private static HostConfig instance;
    public static HostConfig getInstance(){
        if(instance==null) instance = new HostConfig();
        return instance;
    }
    public final String HOST = "http://192.168.1.59:8080/api/";
    public final String CONTATO = "contatos";
    public final String FRANQUIA = "franquias";
}
