package com.xiaoyou.face.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lenyuqin
 * @data 2020/12/18
 */
public interface Service {
    /**
     * 学生注册
     *
     * @param registerInfo 学生注册信息
     * @return 插入条数
     */
    long studentRegister(RegisterInfo registerInfo);


    /**
     * 查询学生信息
     *
     * @param id 注册id
     * @return 学生id
     */
    RegisterInfo getStudentInfo(int id);


    /**
     * 首页签到部分,返回具体时间
     *
     * @return 返回已签到的日子
     */
    List<LocalDate> getCalendar();


    /**
     * 签到统计部分
     *
     * @return 学号，姓名，签到时间
     */
    List<StudentInfoTO> getCountToday() throws ParseException;


    /**
     * 考勤情况统计
     *
     * @return 当天的签到信息
     */
    History getTodayHistory();


    /**
     * 考勤历史(默认返回今年的)
     *
     * @return 返回一个历史数据list
     */
    List<History> getHistory();


    /**
     * 查询
     *
     * @param stuId 学号
     * @param name  用户名
     * @return 学生信息的list
     */
    List<StudentInfoTO> queryStudentInfo(String stuId, String name) throws ParseException;


    /**
     * 签到
     *
     * @param stuId 学号
     * @param name  用户名
     * @param data  当前时间
     * @return 是否插入成功
     */
    Boolean signUp(String stuId, String name, LocalDateTime data);


    /**
     * 查询是否签到
     *
     * @param stuId 学生学号
     * @param data  当前时间
     * @return 是否签到
     */
    Boolean isSignUp(int stuId, LocalDateTime data);


}
