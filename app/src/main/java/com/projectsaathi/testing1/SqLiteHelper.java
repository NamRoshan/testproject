package com.projectsaathi.testing1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class SqLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "RECORDDB.sqlite";
    public static final String TABLE_NAME = "RECORD";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "name";
    public static final String COL_3 = "age";
    public static final  String COL_4="contact";


  SqLiteHelper(Context context,
               String name,
               SQLiteDatabase.CursorFactory factory,
               int version){
      super(context,name,factory,version);
  }
public  void queryData(String sql){
      SQLiteDatabase database=getWritableDatabase();
      database.execSQL(sql);
}
public  void insertData(String name,String age,String phone,byte[] image){
      SQLiteDatabase database=getWritableDatabase();
      String sql="INSERT INTO RECORD VALUES (NULL,?,?,?,?)";

    SQLiteStatement statement =database.compileStatement(sql);
    statement.clearBindings();
    statement.bindString(1,name);
    statement.bindString(2,age);
    statement.bindString(3,phone);
    statement.bindBlob(4,image);

    statement.executeInsert();


}
public  void updateData(String name,String age,String phone,byte[] image,int id){
      SQLiteDatabase database=getWritableDatabase();
      String sql="UPDATE RECORD SET name=?,age=?,phone=?,image=? WHERE id=?";
      SQLiteStatement statement=database.compileStatement(sql);

    statement.bindString(1,name);
    statement.bindString(2,age);
    statement.bindString(3,phone);
    statement.bindBlob(4,image);
    statement.bindDouble(5,(double)id);
    statement.execute();
    database.close();
}
public  void deleteData(int id){
      SQLiteDatabase database =getWritableDatabase();
      String sql="DELETE FROM RECORD WHERE id=?";
      SQLiteStatement statement=database.compileStatement(sql);
      statement.clearBindings();
      statement.bindDouble(1,(double)id);
      statement.execute();
      database.close();
}
public Cursor getData(String sql){
      SQLiteDatabase database =getReadableDatabase();
      return  database.rawQuery(sql,null);
}
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public Cursor getTotalValues() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" +SqLiteHelper.COL_3 + ") as Total FROM " + SqLiteHelper.TABLE_NAME, null);
        if (cursor.moveToFirst()) {

            int total = cursor.getInt(cursor.getColumnIndex("Total"));// get final total
            System.out.print("Roshan is testing..." + total);
        }
        return cursor;
    }
}
