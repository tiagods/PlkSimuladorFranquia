package com.prolink.tiago.prolinkfranquias2018.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.prolink.tiago.prolinkfranquias2018.R;
import com.prolink.tiago.prolinkfranquias2018.config.DBConfig;
import com.prolink.tiago.prolinkfranquias2018.model.Contato;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ContatoOpenHelper extends SQLiteOpenHelper {
    private static final String CONTATO_TABLE_NAME = "contato";
    private static final String FRANQUIA_TABLE_NAME = "franquia";
    private static final String FRANQUIA_PACOTE_TABLE_NAME ="franquia_pacote";

    public ContatoOpenHelper(Context context){
//        super(context, DBConfig.DATABASE,null, DBConfig.DATABASE_VERSION);
        super(context, DBConfig.DATABASE,null,DBConfig.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE "+CONTATO_TABLE_NAME+"(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR," +
                "telefone VARCHAR," +
                "email VARCHAR," +
                "sincronizado INTEGER," +
                "contato_id INTEGER"+
                ");";
        String create1 = "CREATE TABLE "+FRANQUIA_TABLE_NAME+"(" +
                "id INTEGER PRIMARY KEY," +
                "nome VARCHAR," +
                "ativo INTEGER," +
                "tipo VARCHAR," +
                "lastUpdate DATETIME" +
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
                ");";

        db.execSQL(create);
        db.execSQL(create1);
        db.execSQL(create2);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+CONTATO_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+FRANQUIA_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+FRANQUIA_PACOTE_TABLE_NAME);
        onCreate(db);
    }

    public void drop(){
        SQLiteDatabase db  = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+CONTATO_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+FRANQUIA_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+FRANQUIA_PACOTE_TABLE_NAME);
        onCreate(db);
    }
    public void insert(Contato contato){
        ContentValues values = new ContentValues();
        values.put("nome", contato.getNome());
        values.put("telefone",contato.getTelefone());
        values.put("email",contato.getEmail());
        values.put("sincronizado",contato.getSincronizado());
        values.put("contato_id",contato.getContato_id());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(CONTATO_TABLE_NAME,null,values);
    }
    public void update(Contato contato){
        ContentValues values = new ContentValues();
        values.put("nome", contato.getNome());
        values.put("telefone",contato.getTelefone());
        values.put("email",contato.getEmail());
        values.put("sincronizado",contato.getSincronizado());
        values.put("contato_id",contato.getContato_id());
        SQLiteDatabase db = getWritableDatabase();
        db.update(CONTATO_TABLE_NAME,values,"id="+contato.getId(),null);
    }
    public List<Contato> getAll(){
        List<Contato> contatoList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+CONTATO_TABLE_NAME,null);
        if(res.getCount()>0) {
            res.moveToFirst();
            while (!res.isAfterLast()) {
                contatoList.add(cursor(res));
                res.moveToNext();
            }
        }
        return contatoList;
    }

    public Contato getLast(){
        String sql="SELECT * FROM "+CONTATO_TABLE_NAME+" ORDER BY id DESC LIMIT 1";
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery(sql,null);
        Contato contato = null;
        if(res.moveToFirst()){
            contato = cursor(res);
        }
        return contato;
    }

    private Contato cursor(Cursor res){
        Contato c = new Contato();
        c.setId(res.getInt(res.getColumnIndex("id")));
        c.setNome(res.getString(res.getColumnIndex("nome")));
        c.setTelefone(res.getString(res.getColumnIndex("telefone")));
        c.setEmail(res.getString(res.getColumnIndex("email")));
        c.setSincronizado(res.getInt(res.getColumnIndex("sincronizado")));
        c.setContato_id(res.getInt(res.getColumnIndex("contato_id")));
        return c;
    }

    public List<Contato> filtrarNaoSincronizados() {
        List<Contato> contatoList = new ArrayList<>();
        String sql="SELECT * FROM "+CONTATO_TABLE_NAME+" where sincronizado=0";
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery(sql,null);
        if(res.getCount()>0) {
            res.moveToFirst();
            while (!res.isAfterLast()) {
                contatoList.add(cursor(res));
                res.moveToNext();
            }
        }
        return contatoList;
    }
}
