package com.prolink.tiago.plksimuladorfranquia.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Franquia implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nome;
    private int ativo=1;
    private Calendar lastUpdate;
    private Calendar criadoEm;
    private Set<FranquiaPacote> pacotes= new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Franquia franquia = (Franquia) o;
        return id == franquia.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
