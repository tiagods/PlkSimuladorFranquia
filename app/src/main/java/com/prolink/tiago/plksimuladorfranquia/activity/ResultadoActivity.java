package com.prolink.tiago.plksimuladorfranquia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.prolink.tiago.plksimuladorfranquia.R;
import com.prolink.tiago.plksimuladorfranquia.config.TributosConfig;
import com.prolink.tiago.plksimuladorfranquia.model.Anexo;
import com.prolink.tiago.plksimuladorfranquia.model.Faturamento;
import com.prolink.tiago.plksimuladorfranquia.model.FranquiaPacote;
import com.prolink.tiago.plksimuladorfranquia.model.LucroPresumido;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        BootstrapButton concluir = (BootstrapButton) findViewById(R.id.buttonConcluir);
        BootstrapButton refazer = (BootstrapButton) findViewById(R.id.buttonRefazer);
        linearLayout = (LinearLayout) findViewById(R.id.pnTabelas);

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

        TableLayout tbFranquia = findViewById(R.id.tbFraquia);

        Faturamento consumo = (Faturamento) getIntent().getSerializableExtra("faturamento");
        if (consumo instanceof FranquiaPacote) {
            preencherTabelaFranquia((FranquiaPacote)consumo);

        }
        else{
            tbFranquia.setVisibility(View.INVISIBLE);
        }

        Anexo anexo = TributosConfig.getInstance().getAnexo(consumo);
        LucroPresumido presumido = TributosConfig.getInstance().getLucroPresumido(consumo);
        presumido.calculaImposto(consumo.getFaturamento());

        preencherTabelaSimples(anexo,consumo);
        preencherTabelaPresumido(presumido);

        String tx1 = "";
        if (anexo!=null) {
            tx1+= anexo.getEnquadramento() + "->" +
                    anexo.getAliquota() + "% = " +
                    anexo.getValorImposto(consumo.getFaturamento()) +
                    "\n";
        }
        if(presumido!=null){
            tx1+= "Lucro Presumido ->" +
                    presumido.getImposto() + "% = " +
                    presumido.getTotalImposto() +
                    "\n";
        }
        texto1.setText(tx1);

        String nome="";
        String valorImposto="";
        double imposto=0;
        if(anexo.getAliquota()>presumido.getImposto()){
            nome = "Lucro Presumido";
            valorImposto = presumido.getTotalImposto();
            imposto = presumido.getImposto();
        }
        else{
            nome = anexo.getEnquadramento().toString();
            valorImposto = anexo.getValorImposto(consumo.getFaturamento());
            imposto = anexo.getAliquota();
        }
        texto2.setText("Considerando o "+nome+", sua empresa pagará mensalmente "+
                valorImposto+
                " de tributos.");
        //texto2.setText("No primeiro mês sua franquia terá um custo de R$xxx, nos meses seguintes, R$xxx somando R$xxx de impostos e R$xxx de prolabore");

        texto3.setText("");

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
    public View view(int color){
        View view = new View(this);
        ViewGroup.LayoutParams vp = new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
        view.setBackgroundResource(color);
        view.setLayoutParams(vp);
        return view;
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
        int black = R.color.black;
        TableLayout franquiaTable = new TableLayout(this);
        TableLayout.LayoutParams lp = new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        franquiaTable.setLayoutParams(lp);

        TableLayout.LayoutParams row = new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        TableRow tr = new TableRow(this);
        tr.setLayoutParams(row);
        tr.addView(column(franquiaPacote.getNome(), textSize,foregroundColor,black));
        franquiaTable.addView(tr);
        franquiaTable.addView(view(black));
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

        franquiaTable.addView(view(black));
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

        franquiaTable.addView(view(black));
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

        franquiaTable.addView(view(black));

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

        franquiaTable.addView(view(black));
        linearLayout.addView(franquiaTable);
    }
    public void contruirTabela(Faturamento faturamento, Anexo anexo){
        int textSize = 24;
        int colorLine1 = R.color.colorTableBlue;
        int colorLine2 = R.color.colorTableGrey;
        int colorLine3 = R.color.colorTableGreen;
        int colorLine4 = R.color.colorTableRed;
        int foregroundColor = R.color.colorTableTextWhite;
        int black = R.color.black;
        int column1Size = 150;

        TableLayout franquiaTable = new TableLayout(this);
        TableLayout.LayoutParams lp = new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        franquiaTable.setLayoutParams(lp);

        TableLayout.LayoutParams row = new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TableRow tr = new TableRow(this);
        tr.setLayoutParams(row);
        tr.addView(column("Base de Calculo", textSize,foregroundColor,black));
        franquiaTable.addView(tr);
        franquiaTable.addView(view(black));
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

        franquiaTable.addView(view(black));
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

        franquiaTable.addView(view(black));
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

        franquiaTable.addView(view(black));
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

        franquiaTable.addView(view(black));


        franquiaTable.setBottom(20);
        //line separator 3
//      franquiaTable.addView(new View(this));
        linearLayout.addView(franquiaTable);
    }
    public void contruirTabelaPresumido(LucroPresumido presumido){
        int textSize = 24;
        int colorLine1 = R.color.colorTableBlue;
        int colorLine2 = R.color.colorTableGrey;
        int colorLine3 = R.color.colorTableGreen;
        int colorLine4 = R.color.colorTableRed;
        int foregroundColor = R.color.colorTableTextWhite;
        int black = R.color.black;
        int column1Size = 150;

        TableLayout franquiaTable = new TableLayout(this);
        TableLayout.LayoutParams lp = new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        franquiaTable.setLayoutParams(lp);

        TableLayout.LayoutParams row = new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TableRow tr = new TableRow(this);
        tr.setLayoutParams(row);
        tr.addView(column("Lucro Presumido", textSize,foregroundColor,black));
        franquiaTable.addView(tr);
        franquiaTable.addView(view(black));
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
        franquiaTable.addView(view(black));

        //linha 2
        TableRow tr2 = new TableRow(this);
        tr2.setLayoutParams(row);
        //coluna 1
        tr2.addView(column("COFINS",textSize,colorLine2,foregroundColor));
        //coluna 2
        tr2.addView(column(presumido.getCofins()+"%",textSize,colorLine2,foregroundColor));
        //coluna 3
        tr2.addView(column(presumido.getTotalCofins(),textSize,colorLine2,foregroundColor));
        franquiaTable.addView(tr2);
        franquiaTable.addView(view(black));
        //linha 3
        TableRow tr3 = new TableRow(this);
        tr3.setLayoutParams(row);
        //coluna 1
        tr3.addView(column("PIS",textSize,colorLine3,foregroundColor));
        //coluna 2
        tr3.addView(column(presumido.getPis()+"%",textSize,colorLine3,foregroundColor));
        //coluna 3
        tr3.addView(column(""+presumido.getTotalPis(),textSize,colorLine3,foregroundColor));
        franquiaTable.addView(tr3);
        franquiaTable.addView(view(black));

        //linha 4
        TableRow tr4 = new TableRow(this);
        tr4.setLayoutParams(row);
        //coluna 1
        tr4.addView(column("IRPJ",textSize,colorLine4,foregroundColor));
        //coluna 2
        tr4.addView(column(presumido.getIrpj()+"%",textSize,colorLine4,foregroundColor));
        //coluna 3
        tr4.addView(column(presumido.getTotalIrpj(),textSize,colorLine4,foregroundColor));
        franquiaTable.addView(tr4);
        franquiaTable.addView(view(black));
        //linha 5

        TableRow tr5 = new TableRow(this);
        tr5.setLayoutParams(row);
        //coluna 1
        tr5.addView(column("CSLL",textSize,colorLine4,foregroundColor));
        //coluna 2
        tr5.addView(column(presumido.getCsll()+"%",textSize,colorLine4,foregroundColor));
        //coluna 3
        tr5.addView(column(presumido.getTotalCsll(),textSize,colorLine4,foregroundColor));
        franquiaTable.addView(tr5);
        franquiaTable.addView(view(black));

        //linha 4
        TableRow tr6 = new TableRow(this);
        tr6.setLayoutParams(row);
        //coluna 1
        tr6.addView(column("Total",textSize,colorLine4,foregroundColor));
        //coluna 2
        tr6.addView(column(presumido.getImposto()+"%",textSize,colorLine4,foregroundColor));
        //coluna 3
        tr6.addView(column(presumido.getTotalImposto(),textSize,colorLine4,foregroundColor));
        franquiaTable.addView(tr6);
        franquiaTable.addView(view(black));
        franquiaTable.setBottom(20);
        linearLayout.addView(franquiaTable);
    }

    private void preencherTabelaFranquia(FranquiaPacote pacote){
        TextView in= findViewById(R.id.txFranquiaInvestimento);
        TextView re= findViewById(R.id.txFranquiaRetorno);
        TextView fat = findViewById(R.id.txFranquiaFaturamento);
        TextView nome = findViewById(R.id.txFranquiaNome);
        in.setText(nf.format(pacote.getInvestimento()));
        re.setText(pacote.getPrevisao());
        fat.setText(nf.format(pacote.getFaturamento()));
        nome.setText(pacote.getNome());
    }
    private void preencherTabelaPresumido(LucroPresumido presumido){
        TextView txTxCofins = findViewById(R.id.txLPTaxaCofins);
        TextView txVlCofins = findViewById(R.id.txLPValorCofins);
        TextView txTxPis = findViewById(R.id.txLPTaxaPis);
        TextView txVlPis = findViewById(R.id.txLPValorPis);
        TextView txTxIrpj = findViewById(R.id.txLPTaxaIrpj);
        TextView txVlIrpj = findViewById(R.id.txLPValorIrpj);
        TextView txTxCsll = findViewById(R.id.txLPTaxaCsll);
        TextView txVlCsll = findViewById(R.id.txLPValorCsll);
        TextView txTxTotal = findViewById(R.id.txLPTaxaTotal);
        TextView txVlTotal = findViewById(R.id.txLPTotal);

        txTxCofins.setText(String.valueOf(presumido.getCofins()));
        txVlCofins.setText(presumido.getTotalCofins());
        txTxPis.setText(String.valueOf(presumido.getPis()));
        txVlPis.setText(presumido.getTotalPis());
        txTxIrpj.setText(String.valueOf(presumido.getIrpj()));
        txVlIrpj.setText(presumido.getTotalIrpj());
        txTxCsll.setText(String.valueOf(presumido.getCsll()));
        txVlCsll.setText(presumido.getTotalCsll());
        txTxTotal.setText(String.valueOf(presumido.getImposto()));
        txVlTotal.setText(presumido.getTotalImposto());
    }

    private void preencherTabelaSimples(Anexo anexo,Faturamento faturamento){
        TextView txPercImp = findViewById(R.id.txSNTaxaImposto);
        TextView txPercFat = findViewById(R.id.txSNTaxaValor);
        TextView txVlFat = findViewById(R.id.txSNValorFaturamento);
        TextView txVlImp = findViewById(R.id.txSNValorImposto);

        txPercImp.setText(new BigDecimal(anexo.getAliquota()).setScale(2, RoundingMode.HALF_UP).doubleValue()+"");

        txPercFat.setText("--");
        txVlFat.setText(nf.format(faturamento.getFaturamento()));
        txVlImp.setText(anexo.getValorImposto(faturamento.getFaturamento()));
    }

    public void refazer() {
        Intent intent = new Intent(this,FranquiaEscolhaActivity.class);
        startActivity(intent);
    }

}
