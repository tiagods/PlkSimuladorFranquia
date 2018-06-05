package com.prolink.tiago.plksimuladorfranquia.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.prolink.tiago.plksimuladorfranquia.MainActivity;
import com.prolink.tiago.plksimuladorfranquia.R;
import com.prolink.tiago.plksimuladorfranquia.config.AnexosConfig;
import com.prolink.tiago.plksimuladorfranquia.model.Anexo;
import com.prolink.tiago.plksimuladorfranquia.model.Faturamento;
import com.prolink.tiago.plksimuladorfranquia.model.FaturamentoConsumo;
import com.prolink.tiago.plksimuladorfranquia.model.Franquia;
import com.prolink.tiago.plksimuladorfranquia.model.FranquiaPacote;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class ResultadoActivity extends AppCompatActivity {

    NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
    LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        BootstrapButton concluir = (BootstrapButton)findViewById(R.id.buttonConcluir);
        BootstrapButton refazer = (BootstrapButton)findViewById(R.id.buttonRefazer);
        linearLayout = (LinearLayout)findViewById(R.id.pnTabelas);

        TextView textoPrincipal = findViewById(R.id.txPrincipal);
        TextView texto1 = findViewById(R.id.txResultado1);
        TextView texto2 = findViewById(R.id.txResultado2);
        TextView texto3 = findViewById(R.id.txResultado3);

        refazer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refazer();
            }
        });
        concluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                concluir();
            }
        });

        Faturamento consumo = (Faturamento) getIntent().getSerializableExtra("faturamento");
        if(consumo instanceof FranquiaPacote){
            //remover
            consumo = new FranquiaPacote();
            consumo.setFaturamento(20000);
            ((FranquiaPacote)consumo).setInvestimento(100000);
            ((FranquiaPacote)consumo).setPrevisao("2 anos");
            ((FranquiaPacote)consumo).setNome("Pacote 1");

            contruirTabelaFranquia(((FranquiaPacote)consumo));
        }


        Set<Anexo> anexos = receberAnexos(consumo.getFaturamento());
        String anexosNome="";
        Anexo anexoMenor = null;
        for(Anexo a : anexos){
            if(anexoMenor==null || anexoMenor.getAliquota()> a.getAliquota()) anexoMenor = a;

            anexosNome+=a.getEnquadramento()+"->"+
                    a.getAliquota()+"% = "+
                    nf.format(consumo.getFaturamento()*a.getAliquota()/100)+
                    "\n";
        }
        texto1.setText(anexosNome);

        texto2.setText("Para se enquadrar no Anexo XXX recomendamos que se tenha um ProLabore Mensal de R$");
        //Para se enquadrar no Anexo XXX recomendamos que se tenha um ProLabore Mensal de R$

        texto3.setText("Considerando o Anexo xxx, sua empresa empresa pagará mensalmente "+
                nf.format(consumo.getFaturamento()*anexoMenor.getAliquota()/100)+
                " de tributos.");
        //texto2.setText("No primeiro mês sua franquia terá um custo de R$xxx, nos meses seguintes, R$xxx somando R$xxx de impostos e R$xxx de prolabore");

    }
    public TextView criarTextView(String text, int size, int backgroundColor, int foregroundColor){
        TextView tx = new TextView(this);
        tx.setText(text);
        tx.setTextSize(size);
        tx.setBackgroundResource(backgroundColor);
        tx.setTextColor(foregroundColor);
        return tx;
    }
    public void concluir() {
        //Intent intent = new Intent(this,MainActivity.class);
        Intent intent = new Intent(this,FimActivity.class);
        startActivity(intent);
    }

    public void contruirTabelaFranquia(FranquiaPacote franquiaPacote){
        int textSize = 24;
        int colorLine1 = R.color.colorTableBlue;
        int colorLine2 = R.color.colorTableGrey;
        int colorLine3 = R.color.colorTableGreen;
        int colorLine4 = R.color.colorTableRed;
        int foregroundColor = R.color.colorTableTextWhite;
        int column1Size = 150;

        TableLayout franquiaTable = new TableLayout(this);
        TableLayout.LayoutParams lp = new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        franquiaTable.setLayoutParams(lp);

        TableLayout.LayoutParams row = new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //linha 1
        TableRow tr1 = new TableRow(this);
        tr1.setLayoutParams(row);
        //coluna 1
        tr1.addView(criarTextView("",textSize,colorLine1,foregroundColor));
        //coluna 2
        tr1.addView(criarTextView("%",textSize,colorLine1,foregroundColor));
        //coluna 3
        tr1.addView(criarTextView("Valor",textSize,colorLine1,foregroundColor));
        franquiaTable.addView(tr1);
        //line separator 1
        //franquiaTable.addView(new View(this));

        //linha 2
        TableRow tr2 = new TableRow(this);
        tr2.setLayoutParams(row);
        //coluna 1
        tr2.addView(criarTextView("Investimento",textSize,colorLine2,foregroundColor));
        //coluna 2
        tr2.addView(criarTextView("--",textSize,colorLine2,foregroundColor));
        //coluna 3
        tr2.addView(criarTextView(nf.format(franquiaPacote.getInvestimento()),textSize,colorLine2,foregroundColor));
        franquiaTable.addView(tr2);
        //line separator 2
//            franquiaTable.addView(new View(this));

        //linha 3
        TableRow tr3 = new TableRow(this);
        tr3.setLayoutParams(row);
        //coluna 1
        tr3.addView(criarTextView("Faturamento",textSize,colorLine3,foregroundColor));
        //coluna 2
        tr3.addView(criarTextView("--",textSize,colorLine3,foregroundColor));
        //coluna 3
        tr3.addView(criarTextView(nf.format(franquiaPacote.getFaturamento()),textSize,colorLine3,foregroundColor));
        franquiaTable.addView(tr3);
        //line separator 3
//            franquiaTable.addView(new View(this));

        //linha 4
        TableRow tr4 = new TableRow(this);
        tr4.setLayoutParams(row);
        //coluna 1
        tr4.addView(criarTextView("Retorno",textSize,colorLine4,foregroundColor));
        //coluna 2
        tr4.addView(criarTextView("--",textSize,colorLine4,foregroundColor));
        //coluna 3
        tr4.addView(criarTextView(franquiaPacote.getPrevisao(),textSize,colorLine4,foregroundColor));
        franquiaTable.addView(tr4);
        //line separator 3
//            franquiaTable.addView(new View(this));
        linearLayout.addView(franquiaTable);
    }
    public void refazer() {
        Intent intent = new Intent(this,FranquiaEscolhaActivity.class);
        startActivity(intent);
    }
    private Set<Anexo> receberAnexos(double faturamento){
        Set<Anexo> anexos = AnexosConfig.getInstance().getAnexoList();
        Set<Anexo> newAnexo = new HashSet<>();
        for(Anexo anexo : anexos){
            if(anexo.estaDentroDoLimite(faturamento) && anexo.getEnquadramento().equals(Anexo.Enquadramento.ANEXO3)
                    || anexo.estaDentroDoLimite(faturamento) && anexo.getEnquadramento().equals(Anexo.Enquadramento.ANEXO5)) newAnexo.add(anexo);
        }
        return newAnexo;
    }
}
