package com.xiaoyou.face.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lenyuqin
 * @data 2020/12/16
 */
@Data
@AllArgsConstructor
public class StudentInfo {
    //学生学号
    private String id;
    //学生名字
    private String name;
    //是否打卡 1打卡成功，0打卡失败
    private int isSign;
    //打卡时间
    private LocalDate dateTime;
}
