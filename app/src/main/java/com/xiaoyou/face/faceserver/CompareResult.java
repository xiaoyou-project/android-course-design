package com.xiaoyou.face.faceserver;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 比对结果
 * @author 小游
 * @date 2020/12/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompareResult {
    /**
     * id
     */
    private String id;
    /**
     * 姓名
     */
    private String userName;
    /**
     * 学号
     */
    private String userNo;
    /**
     * 相似度
     */
    private float similar;
    /**
     * 追踪id
     */
    private int trackId;

    public CompareResult(String id,String stuId,String name, float similar) {
        this.id = id;
        this.userNo =stuId;
        this.userName =name;
        this.similar = similar;
    }
}
