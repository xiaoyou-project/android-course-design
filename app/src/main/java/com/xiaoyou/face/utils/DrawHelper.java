package com.xiaoyou.face.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.hardware.Camera;

import com.arcsoft.face.AgeInfo;
import com.arcsoft.face.GenderInfo;
import com.arcsoft.face.LivenessInfo;
import com.xiaoyou.face.model.DrawInfo;
import com.xiaoyou.face.widget.FaceRectView;

import java.util.List;

/**
 * 绘制人脸框帮助类，用于在{@link com.xiaoyou.face.widget.FaceRectView}上绘制矩形
 */
public class DrawHelper {
    private int previewWidth, previewHeight, canvasWidth, canvasHeight, cameraDisplayOrientation, cameraId;
    private boolean isMirror;
    private boolean mirrorHorizontal = false, mirrorVertical = false;

    /**
     * 创建一个绘制辅助类对象，并且设置绘制相关的参数
     *
     * @param previewWidth             预览宽度
     * @param previewHeight            预览高度
     * @param canvasWidth              绘制控件的宽度
     * @param canvasHeight             绘制控件的高度
     * @param cameraDisplayOrientation 旋转角度
     * @param cameraId                 相机ID
     * @param isMirror                 是否水平镜像显示（若相机是镜像显示的，设为true，用于纠正）
     * @param mirrorHorizontal         为兼容部分设备使用，水平再次镜像
     * @param mirrorVertical           为兼容部分设备使用，垂直再次镜像
     */
    public DrawHelper(int previewWidth, int previewHeight, int canvasWidth,
                      int canvasHeight, int cameraDisplayOrientation, int cameraId,
                      boolean isMirror, boolean mirrorHorizontal, boolean mirrorVertical) {
        this.previewWidth = previewWidth;
        this.previewHeight = previewHeight;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.cameraDisplayOrientation = cameraDisplayOrientation;
        this.cameraId = cameraId;
        this.isMirror = isMirror;
        this.mirrorHorizontal = mirrorHorizontal;
        this.mirrorVertical = mirrorVertical;
    }

    public void draw(FaceRectView faceRectView, List<DrawInfo> drawInfoList) {
        if (faceRectView == null) {
            return;
        }
        faceRectView.clearFaceInfo();
        if (drawInfoList == null || drawInfoList.size() == 0) {
            return;
        }
        faceRectView.addFaceInfo(drawInfoList);
    }

    /**
     * 调整人脸框用来绘制
     *
     * @param ftRect FT人脸框
     * @return 调整后的需要被绘制到View上的rect
     */
    public Rect adjustRect(Rect ftRect) {
        int previewWidth = this.previewWidth;
        int previewHeight = this.previewHeight;
        int canvasWidth = this.canvasWidth;
        int canvasHeight = this.canvasHeight;
        int cameraDisplayOrientation = this.cameraDisplayOrientation;
        int cameraId = this.cameraId;
        boolean isMirror = this.isMirror;
        boolean mirrorHorizontal = this.mirrorHorizontal;
        boolean mirrorVertical = this.mirrorVertical;

        if (ftRect == null) {
            return null;
        }

        Rect rect = new Rect(ftRect);
        float horizontalRatio;
        float verticalRatio;
        if (cameraDisplayOrientation % 180 == 0) {
            horizontalRatio = (float) canvasWidth / (float) previewWidth;
            verticalRatio = (float) canvasHeight / (float) previewHeight;
        } else {
            horizontalRatio = (float) canvasHeight / (float) previewWidth;
            verticalRatio = (float) canvasWidth / (float) previewHeight;
        }
        rect.left *= horizontalRatio;
        rect.right *= horizontalRatio;
        rect.top *= verticalRatio;
        rect.bottom *= verticalRatio;

        Rect newRect = new Rect();
        switch (cameraDisplayOrientation) {
            case 0:
                if (cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    newRect.left = canvasWidth - rect.right;
                    newRect.right = canvasWidth - rect.left;
                } else {
                    newRect.left = rect.left;
                    newRect.right = rect.right;
                }
                newRect.top = rect.top;
                newRect.bottom = rect.bottom;
                break;
            case 90:
                newRect.right = canvasWidth - rect.top;
                newRect.left = canvasWidth - rect.bottom;
                if (cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    newRect.top = canvasHeight - rect.right;
                    newRect.bottom = canvasHeight - rect.left;
                } else {
                    newRect.top = rect.left;
                    newRect.bottom = rect.right;
                }
                break;
            case 180:
                newRect.top = canvasHeight - rect.bottom;
                newRect.bottom = canvasHeight - rect.top;
                if (cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    newRect.left = rect.left;
                    newRect.right = rect.right;
                } else {
                    newRect.left = canvasWidth - rect.right;
                    newRect.right = canvasWidth - rect.left;
                }
                break;
            case 270:
                newRect.left = rect.top;
                newRect.right = rect.bottom;
                if (cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    newRect.top = rect.left;
                    newRect.bottom = rect.right;
                } else {
                    newRect.top = canvasHeight - rect.right;
                    newRect.bottom = canvasHeight - rect.left;
                }
                break;
            default:
                break;
        }

        /**
         * isMirror mirrorHorizontal finalIsMirrorHorizontal
         * true         true                false
         * false        false               false
         * true         false               true
         * false        true                true
         *
         * XOR
         */
        if (isMirror ^ mirrorHorizontal) {
            int left = newRect.left;
            int right = newRect.right;
            newRect.left = canvasWidth - right;
            newRect.right = canvasWidth - left;
        }
        if (mirrorVertical) {
            int top = newRect.top;
            int bottom = newRect.bottom;
            newRect.top = canvasHeight - bottom;
            newRect.bottom = canvasHeight - top;
        }
        return newRect;
    }

    /**
     * 绘制数据信息到view上，若 {@link DrawInfo#getName()} 不为null则绘制 {@link DrawInfo#getName()}
     *
     * @param canvas            需要被绘制的view的canvas
     * @param drawInfo          绘制信息
     * @param faceRectThickness 人脸框厚度
     * @param paint             画笔
     */
    public static void drawFaceRect(Canvas canvas, DrawInfo drawInfo, int faceRectThickness, Paint paint) {
        if (canvas == null || drawInfo == null) {
            return;
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(faceRectThickness);
        paint.setColor(drawInfo.getColor());
        paint.setAntiAlias(true);

        Path mPath = new Path();
        // 左上
        Rect rect = drawInfo.getRect();
        mPath.moveTo(rect.left, rect.top + rect.height() / 4);
        mPath.lineTo(rect.left, rect.top);
        mPath.lineTo(rect.left + rect.width() / 4, rect.top);
        // 右上
        mPath.moveTo(rect.right - rect.width() / 4, rect.top);
        mPath.lineTo(rect.right, rect.top);
        mPath.lineTo(rect.right, rect.top + rect.height() / 4);
        // 右下
        mPath.moveTo(rect.right, rect.bottom - rect.height() / 4);
        mPath.lineTo(rect.right, rect.bottom);
        mPath.lineTo(rect.right - rect.width() / 4, rect.bottom);
        // 左下
        mPath.moveTo(rect.left + rect.width() / 4, rect.bottom);
        mPath.lineTo(rect.left, rect.bottom);
        mPath.lineTo(rect.left, rect.bottom - rect.height() / 4);
        canvas.drawPath(mPath, paint);

        // 绘制文字，用最细的即可，避免在某些低像素设备上文字模糊
        paint.setStrokeWidth(1);

        if (drawInfo.getName() == null) {
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setTextSize(rect.width() / 8);

            String str = (drawInfo.getSex() == GenderInfo.MALE ? "MALE" : (drawInfo.getSex() == GenderInfo.FEMALE ? "FEMALE" : "UNKNOWN"))
                    + ","
                    + (drawInfo.getAge() == AgeInfo.UNKNOWN_AGE ? "UNKNOWN" : drawInfo.getAge())
                    + ","
                    + (drawInfo.getLiveness() == LivenessInfo.ALIVE ? "ALIVE" : (drawInfo.getLiveness() == LivenessInfo.NOT_ALIVE ? "NOT_ALIVE" : "UNKNOWN"));
            canvas.drawText(str, rect.left, rect.top - 10, paint);
        } else {
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setTextSize(rect.width() / 8);
            canvas.drawText(drawInfo.getName(), rect.left, rect.top - 10, paint);
        }
    }

    public void setPreviewWidth(int previewWidth) {
        this.previewWidth = previewWidth;
    }

    public void setPreviewHeight(int previewHeight) {
        this.previewHeight = previewHeight;
    }

    public void setCanvasWidth(int canvasWidth) {
        this.canvasWidth = canvasWidth;
    }

    public void setCanvasHeight(int canvasHeight) {
        this.canvasHeight = canvasHeight;
    }

    public void setCameraDisplayOrientation(int cameraDisplayOrientation) {
        this.cameraDisplayOrientation = cameraDisplayOrientation;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public void setMirror(boolean mirror) {
        isMirror = mirror;
    }

    public int getPreviewWidth() {
        return previewWidth;
    }

    public int getPreviewHeight() {
        return previewHeight;
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }

    public int getCameraDisplayOrientation() {
        return cameraDisplayOrientation;
    }

    public int getCameraId() {
        return cameraId;
    }

    public boolean isMirror() {
        return isMirror;
    }

    public boolean isMirrorHorizontal() {
        return mirrorHorizontal;
    }

    public void setMirrorHorizontal(boolean mirrorHorizontal) {
        this.mirrorHorizontal = mirrorHorizontal;
    }

    public boolean isMirrorVertical() {
        return mirrorVertical;
    }

    public void setMirrorVertical(boolean mirrorVertical) {
        this.mirrorVertical = mirrorVertical;
    }
}
