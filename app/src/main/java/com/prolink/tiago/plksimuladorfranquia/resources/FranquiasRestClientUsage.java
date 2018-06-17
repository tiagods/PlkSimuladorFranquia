package com.prolink.tiago.plksimuladorfranquia.resources;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.prolink.tiago.plksimuladorfranquia.R;
import com.prolink.tiago.plksimuladorfranquia.dao.FranquiaDAO;
import com.prolink.tiago.plksimuladorfranquia.model.Franquia;
import com.prolink.tiago.plksimuladorfranquia.model.FranquiaPacote;
import com.prolink.tiago.plksimuladorfranquia.model.TipoServico;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import cz.msebera.android.httpclient.Header;

import static java.lang.Boolean.TRUE;

public class FranquiasRestClientUsage extends RestClient{
    private Context context;
    private final SimpleDateFormat dateFormat= new SimpleDateFormat("ddMMyyyyHHmmss");
    public FranquiasRestClientUsage(Context context){
        this.context=context;
    }
    public void getPublic(Calendar periodo) {
        String url="";
        if(periodo!=null) {
            Calendar calendar =Calendar.getInstance();
            calendar.setTime(periodo.getTime());
            calendar.add(Calendar.HOUR,-1);
            url="/"+dateFormat.format(calendar.getTime())+"/periodo";
        }
        Log.v("PERIODO",url);
        get(API_FRANQUIAS, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                try {
                    Set<Franquia> franquias = new HashSet<>();
                    JSONArray array = jsonArray;
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        Franquia franquia = new Franquia();
                        franquia.setId(jsonObject.getLong("id"));
                        franquia.setNome(jsonObject.getString("nome"));
                        franquia.setAtivo(jsonObject.getBoolean("ativo")==TRUE?1:0);

                        Calendar fUpdate = Calendar.getInstance();
                        fUpdate.setTimeInMillis(jsonObject.getLong("lastUpdate"));
                        Calendar fCreated = Calendar.getInstance();
                        fCreated.setTimeInMillis(jsonObject.getLong("criadoEm"));
                        franquia.setCriadoEm(fCreated);
                        franquia.setLastUpdate(fUpdate);
                        franquia.setTipo(jsonObject.getString("tipo").equals("COMERCIO")? TipoServico.COMERCIO:TipoServico.SERVICO);

                        JSONArray arrayPack = jsonObject.getJSONArray("pacotes");
                        Set<FranquiaPacote> pacotes = new HashSet<>();
                        for(int j=0; j<arrayPack.length(); j++){
                            JSONObject jsonOb2 = arrayPack.getJSONObject(j);
                            FranquiaPacote pacote = new FranquiaPacote();
                            pacote.setId(jsonOb2.getLong("id"));;
                            pacote.setNome(jsonOb2.getString("nome"));
                            pacote.setCusto(jsonOb2.getDouble("custo"));
                            pacote.setInvestimento(jsonOb2.getDouble("investimento"));
                            pacote.setFaturamento(jsonOb2.getDouble("faturamento"));
                            pacote.setProLabore(jsonOb2.getDouble("proLabore"));
                            pacote.setIcms(jsonOb2.getDouble("icms"));
                            pacote.setValorBaseICMS(jsonOb2.getDouble("baseIcms"));
                            pacote.setPrevisao(jsonOb2.getString("previsao"));
                            pacote.setFranquia_id(franquia.getId());
                            pacotes.add(pacote);
                        }
                        franquia.setPacotes(pacotes);
                        franquias.add(franquia);
                    }
                    for(Franquia franquia : franquias){
                        Log.v("FRANQUIAREST", new SimpleDateFormat("dd//MM/yyyy HH:mm:ss").format(franquia.getLastUpdate().getTime()));

                        FranquiaDAO franquiaDAO = new FranquiaDAO(context);
                        franquiaDAO.cadastrar(franquia);
                    }
                }catch (JSONException e){
                    Log.e("JSONException",e.getMessage());
                }
            }
        });
    }
}
