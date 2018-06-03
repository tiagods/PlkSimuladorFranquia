package com.prolink.tiago.plksimuladorfranquia.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.prolink.tiago.plksimuladorfranquia.R;
import com.prolink.tiago.plksimuladorfranquia.model.Contato;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ContatoOpenHelper extends SQLiteOpenHelper {
    //private static final String DATABASE_NAME = "plkabf";
    private static final String CONTATO_TABLE_NAME = "contato";

    public ContatoOpenHelper(Context context){
        super(context, String.valueOf(R.string.DATABASE_NAME),null,R.string.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE "+CONTATO_TABLE_NAME+"(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR," +
                "telefone VARCHAR," +
                "email VARCHAR," +
                "sincronizado INTEGER," +
                "contato_id INTEGER,"+
                "aliquota INTEGER,"+
                "tipo INTEGER,"+
                "faturamento DECIMAL(10,2)," +
                "prolabore DECIMAL(10,2)" +
                ");";
        db.execSQL(create);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+CONTATO_TABLE_NAME);
        onCreate(db);
    }
    public void drop(){
        SQLiteDatabase db  = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+CONTATO_TABLE_NAME);
        onCreate(db);
    }

    public void insert(Contato contato){
        ContentValues values = new ContentValues();
        values.put("nome", contato.getNome());
        values.put("telefone",contato.getTelefone());
        values.put("email",contato.getEmail());
        values.put("sincronizado",contato.getSincronizado());
        values.put("tipo",contato.getTipo().toString());
        values.put("aliquota",contato.getAliquotaICMS());
        values.put("faturamento",contato.getFaturamento().doubleValue());
        values.put("prolabore",contato.getProlabore().doubleValue());
        getWritableDatabase().insert(CONTATO_TABLE_NAME,null,values);
    }
    public void update(Contato contato){
        ContentValues values = new ContentValues();
        values.put("nome", contato.getNome());
        values.put("telefone",contato.getTelefone());
        values.put("email",contato.getEmail());
        values.put("faturamento", contato.getFaturamento().doubleValue());
        values.put("prolabore",contato.getProlabore().doubleValue());
        values.put("aliquota",contato.getAliquotaICMS());
        values.put("tipo",contato.getTipo().toString());
        values.put("sincronizado",contato.getSincronizado());
        values.put("contato_id",contato.getContato_id());
        getWritableDatabase().update(CONTATO_TABLE_NAME,values,"id="+contato.getId(),null);
    }
    public List<Contato> getAll(){
        List<Contato> contatoList = new ArrayList<>();
        Cursor res = getReadableDatabase().rawQuery("SELECT * FROM "+CONTATO_TABLE_NAME,null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            contatoList.add(cursor(res));
            res.moveToNext();
        }
        return contatoList;
    }
    public Contato getLast(){
        String sql="SELECT * FROM "+CONTATO_TABLE_NAME+" ORDER BY id DESC LIMIT 1";
        Cursor res = getReadableDatabase().rawQuery(sql,null);
        if(res.moveToFirst()){
            return cursor(res);
        }
        return null;
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
        String sql="SELECT * FROM "+CONTATO_TABLE_NAME+" where sincronizado";
        Cursor res = getReadableDatabase().rawQuery(sql,null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            contatoList.add(cursor(res));
            res.moveToNext();
        }
        return contatoList;
    }
}
