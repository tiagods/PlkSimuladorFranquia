package com.prolink.tiago.plksimuladorfranquia.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.prolink.tiago.plksimuladorfranquia.R;

public class FranquiaOpenHelper extends SQLiteOpenHelper {
    private static final String FRANQUIA_TABLE_NAME = "franquia";
    private static final String FRANQUIA_DETALHES_TABLE_NAME="franquia_detalhes";

    public FranquiaOpenHelper(Context context) {
        super(context, String.valueOf(R.string.DATABASE_NAME),null,R.string.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create1 = "CREATE TABLE "+FRANQUIA_TABLE_NAME+"(" +
                "id INTEGER PRIMARY KEY," +
                "nome VARCHAR," +
                "ativo INTEGER," +
                "lastUpdate TIMESTAMP," +
                "criadoEm TIMESTAMP" +
                ");";
        String create2 = "CREATE TABLE "+FRANQUIA_DETALHES_TABLE_NAME+"(" +
                "id INTEGER PRIMARY KEY," +
                "nome VARCHAR," +
                "investimento DECIMAL(10,2)," +
                "custo DECIMAL(10,2)," +
                "previsao VARCHAR," +
                "faturamento DECIMAL(10,2)," +
                "icms DECIMAL(10,2)," +
                "lastUpdate TIMESTAMP," +
                "franquia_id INTEGER"+
                ")";
        db.execSQL(create1);
        db.execSQL(create2);
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
