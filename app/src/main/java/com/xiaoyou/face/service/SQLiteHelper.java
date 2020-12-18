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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lenyuqin
 * @data 2020/12/16
 */
public class SQLiteHelper extends SQLiteOpenHelper implements Service {
    private final static String DATABASE_NAME = "FaceCheck";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "StudentInfo";
    private final static String TABLE_REGISTER = "Student";

    //创建数据库，里面添加了3个参数，分别是：Msgone VARCHAR类型，30长度当然这了可以自定义
    //Msgtwo VARCHAR(20)   Msgthree VARCHAR(30))  NOT NULL不能为空
    String sql = "CREATE TABLE StudentInfo (stu_id int(11) NOT NULL ," +
            "  name varchar(20) DEFAULT NULL ," +
            "  is_Sign bit(1) DEFAULT NULL ," +
            "  day int(5) DEFAULT NULL ," +
            "  date date  DEFAULT NULL ," +
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


    /**
     * 学生注册
     *
     * @param registerInfo 学生注册信息
     * @return 插入条数
     */
    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public long studentRegister(RegisterInfo registerInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", registerInfo.getId());
        cv.put("stu_id", registerInfo.getStuId());
        cv.put("name", registerInfo.getName());
        return db.insert(TABLE_REGISTER, null, cv);
    }

    /**
     * 查询学生信息
     *
     * @param id 注册id
     * @return 学生id
     */
    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public RegisterInfo getStudentInfo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_REGISTER, null, "id=?", new String[]{"" + id}, null, null, null);
        RegisterInfo registerInfo = new RegisterInfo();
        if (cursor.getCount() != 0) {
            if (cursor.moveToNext()) {
                registerInfo.setId(cursor.getInt(0));
                registerInfo.setStuId(cursor.getString(1));
                registerInfo.setName(cursor.getString(2));
            }
        }
        return registerInfo;
    }


    /**
     * 首页签到部分,返回具体时间
     *
     * @return 返回已签到的日子
     * @deprecated
     */
    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<LocalDate> getCalendar() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<LocalDate> localDates = new ArrayList<>();
        localDates.add(LocalDate.now());
        return localDates;
    }

    /**
     * 签到统计部分
     *
     * @return 学号，姓名，签到时间
     */
    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<StudentInfoTO> getCountToday() throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] selectionArgs = new String[3];
        selectionArgs[0] = String.valueOf(LocalDateTime.now().getDayOfMonth());
        selectionArgs[1] = String.valueOf(LocalDate.now().getMonthValue());
        selectionArgs[2] = String.valueOf(LocalDate.now().getYear());
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_NAME + " WHERE  day= ? and month = ? and year = ?", selectionArgs);
        ArrayList<StudentInfoTO> studentInfoTO = new ArrayList<>();
        if (cursor.isLast()) {
            StudentInfoTO studentInfoTO1 = new StudentInfoTO();
            studentInfoTO1.setDateTime(sdf.parse(cursor.getString(4)));
            studentInfoTO1.setStuId(cursor.getString(1));
            studentInfoTO1.setName(cursor.getString(2));
            studentInfoTO.add(studentInfoTO1);
            cursor.moveToNext();
        }
        return studentInfoTO;
    }


    /**
     * 考勤情况统计
     *
     * @return 当天的签到信息
     */
    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public History getTodayHistory() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = new String[3];
        selectionArgs[0] = String.valueOf(LocalDateTime.now().getDayOfMonth());
        selectionArgs[1] = String.valueOf(LocalDate.now().getMonthValue());
        selectionArgs[2] = String.valueOf(LocalDate.now().getYear());
        @SuppressLint("Recycle") Cursor cursor1 = db.rawQuery("SELECT  * FROM " + TABLE_NAME + " WHERE  day= ? and month = ? and year = ? ", selectionArgs);
        @SuppressLint("Recycle") Cursor curso2r = db.rawQuery("SELECT  * FROM " + TABLE_REGISTER, null);
        int count = curso2r.getCount();
        History history = new History();
        history.setDate(LocalDate.now());
        history.setIsSignUp(cursor1.getCount());
        history.setNotSigUp(count - cursor1.getCount());
        return history;
    }

    /**
     * 考勤历史(默认返回今年的)
     *
     * @return 返回一个历史数据list
     */
    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<History> getHistory() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(LocalDate.now().getYear());
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT distinct day,month FROM " + TABLE_NAME + " WHERE year=?", selectionArgs);
        ArrayList<History> historyArrayList = new ArrayList<>();
        while (cursor.isLast()) {
            History history = new History();
            historyArrayList.add(history);
            cursor.moveToNext();
        }
        return historyArrayList;
    }


    /**
     * 查询
     *
     * @param stuId 学号
     * @param name  用户名
     * @return 学生信息的list
     */
    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<StudentInfoTO> queryStudentInfo(String stuId, String name) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = new String[4];
        selectionArgs[0] = String.valueOf(stuId);
        selectionArgs[1] = String.valueOf(name);
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE stu_id = ? and name= ?  ", selectionArgs);
        ArrayList<StudentInfoTO> studentInfoTOS = new ArrayList<>();
        while (cursor.isLast()) {
            StudentInfoTO studentInfo = new StudentInfoTO();
            String datetime = cursor.getString(4);
            studentInfo.setName(name);
            studentInfo.setStuId(stuId);
            studentInfo.setDateTime(sdf.parse(datetime));
            cursor.moveToNext();
        }
        return studentInfoTOS;
    }

    /**
     * 签到
     *
     * @param stuId 学号
     * @param name  用户名
     * @param data  当前时间
     * @return 是否插入成功
     */
    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean signUp(String stuId, String name, LocalDateTime data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", stuId);
        cv.put("name", name);
        cv.put("is_Sign", Is_Sign.TURE.getCode());
        cv.put("day", data.getDayOfMonth());
        cv.put("month", data.getMonthValue());
        cv.put("year", data.getYear());
        cv.put("date", DateFormatUtils.getTodayDate());
        return db.insert(TABLE_NAME, null, cv) == 1;
    }

    /**
     * 查询是否签到
     *
     * @param stuId 学生学号
     * @param data  当前时间
     * @return 是否签到
     */
    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean isSignUp(int stuId, LocalDateTime data) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = new String[4];
        selectionArgs[0] = String.valueOf(stuId);
        selectionArgs[1] = String.valueOf(LocalDateTime.now().getDayOfMonth());
        selectionArgs[2] = String.valueOf(LocalDate.now().getMonthValue());
        selectionArgs[3] = String.valueOf(LocalDate.now().getYear());
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE stu_id = ? and day= ? and month = ? and year = ? ", selectionArgs);
        return cursor.getCount() == 1;
    }


}
