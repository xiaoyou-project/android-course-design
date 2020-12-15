package com.xiaoyou.face.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 类型转换函数
 *
 * @author 小游
 * @date 2020/12/15
 */
public class TypeChange {
    /**
     *  bitmap对象转string
     * @param bm bitmap对象
     * @return 返回InputStream 对象
     */
    public static InputStream bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        return new ByteArrayInputStream(bao.toByteArray());
    }
}
