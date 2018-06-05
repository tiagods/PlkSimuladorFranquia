package com.prolink.tiago.plksimuladorfranquia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.prolink.tiago.plksimuladorfranquia.R;
import com.prolink.tiago.plksimuladorfranquia.dao.FranquiaDAO;
import com.prolink.tiago.plksimuladorfranquia.model.Franquia;
import com.prolink.tiago.plksimuladorfranquia.model.FranquiaPacote;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FranquiaDetalhesActivity extends AppCompatActivity implements View.OnClickListener{
    private List<FranquiaPacote> pacotes;
    private Franquia franquia;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franquiadetalhes);

        /*TableLayout tl = new TableLayout(this);
		TableLayout.LayoutParams lp = new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		tl.setLayoutParams(lp);

		TableRow tr = new TableRow(this);
		tl.addView(tr);

		TextView tv = new TextView(this);
		tv.setText("Widget 1");
		tr.addView(tv);

		setContentView(tl);

		*/

        franquia = (Franquia)getIntent().getSerializableExtra("franquia");

        FranquiaDAO franquiaDAO = new FranquiaDAO(this);
        pacotes = new ArrayList<FranquiaPacote>();
        pacotes.addAll(franquiaDAO.getPacotes(franquia));

    }
    @Override
    public void onClick(View view){
        Intent intent = new Intent(this,ResultadoActivity.class);
        FranquiaPacote pacote = pacotes.get(0);
        pacote.setNome(franquia.getNome()+" - "+pacote.getNome());
        intent.putExtra("faturamento",pacote);
        startActivity(intent);
    }
}
