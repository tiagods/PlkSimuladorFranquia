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
import com.prolink.tiago.plksimuladorfranquia.model.TipoServico;

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
    LinearLayout layoutSN;
    LinearLayout layoutLP;
    LinearLayout layoutFranquia;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        BootstrapButton concluir = findViewById(R.id.buttonConcluir);
        BootstrapButton refazer = findViewById(R.id.buttonRefazer);
        linearLayout = findViewById(R.id.pnPrincipal);
        layoutSN = findViewById(R.id.pnSimples);
        layoutLP = findViewById(R.id.pnLP);
        layoutFranquia = findViewById(R.id.pnFranquia);

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
        Anexo anexo = TributosConfig.getInstance().getAnexo(consumo);
        if (consumo instanceof FranquiaPacote) {
            preencherTabelaFranquia((FranquiaPacote)consumo);
        }
        else linearLayout.removeView(layoutFranquia);

        LucroPresumido presumido = TributosConfig.getInstance().getLucroPresumido(consumo);
        presumido.calculaImposto(consumo.getFaturamento());
        preencherTabelaPresumido(presumido);
        String tx1 = "";

        String nome="";
        String valorImposto="";
        double imposto=0;

        if(anexo==null)
            linearLayout.removeView(layoutSN);

        else if (consumo.getFaturamento()<=400000 ) {
            anexo.calcularAliquota(consumo.getFaturamento());
            preencherTabelaSimples(anexo,consumo);
            tx1+= anexo.getEnquadramento() + "->" +
                    anexo.getAliquotaFinal() + "% = " +
                    anexo.getValorImposto(consumo.getFaturamento()) +
                    "\n";
            nome = anexo.getEnquadramento().toString();
            valorImposto = anexo.getValorImposto(consumo.getFaturamento());
            imposto = anexo.getAliquotaFinal();
        }
        if(presumido!=null){
            tx1+= "Lucro Presumido ->" +
                    presumido.getImposto() + "% = " +
                    presumido.getTotalImposto() +
                    "\n";
            if (anexo==null || anexo.getAliquota() > presumido.getImposto()) {
                nome = "Lucro Presumido";
                valorImposto = presumido.getTotalImposto();
                imposto = presumido.getImposto();
            }
        }
        texto1.setText(tx1);
        texto2.setText("Considerando o "+nome+", sua empresa pagará mensalmente, o valor aproximado de "+
                valorImposto+
                " em tributos.");
        //texto2.setText("No primeiro mês sua franquia terá um custo de R$xxx, nos meses seguintes, R$xxx somando R$xxx de impostos e R$xxx de prolabore");

        String icms = consumo.getTipo().equals(TipoServico.COMERCIO)?", ICMS":"";
        texto3.setText("Para efeito de cálculo, desconsideramos os seguintes impostos (GPS, IRPF"+icms+"). \n" +
                "Em caso de dúvidas consulte um de nossos especialistas.");

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
        TextView txTxIss = findViewById(R.id.txLPTaxaIss);
        TextView txVlIss = findViewById(R.id.txLPValorIss);
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

        txTxIss.setText(String.valueOf(presumido.getIss()));
        txVlIss.setText(presumido.getTotalIss());
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

        txPercImp.setText(anexo.getAliquotaFinal()+"");

        txPercFat.setText("--");
        txVlFat.setText(nf.format(faturamento.getFaturamento()));
        txVlImp.setText(anexo.getValorImposto(faturamento.getFaturamento()));
    }

    public void refazer() {
        Intent intent = new Intent(this,FranquiaEscolhaActivity.class);
        startActivity(intent);
    }

}
