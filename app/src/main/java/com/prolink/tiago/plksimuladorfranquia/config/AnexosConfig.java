package com.prolink.tiago.plksimuladorfranquia.config;

import com.prolink.tiago.plksimuladorfranquia.model.Anexo;

import java.util.HashSet;
import java.util.Set;

public class AnexosConfig {
    private static AnexosConfig instance;
    private Set<Anexo> anexoList = new HashSet<>();

    public static AnexosConfig getInstance() {
        if(instance==null) instance = new AnexosConfig();
        return instance;
    }

    public AnexosConfig(){
        initializer();
    }
    public void initializer() {
        double linha1i = 0.00;
        double linha1f = 180000.00;

        double linha2i = 180000.01;
        double linha2f = 360000.00;

        double linha3i = 360000.01;
        double linha3f = 720000.00;

        double linha4i = 720000.01;
        double linha4f = 1800000.00;

        double linha5i = 1800000.01;
        double linha5f = 3600000.00;

        double linha6i = 3600000.01;
        double linha6f = 4800000.00;

        //anexo 1
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO1, linha1i, linha1f, 4.00, 0.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO1, linha2i, linha2f, 7.30, 5940.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO1, linha3i, linha3f, 9.50, 13860.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO1, linha4i, linha4f, 10.70, 22500.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO1, linha5i, linha5f, 14.30, 87300.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO1, linha6i, linha6f, 19.00, 378000.00));

        //anexo 2
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO2, linha1i, linha1f, 4.50, 0.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO2, linha2i, linha2f, 7.80, 5940.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO2, linha3i, linha3f, 10.00, 13860.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO2, linha4i, linha4f, 11.20, 22500.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO2, linha5i, linha5f, 14.70, 85500.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO2, linha6i, linha6f, 30.00, 720000.00));

        //anexo 3
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO3, linha1i, linha1f, 6, 0.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO3, linha2i, linha2f, 11.20, 9360.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO3, linha3i, linha3f, 13.50, 17640.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO3, linha4i, linha4f, 16.00, 35640.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO3, linha5i, linha5f, 21.00, 125640.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO3, linha6i, linha6f, 33.00, 648000.00));

        //anexo 4
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO4, linha1i, linha1f, 4.50, 0.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO4, linha2i, linha2f, 9.00, 8100.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO4, linha3i, linha3f, 10.20, 12420.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO4, linha4i, linha4f, 14.00, 39780.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO4, linha5i, linha5f, 22.00, 183780.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO4, linha6i, linha6f, 33.00, 828000.00));

        //anexo 4
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO5, linha1i, linha1f, 15.50, 0.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO5, linha2i, linha2f, 18.00, 4500.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO5, linha3i, linha3f, 19.50, 9900.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO5, linha4i, linha4f, 20.50, 17100.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO5, linha5i, linha5f, 23.00, 62100.00));
        anexoList.add(new Anexo(Anexo.Enquadramento.ANEXO5, linha6i, linha6f, 30.50, 540000.00));
    }

    public Set<Anexo> getAnexoList() {
        return anexoList;
    }
}
