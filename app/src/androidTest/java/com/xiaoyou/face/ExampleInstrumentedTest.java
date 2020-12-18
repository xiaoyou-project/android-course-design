package com.xiaoyou.face;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.xiaoyou.face.service.Is_Sign;
import com.xiaoyou.face.service.RegisterInfo;
import com.xiaoyou.face.service.SQLiteHelper;
import com.xiaoyou.face.service.Service;
import com.xiaoyou.face.service.StudentInfo;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.xiaoyou.face", appContext.getPackageName());
    }

    @Test
    public void addData(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Service service;
        service = new SQLiteHelper(appContext);
       service.signUp("1806040103","小游", LocalDateTime.now());
       service.signUp("1806040104","小游1", LocalDateTime.now());
       service.signUp("1806040105","小游2", LocalDateTime.now());
       service.signUp("1806040106","小游3", LocalDateTime.now());
       service.signUp("1806040107","小游4", LocalDateTime.now());
       service.signUp("1806040108","小游5", LocalDateTime.now());


    }

    @Test
    public void userLogin(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SQLiteHelper sqLiteHelper = new SQLiteHelper(appContext);
        System.out.println();
    }
}