package com.prolink.tiago.plksimuladorfranquia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.prolink.tiago.plksimuladorfranquia.R;
import com.prolink.tiago.plksimuladorfranquia.config.AnexosConfig;
import com.prolink.tiago.plksimuladorfranquia.model.Anexo;
import com.prolink.tiago.plksimuladorfranquia.model.Faturamento;
import com.prolink.tiago.plksimuladorfranquia.model.FranquiaPacote;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
            contruirTabelaFranquia(((FranquiaPacote)consumo));
        }
        List<Anexo> anexos = receberAnexos(consumo.getFaturamento());
        //mostrar nome dos anexos disponíveis
        String anexosNome="";
        Anexo anexoMenor = null;
        for(Anexo a : anexos){
            if(anexoMenor==null || anexoMenor.getAliquota()> a.getAliquota()) anexoMenor = a;

            anexosNome+=a.getEnquadramento()+"->"+
                    a.getAliquota()+"% = "+
                    a.getValorImposto(consumo.getFaturamento())+
                    "\n";
        }
        texto1.setText(anexosNome);

        texto2.setText("Para se enquadrar no"+anexoMenor.getEnquadramento()+" recomendamos que se tenha um ProLabore Mensal mínimo de R$");
        //Para se enquadrar no Anexo XXX recomendamos que se tenha um ProLabore Mensal de R$

        texto3.setText("Considerando o "+anexoMenor.getEnquadramento()+", sua empresa pagará mensalmente "+
                anexoMenor.getValorImposto(consumo.getFaturamento())+
                " de tributos.");
        //texto2.setText("No primeiro mês sua franquia terá um custo de R$xxx, nos meses seguintes, R$xxx somando R$xxx de impostos e R$xxx de prolabore");
        contruirTabela(consumo,anexoMenor);
    }
    public void line(){

    }
    public TextView column(String text, int size, int backgroundColor, int foregroundColor){
        TextView tx = new TextView(this);
        tx.setText(text);
        tx.setTextSize(size);
        tx.setBackgroundResource(backgroundColor);
        tx.setTextColor(foregroundColor);
        return tx;
    }
    public void concluir() {
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
        tr1.addView(column("",textSize,colorLine1,foregroundColor));
        //coluna 2
        tr1.addView(column("%",textSize,colorLine1,foregroundColor));
        //coluna 3
        tr1.addView(column("Valor",textSize,colorLine1,foregroundColor));
        franquiaTable.addView(tr1);
        //line separator 1
        //franquiaTable.addView(new View(this));

        //linha 2
        TableRow tr2 = new TableRow(this);
        tr2.setLayoutParams(row);
        //coluna 1
        tr2.addView(column("Investimento",textSize,colorLine2,foregroundColor));
        //coluna 2
        tr2.addView(column("--",textSize,colorLine2,foregroundColor));
        //coluna 3
        tr2.addView(column(nf.format(franquiaPacote.getInvestimento()),textSize,colorLine2,foregroundColor));
        franquiaTable.addView(tr2);
        //line separator 2
//            franquiaTable.addView(new View(this));

        //linha 3
        TableRow tr3 = new TableRow(this);
        tr3.setLayoutParams(row);
        //coluna 1
        tr3.addView(column("Faturamento",textSize,colorLine3,foregroundColor));
        //coluna 2
        tr3.addView(column("--",textSize,colorLine3,foregroundColor));
        //coluna 3
        tr3.addView(column(nf.format(franquiaPacote.getFaturamento()),textSize,colorLine3,foregroundColor));
        franquiaTable.addView(tr3);
        //line separator 3
//            franquiaTable.addView(new View(this));

        //linha 4
        TableRow tr4 = new TableRow(this);
        tr4.setLayoutParams(row);
        //coluna 1
        tr4.addView(column("Retorno",textSize,colorLine4,foregroundColor));
        //coluna 2
        tr4.addView(column("--",textSize,colorLine4,foregroundColor));
        //coluna 3
        tr4.addView(column(franquiaPacote.getPrevisao(),textSize,colorLine4,foregroundColor));
        franquiaTable.addView(tr4);
        //line separator 3
//            franquiaTable.addView(new View(this));
        linearLayout.addView(franquiaTable);
    }
    public void contruirTabela(Faturamento faturamento, Anexo anexo){
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
        tr1.addView(column("",textSize,colorLine1,foregroundColor));
        //coluna 2
        tr1.addView(column("%",textSize,colorLine1,foregroundColor));
        //coluna 3
        tr1.addView(column("Valor",textSize,colorLine1,foregroundColor));
        franquiaTable.addView(tr1);
        //line separator 1
        //franquiaTable.addView(new View(this));

        //linha 2
        TableRow tr2 = new TableRow(this);
        tr2.setLayoutParams(row);
        //coluna 1
        tr2.addView(column("Faturamento",textSize,colorLine2,foregroundColor));
        //coluna 2
        tr2.addView(column("--",textSize,colorLine2,foregroundColor));
        //coluna 3
        tr2.addView(column(nf.format(faturamento.getFaturamento()),textSize,colorLine2,foregroundColor));
        franquiaTable.addView(tr2);
        //line separator 2
//            franquiaTable.addView(new View(this));

        //linha 3
        TableRow tr3 = new TableRow(this);
        tr3.setLayoutParams(row);
        //coluna 1
        tr3.addView(column("ProLabore",textSize,colorLine3,foregroundColor));
        //coluna 2
        tr3.addView(column("--",textSize,colorLine3,foregroundColor));
        //coluna 3
        tr3.addView(column(nf.format(faturamento.getProLabore()),textSize,colorLine3,foregroundColor));
        franquiaTable.addView(tr3);
        //line separator 3
//            franquiaTable.addView(new View(this));

        //linha 4
        TableRow tr4 = new TableRow(this);
        tr4.setLayoutParams(row);
        //coluna 1
        tr4.addView(column("Imposto",textSize,colorLine4,foregroundColor));
        //coluna 2
        tr4.addView(column(anexo.getAliquota()+"%",textSize,colorLine4,foregroundColor));
        //coluna 3
        tr4.addView(column(anexo.getValorImposto(faturamento.getFaturamento()),textSize,colorLine4,foregroundColor));
        franquiaTable.addView(tr4);
        //line separator 3
//      franquiaTable.addView(new View(this));
        linearLayout.addView(franquiaTable);
    }
    public void refazer() {
        Intent intent = new Intent(this,FranquiaEscolhaActivity.class);
        startActivity(intent);
    }
    private List<Anexo> receberAnexos(double faturamento){
        Set<Anexo> anexos = AnexosConfig.getInstance().getAnexoList();
        List<Anexo> newAnexo = new ArrayList<>();
        for(Anexo anexo : anexos){
//            if(anexo.estaDentroDoLimite(faturamento)) newAnexo.add(anexo);
            if(anexo.estaDentroDoLimite(faturamento) && anexo.getEnquadramento().equals(Anexo.Enquadramento.ANEXO3)
                    || anexo.estaDentroDoLimite(faturamento) && anexo.getEnquadramento().equals(Anexo.Enquadramento.ANEXO5)) newAnexo.add(anexo);
        }
        Comparator<Anexo> comparator = new Comparator<Anexo>() {
            @Override
            public int compare(Anexo o1, Anexo o2) {
                return o1.getEnquadramento().getOrdenacao()-o2.getEnquadramento().getOrdenacao();
            }
        };
        Collections.sort(newAnexo,comparator);
        return newAnexo;
    }
}
