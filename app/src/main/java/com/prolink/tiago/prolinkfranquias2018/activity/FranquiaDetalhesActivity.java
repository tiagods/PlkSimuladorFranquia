package com.prolink.tiago.prolinkfranquias2018.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.prolink.tiago.prolinkfranquias2018.R;
import com.prolink.tiago.prolinkfranquias2018.dao.FranquiaDAO;
import com.prolink.tiago.prolinkfranquias2018.model.Franquia;
import com.prolink.tiago.prolinkfranquias2018.model.FranquiaPacote;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FranquiaDetalhesActivity extends AppCompatActivity{
    NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
    private List<FranquiaPacote> pacotes;
    private Franquia franquia;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franquiadetalhes);
        linearLayout = findViewById(R.id.layoutTable);

        TableLayout tb1 = findViewById(R.id.tbSimulacao1);
        TableLayout tb2 = findViewById(R.id.tbSimulacao2);
        TableLayout tb3 = findViewById(R.id.tbSimulacao3);

        Button button1 = findViewById(R.id.btSimular1);
        Button button2 = findViewById(R.id.btSimular2);
        Button button3 = findViewById(R.id.btSimular3);

        TextView txIn1 = findViewById(R.id.txInvestimento1);
        TextView txIn2 = findViewById(R.id.txInvestimento2);
        TextView txIn3 = findViewById(R.id.txInvestimento3);

        TextView txFa1 = findViewById(R.id.txFaturamento1);
        TextView txFa2 = findViewById(R.id.txFaturamento2);
        TextView txFa3 = findViewById(R.id.txFaturamento3);

        TextView txRe1 = findViewById(R.id.txRetorno1);
        TextView txRe2 = findViewById(R.id.txRetorno2);
        TextView txRe3 = findViewById(R.id.txRetorno3);

        franquia = (Franquia)getIntent().getSerializableExtra("franquia");

        FranquiaDAO franquiaDAO = new FranquiaDAO(this);
        pacotes = new ArrayList<>();
        pacotes.addAll(franquiaDAO.getPacotes(franquia));

        if(pacotes.size()==0){
            tb1.setVisibility(View.INVISIBLE);
            tb2.setVisibility(View.INVISIBLE);
            tb3.setVisibility(View.INVISIBLE);
        }
        else{
            FranquiaPacote p = pacotes.get(0);
            txIn1.setText(nf.format(p.getInvestimento()));
            txFa1.setText(nf.format(p.getFaturamento()));
            txRe1.setText(p.getPrevisao());
            if(pacotes.size()==1){
                tb2.setVisibility(View.INVISIBLE);
                tb3.setVisibility(View.INVISIBLE);
            }
            else {
                FranquiaPacote p2 = pacotes.get(1);
                txIn2.setText(nf.format(p2.getInvestimento()));
                txFa2.setText(nf.format(p2.getFaturamento()));
                txRe2.setText(p2.getPrevisao());

                if(pacotes.size()==2)
                    tb3.setVisibility(View.INVISIBLE);
                else{
                    FranquiaPacote p3 = pacotes.get(2);
                    txIn3.setText(nf.format(p3.getInvestimento()));
                    txFa3.setText(nf.format(p3.getFaturamento()));
                    txRe3.setText(p3.getPrevisao());
                }
            }
        }
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avancar(pacotes.get(0));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avancar(pacotes.get(1));
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avancar(pacotes.get(2));
            }
        });
    }

    public void avancar(FranquiaPacote pacote){
        Intent intent = new Intent(this,ResultadoActivity.class);
        pacote.setNome(franquia.getNome()+" - "+pacote.getNome());
        pacote.setTipo(franquia.getTipo());
        intent.putExtra("faturamento",pacote);
        startActivity(intent);
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
    private void contruirPacotes(List<FranquiaPacote> pacotes){

        int[] background = new int[4];
        background[0] = R.color.colorTable399c91;
        background[1] = R.color.colorTableGreen;
        background[2] = R.color.colorTableRed;
        background[3] = R.color.colorTableRed;
        int textSize = 24;
        int linha1 = R.color.colorTableBlue;

        int white = R.color.bootstrap_brand_info;
        int black = R.color.colorPrimaryDark;

        TableLayout franquiaTable = new TableLayout(this);
        TableLayout.LayoutParams lp = new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        franquiaTable.setLayoutParams(lp);
        TableLayout.LayoutParams row = new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TableRow tr1 = new TableRow(this);
        tr1.setLayoutParams(row);
        //primeira linha
        tr1.addView(column("", textSize, white, black));
        for(int i =0;i<pacotes.size();i++){
            tr1.addView(column(pacotes.get(i).getNome(),textSize, linha1, black));
        }
        franquiaTable.addView(tr1);
        franquiaTable.addView(view(white));




        for(int i=0;i<pacotes.size();i++) {


            TableRow tr2 = new TableRow(this);
            tr2.setLayoutParams(row);
            tr2.addView(column("Investimento", textSize, white, black));
            franquiaTable.addView(tr2);
            franquiaTable.addView(view(white));

            TableRow tr3 = new TableRow(this);
            tr3.setLayoutParams(row);
            tr3.addView(column("Retorno", textSize, white, black));
            franquiaTable.addView(tr3);
            franquiaTable.addView(view(white));

            TableRow tr4 = new TableRow(this);
            tr4.setLayoutParams(row);
            tr4.addView(column("", textSize, white, black));
            franquiaTable.addView(tr4);
            franquiaTable.addView(view(white));

            linearLayout.addView(franquiaTable);
        }
    }
}
