package com.xiaoyou.face.service;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.xiaoyou.face.R;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lenyuqin
 * @data 2020/12/16
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "FaceCheck";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "StudentInfo";
    private final static String TABLE_REGISTER = "Student";

    //创建数据库，里面添加了3个参数，分别是：Msgone VARCHAR类型，30长度当然这了可以自定义
    //Msgtwo VARCHAR(20)   Msgthree VARCHAR(30))  NOT NULL不能为空
    String sql = "CREATE TABLE StudentInfo (id int(11) NOT NULL ," +
            "  name varchar(20) DEFAULT NULL ," +
            "  is_Sign bit(1) DEFAULT NULL ," +
            "  day int(5) DEFAULT NULL ," +
            "  month int(5) DEFAULT NULL ," +
            "  year int(5) DEFAULT NULL)";

    String createStudent = "CREATE TABLE Student (" +
            "  id int(11) NOT NULL," +
            "  stu_id varchar(20) NOT NULL," +
            "  name varchar(20) DEFAULT NULL," +
            "  PRIMARY KEY (id))";

    //构造函数，创建数据库
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
        db.execSQL(createStudent);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        String sql2 = "DROP TABLE IF EXISTS " + TABLE_REGISTER;
        db.execSQL(sql);
        db.execSQL(sql2);
        onCreate(db);
    }


    //查询本月的数据，查询一个字段，返回日子的int集合 根据月去查询去除相同日子，返回日子
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Integer> queryByMouth() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = new String[1];
        selectionArgs[1] = String.valueOf(LocalDate.now().getMonthValue());
        Cursor cursor = db.rawQuery("SELECT DISTINCT day FROM " + TABLE_NAME + " WHERE month = ?", selectionArgs);
        ArrayList<Integer> result = new ArrayList<>();
        while (cursor.isLast()) {
            String columnName = cursor.getColumnName(1);
            result.add(Integer.valueOf(columnName));
            cursor.moveToNext();
        }
        return result;
    }


    //查询学生总数 查询学生表
    public int queryCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = new String[1];
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_REGISTER, selectionArgs);
        return cursor.getCount();
    }


    //查询当天打卡学生人数 根据时间查询
    @RequiresApi(api = Build.VERSION_CODES.O)
    public int queryByDate() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = new String[3];
        selectionArgs[0] = String.valueOf(LocalDate.now().getDayOfMonth());
        selectionArgs[1] = String.valueOf(LocalDate.now().getMonthValue());
        selectionArgs[2] = String.valueOf(LocalDate.now().getYear());
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE day = ? and month = ? and year = ?", selectionArgs);
        return cursor.getCount();
    }


    //查询是否已打卡 根据日期和学生id查询
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean queryIsSign(StudentInfo studentInfo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = new String[2];
        selectionArgs[0] = String.valueOf(studentInfo.getId());
        selectionArgs[1] = String.valueOf(studentInfo.getDateTime().getDayOfMonth());
        Cursor cursor = db.rawQuery("SELECT id FROM " + TABLE_NAME + " WHERE id = ? and day = ?", selectionArgs);
        return cursor.getCount() != 0;
    }


    //学生打卡 插入数据
    @RequiresApi(api = Build.VERSION_CODES.O)
    public long checkIn(StudentInfo studentInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", studentInfo.getId());
        cv.put("name", studentInfo.getName());
        cv.put("is_Sign", studentInfo.getIsSign());
        cv.put("day", studentInfo.getDateTime().getDayOfMonth());
        cv.put("month", studentInfo.getDateTime().getMonthValue());
        cv.put("year", studentInfo.getDateTime().getYear());
        return db.insert(TABLE_NAME, null, cv);
    }

    /**
     *  学生注册
     * @param studentInfo
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public long insert(RegisterInfo studentInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", studentInfo.getId());
        cv.put("stu_id", studentInfo.getStuId());
        cv.put("name", studentInfo.getName());
        return db.insert(TABLE_REGISTER, null, cv);
    }

    /**
     *  查询学生信息
     * @param id 注册id
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public RegisterInfo getInfo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_REGISTER,null,"id=?",new String[]{""+id},null,null,null);
        RegisterInfo registerInfo = new RegisterInfo();
        if (cursor.getCount()!=0){
            if (cursor.moveToNext()){
                registerInfo.setId(cursor.getInt(0));
                registerInfo.setStuId(cursor.getString(1));
                registerInfo.setName(cursor.getString(2));
            }
        }
        return registerInfo;
    }



}
