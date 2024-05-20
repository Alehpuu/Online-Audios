package ifpes.gestionaudios;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class BaseDeDatos extends SQLiteOpenHelper {
    public BaseDeDatos(Context context) {     // constructor
        super(context, "audio", null, 1);
    }
    // metodo para crear la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE media (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, url TEXT)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //actualiza base de datos
        db.execSQL("DROP TABLE IF EXISTS media");
        onCreate(db);
    }
    public ArrayList<String> getAudios() {  //obtiene lista de audios
        ArrayList<String> info = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM media", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                    @SuppressLint("Range") String titulo = cursor.getString(cursor.getColumnIndex("titulo"));
                    @SuppressLint("Range") String url = cursor.getString(cursor.getColumnIndex("url"));
                    info.add(id + ".- " + titulo + " - " + url);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close(); // cerrar el cursor
            }
            db.close();
        }
        return info;
    }
    public void insertarAudio( String titulo, String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", titulo);
        values.put("url", url);
        long result = db.insert("media", null, values);
        Log.d("BaseDeDatos", "Resultado de la inserción: " + result); // agregar log
        db.close();
    }

}
