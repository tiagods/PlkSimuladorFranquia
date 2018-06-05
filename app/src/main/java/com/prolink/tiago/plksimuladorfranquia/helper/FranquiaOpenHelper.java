package com.prolink.tiago.plksimuladorfranquia.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.prolink.tiago.plksimuladorfranquia.R;
import com.prolink.tiago.plksimuladorfranquia.model.Franquia;
import com.prolink.tiago.plksimuladorfranquia.model.FranquiaPacote;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FranquiaOpenHelper extends SQLiteOpenHelper {
    private static final String FRANQUIA_TABLE_NAME = "franquia";
    private static final String FRANQUIA_PACOTE_TABLE_NAME ="franquia_pacote";
    public FranquiaOpenHelper(Context context) {
        super(context, String.valueOf(R.string.DATABASE_NAME),null,R.string.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create1 = "CREATE TABLE "+FRANQUIA_TABLE_NAME+"(" +
                "id INTEGER PRIMARY KEY," +
                "nome VARCHAR," +
                "ativo INTEGER" +
                ");";
        String create2 = "CREATE TABLE "+ FRANQUIA_PACOTE_TABLE_NAME +"(" +
                "id INTEGER PRIMARY KEY," +
                "nome VARCHAR," +
                "franquia_id INTEGER," +
                "custo DECIMAL(10,2)," +
                "investimento DECIMAL(10,2)," +
                "previsao VARCHAR," +
                "faturamento DECIMAL(10,2)," +
                "icms DECIMAL(10,2)," +
                "prolabore DECIMAL(10,2)," +
                "base_icms DECIMAL(10,2)," +
                "tipo VARCHAR" +
                ")";
        db.execSQL(create1);
        db.execSQL(create2);
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+FRANQUIA_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+FRANQUIA_PACOTE_TABLE_NAME);
        onCreate(db);
    }
    public void drop(){
        SQLiteDatabase db  = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+FRANQUIA_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+FRANQUIA_PACOTE_TABLE_NAME);
        onCreate(db);
    }

    public void insert(Franquia franquia){
        ContentValues values = new ContentValues();
        values.put("id", franquia.getNome());
        values.put("nome", franquia.getNome());
        values.put("ativo",franquia.getAtivo());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(FRANQUIA_TABLE_NAME,null,values);
        db.delete(FRANQUIA_PACOTE_TABLE_NAME,"franquia_id="+franquia.getId(),null);
        for(FranquiaPacote pacote : franquia.getPacotes()){
            ContentValues values2 = obterContentPacotes(pacote);
            db.insert(FRANQUIA_PACOTE_TABLE_NAME,null,values);
        }
        db.close();
    }
    public void update(Franquia franquia){
        ContentValues values = new ContentValues();
        values.put("nome", franquia.getNome());
        values.put("ativo",franquia.getAtivo());
        SQLiteDatabase db = getWritableDatabase();
        db.update(FRANQUIA_TABLE_NAME,values,"id="+franquia.getId(),null);
        db.delete(FRANQUIA_PACOTE_TABLE_NAME,"franquia_id="+franquia.getId(),null);
        for(FranquiaPacote pacote : franquia.getPacotes()){
            ContentValues values2 = obterContentPacotes(pacote);
            db.insert(FRANQUIA_PACOTE_TABLE_NAME,null,values);
        }
        db.close();
    }
    private ContentValues obterContentPacotes(FranquiaPacote pacote){
        ContentValues values = new ContentValues();
        values.put("id",pacote.getId());
        values.put("nome",pacote.getId());
        values.put("investimento",pacote.getId());
        values.put("custo",pacote.getCusto());
        values.put("previsao",pacote.getPrevisao());
        values.put("faturamento",pacote.getFaturamento());
        values.put("icms",pacote.getIcms());
        values.put("franquia_id",pacote.getFranquia_id());
        return values;
    }

    public List<Franquia> getAll(){
        List<Franquia> franquias = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+FRANQUIA_TABLE_NAME,null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            franquias.add(cursorFranquia(res));
            res.moveToNext();
        }
        db.close();
        return franquias;
    }
    public Set<FranquiaPacote> getPacotes(long franquia_id){
        Set<FranquiaPacote> pacotes = new HashSet<>();
        String sql="SELECT * FROM "+FRANQUIA_PACOTE_TABLE_NAME+" where franquia_id="+franquia_id;
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery(sql,null);
        if(res.getCount()>0) {
            res.moveToFirst();
            while (!res.isAfterLast()) {
                pacotes.add(cursorPacote(res));
                res.moveToNext();
            }
        }
        db.close();
        return pacotes;
    }

    private Franquia cursorFranquia(Cursor res){
        Franquia c = new Franquia();
        c.setId(res.getLong(res.getColumnIndex("id")));
        c.setNome(res.getString(res.getColumnIndex("nome")));
        c.setAtivo(res.getInt(res.getColumnIndex("telefone")));
        return c;
    }
    private FranquiaPacote cursorPacote(Cursor res){
        FranquiaPacote pacote = new FranquiaPacote();
        pacote.setId(res.getLong(res.getColumnIndex("id")));
        pacote.setNome(res.getString(res.getColumnIndex("nome")));
        pacote.setInvestimento(res.getDouble(res.getColumnIndex("investimento")));
        pacote.setCusto(res.getDouble(res.getColumnIndex("custo")));
        pacote.setPrevisao(res.getString(res.getColumnIndex("previsao")));
        pacote.setFaturamento(res.getDouble(res.getColumnIndex("faturamento")));
        pacote.setIcms(res.getDouble(res.getColumnIndex("icms")));
        pacote.setFranquia_id(res.getInt(res.getColumnIndex("franquia_id")));
        return pacote;
    }
    public boolean verificarSeExiste(Franquia franquia) {
        String sql="SELECT * FROM "+FRANQUIA_TABLE_NAME+" where id="+franquia.getId();
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery(sql,null);
        int count = res.getCount();
        db.close();
        return count>=1;
    }
}
