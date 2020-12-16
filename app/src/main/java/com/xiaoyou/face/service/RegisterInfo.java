package com.xiaoyou.face.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注册
 * @author 小游
 * @date 2020/12/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterInfo {
    private int id;
    private String stuId;
    private String name;
}
