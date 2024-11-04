package com.example.assignment3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database {
    private final String DATABASE_NAME = "PRODUCTS_DATABASE";
    private final String TABLE_NAME = "PRODUCTS";
    private final String KEY_ID = "ID";
    private final String KEY_NAME = "NAME";
    private final String KEY_PRICE = "PRICE";
    private final String KEY_STATUS = "STATUS";
    private final int DB_VERSION = 1;
    private final Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabaseReader;
    private SQLiteDatabase sqLiteDatabaseWriter;

    public Database(Context context){
        this.context = context;
    }

    public void open(){
        databaseHelper = new DatabaseHelper(context, DATABASE_NAME, null, DB_VERSION);
        sqLiteDatabaseReader = databaseHelper.getReadableDatabase();
        sqLiteDatabaseWriter = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();
    }

    public long insertProduct(Product product){

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, product.getName());
        contentValues.put(KEY_PRICE, product.getPrice());
        contentValues.put(KEY_STATUS, product.getStatus());

        return sqLiteDatabaseWriter.insert(TABLE_NAME, null, contentValues);
    }

    public boolean deleteProduct(int id){

        int result = sqLiteDatabaseWriter.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{"" + id});
        return result > 0;
    }

    public boolean updateProduct(Product product){

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, product.getName());
        contentValues.put(KEY_PRICE, product.getPrice());
        contentValues.put(KEY_STATUS, product.getStatus());
        int result = sqLiteDatabaseWriter.update(TABLE_NAME, contentValues, KEY_ID + " = ?",
                new String[]{"" + product.getId()});
        return result > 0;
    }

    public ArrayList<Product> selectProducts(int status){

        ArrayList<Product> products = new ArrayList<>();
        String[] columns = {KEY_ID, KEY_NAME, KEY_PRICE, KEY_STATUS};
        Cursor cursor = sqLiteDatabaseReader.query(TABLE_NAME, columns, KEY_STATUS + " = ?",
                new String[]{"" + status}, null, null, KEY_ID);

        if (cursor != null){
            int idIndex = cursor.getColumnIndex(KEY_ID);
            int nameIndex = cursor.getColumnIndex(KEY_NAME);
            int priceIndex = cursor.getColumnIndex(KEY_PRICE);
            int statusIndex = cursor.getColumnIndex(KEY_STATUS);
            while (cursor.moveToNext()){
                Product product = new Product(cursor.getInt(idIndex), cursor.getString(nameIndex),
                        cursor.getInt(priceIndex), cursor.getInt(statusIndex));
                products.add(product);
            }
            cursor.close();
        }

        return products;
    }

    public void customDelete(){
        int result = sqLiteDatabaseWriter.delete(TABLE_NAME, null, null);
    }

    private class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "\n"
                    + "(\n"
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, \n"
                    + KEY_NAME + " TEXT NOT NULL, \n"
                    + KEY_PRICE + " INTEGER NOT NULL, \n"
                    + KEY_STATUS + " INTEGER NOT NULL \n"
                    + ")";

            sqLiteDatabase.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
            sqLiteDatabase.execSQL(query);
            onCreate(sqLiteDatabase);
        }
    }
}
