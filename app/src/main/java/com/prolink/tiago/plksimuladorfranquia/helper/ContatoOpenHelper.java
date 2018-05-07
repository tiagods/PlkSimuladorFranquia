package com.prolink.tiago.plksimuladorfranquia.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.prolink.tiago.plksimuladorfranquia.R;
import com.prolink.tiago.plksimuladorfranquia.model.Contato;

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
                "email varchar" +
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
        getWritableDatabase().insert(CONTATO_TABLE_NAME,null,values);
    }
    public List<Contato> getAll(){
        List<Contato> contatoList = new ArrayList<>();
        Cursor res = getReadableDatabase().rawQuery("SELECT * FROM "+CONTATO_TABLE_NAME,null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            Contato c = new Contato();
            c.setId(res.getInt(res.getColumnIndex("id")));
            c.setNome(res.getString(res.getColumnIndex("nome")));
            c.setTelefone(res.getString(res.getColumnIndex("telefone")));
            c.setEmail(res.getString(res.getColumnIndex("email")));
            contatoList.add(c);
            res.moveToNext();
        }
        return contatoList;
    }


}
