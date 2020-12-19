package com.xiaoyou.face.model;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *  用户签到信息
 * @author 小游
 * @date 2020/12/16
 */
@Data
@AllArgsConstructor
@SmartTable()
public class Login {
    @SmartColumn(id =1,name = "学号")
    String no;
    @SmartColumn(id =2,name = "姓名")
    String name;
    @SmartColumn(id =3,name = "签到时间")
    String time;
}
