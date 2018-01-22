package com.cyw.a2018012201;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //在res中新開一個raw資料夾, 然後把sqlite的檔案copy進去, (Students.db 剛剛在sqlitebrowser中建立的)
    //然後按click1要把她讀到手機裡
    public void click1(View v)
    {
        //把要存入的檔案名稱開好
        File dbFile = new File(getFilesDir(), "student.db");
        //將外部的student.db 以 imputstream形式讀入
        InputStream is = getResources().openRawResource(R.raw.student);
        try {
            //用outputstream輸出到手機裡面的data/data/檔案名稱/file/student.db裡
            OutputStream os = new FileOutputStream(dbFile);
            int r;
            while ((r = is.read()) != -1)
            {
                os.write(r);
            }
            is.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //把手機內db抓出來,用log顯示
    public void click2(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        //cursor 游標指在資料的第幾筆
        Cursor c = db.rawQuery("Select * from students", null);
        //cursor 游標指在資料的第一筆
        c.moveToFirst();
        Log.d("DB", c.getInt(0)+","+c.getString(1) + "," + c.getInt(2));
        //cursor 游標指到下一筆
        while (c.moveToNext())
        {
            Log.d("DB", c.getInt(0)+","+c.getString(1) + "," + c.getInt(2));
        }
   }
    //Sql 語法 select 1 (rawQuery)
    public void click3(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        String strSql = "Select * from students where _id=?";
        //因為有一個問號,所以要宣告一個陣列,放一個字進去, 如果有兩個問號就放兩個
        Cursor c = db.rawQuery(strSql, new String[] {"2"});
        c.moveToFirst();
        Log.d("DB", c.getString(1) + "," + c.getInt(2));
    }
    //Sql select 2 (query)
    public void click4(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        //陣列中每個欄位都要寫,不然會當掉
        Cursor c = db.query("students", new String[] {"_id", "name", "score"}, null, null, null, null, null);
        c.moveToFirst();
        Log.d("DB", c.getString(1) + "," + c.getInt(2));
    }
    //Sql select 3 (query)
    public void click5(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        Cursor c = db.query("students", new String[] {"_id", "name", "score"}, "_id=?", new String[] {"2"}, null, null, null);
        c.moveToFirst();
        Log.d("DB", c.getString(1) + "," + c.getInt(2));
    }

    //insert 1
    public void click6(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        db.execSQL("Insert into students (_id,name,score) values (5,'Jenny',100)");
        db.close();
    }

    //insert 2
    public void click7(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues cv = new ContentValues();
        cv.put("_id", 4);
        cv.put("name", "Mary");
        cv.put("score", 92);
        db.insert("students", null, cv);
        db.close();
    }

    //Update
    public void click8(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues cv = new ContentValues();
        cv.put("score", 85);
        db.update("students", cv, "_id=?", new String[] {"3"});
        db.close();
    }
    //Delete
    public void click9(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        db.delete("students", "_id=?", new String[] {"2"});
        db.close();
    }

}
