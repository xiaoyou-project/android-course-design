package com.xiaoyou.face;

import com.xiaoyou.face.service.StudentInfo;
import com.xiaoyou.face.utils.FaceRead;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void faceTest() throws Exception {
        //FaceRead.checkFace("D:\\tmp\\2.jpg");
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setDateTime(LocalDate.now());
        System.out.println(studentInfo.getDateTime().getMonthValue());
    }
}