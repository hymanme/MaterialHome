/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hymane.materialhome.module.zxing;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.hymane.materialhome.R;
import com.hymane.materialhome.module.zxing.camera.CameraManager;

import java.util.Collection;
import java.util.HashSet;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder rectangle and partial
 * transparency outside it, as well as the laser scanner animation and result points.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ViewfinderView extends View {

    private static final int[] SCANNER_ALPHA = {0, 64, 128, 192, 255, 192, 128, 64};
    private static final long ANIMATION_DELAY = 100L;
    private static final int OPAQUE = 0xFF;

    private final Paint paint;
    private Bitmap resultBitmap;
    private final int maskColor;
    private final int resultColor;
    //    private final int frameColor;
//    private final int laserColor;
//    private final int resultPointColor;
    private int i = 0;// 添加的
    private Rect mRect;// 扫描线填充边界
    private GradientDrawable mDrawable;// 采用渐变图作为扫描线
    private Drawable lineDrawable;// 采用图片作为扫描线
    private int scannerAlpha;
    private Collection<ResultPoint> possibleResultPoints;
    private Collection<ResultPoint> lastPossibleResultPoints;

    // This constructor is used when the class is built from an XML resource.
    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize these once for performance rather than calling them every time in onDraw().
        paint = new Paint();
        mRect = new Rect();
        lineDrawable = getResources().getDrawable(R.drawable.zx_code_line);
        Resources resources = getResources();
        maskColor = resources.getColor(R.color.viewfinder_mask);
        resultColor = resources.getColor(R.color.result_view);
//        frameColor = resources.getColor(R.color.viewfinder_frame);
//        laserColor = resources.getColor(R.color.viewfinder_laser);
//        resultPointColor = resources.getColor(R.color.possible_result_points);
        scannerAlpha = 0;
        possibleResultPoints = new HashSet<ResultPoint>(5);
    }

    @Override
    public void onDraw(Canvas canvas) {
        Rect frame = CameraManager.get().getFramingRect();
        if (frame == null) {
            return;
        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(OPAQUE);
            canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
        } else {

            // Draw a two pixel solid black border inside the framing rect
//            paint.setColor(frameColor);
//            canvas.drawRect(frame.left, frame.top, frame.right + 1, frame.top + 2, paint);
//            canvas.drawRect(frame.left, frame.top + 2, frame.left + 2, frame.bottom - 1, paint);
//            canvas.drawRect(frame.right - 1, frame.top, frame.right + 1, frame.bottom - 1, paint);
//            canvas.drawRect(frame.left, frame.bottom - 1, frame.right + 1, frame.bottom + 1, paint);
//
//            // Draw a red "laser scanner" line through the middle to show decoding is active
//            paint.setColor(laserColor);
//            paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
//            scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
//            int middle = frame.height() / 2 + frame.top;
//            canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, paint);
//
//            Collection<ResultPoint> currentPossible = possibleResultPoints;
//            Collection<ResultPoint> currentLast = lastPossibleResultPoints;
//            if (currentPossible.isEmpty()) {
//                lastPossibleResultPoints = null;
//            } else {
//                possibleResultPoints = new HashSet<ResultPoint>(5);
//                lastPossibleResultPoints = currentPossible;
//                paint.setAlpha(OPAQUE);
//                paint.setColor(resultPointColor);
//                for (ResultPoint point : currentPossible) {
//                    canvas.drawCircle(frame.left + point.getX(), frame.top + point.getY(), 6.0f, paint);
//                }
//            }
//            if (currentLast != null) {
//                paint.setAlpha(OPAQUE / 2);
//                paint.setColor(resultPointColor);
//                for (ResultPoint point : currentLast) {
//                    canvas.drawCircle(frame.left + point.getX(), frame.top + point.getY(), 3.0f, paint);
//                }
//            }

            // 画出四个角
            paint.setColor(getResources().getColor(R.color.green));
            // 左上角
            canvas.drawRect(frame.left, frame.top, frame.left + 15,
                    frame.top + 5, paint);
            canvas.drawRect(frame.left, frame.top, frame.left + 5,
                    frame.top + 15, paint);
            // 右上角
            canvas.drawRect(frame.right - 15, frame.top, frame.right,
                    frame.top + 5, paint);
            canvas.drawRect(frame.right - 5, frame.top, frame.right,
                    frame.top + 15, paint);
            // 左下角
            canvas.drawRect(frame.left, frame.bottom - 5, frame.left + 15,
                    frame.bottom, paint);
            canvas.drawRect(frame.left, frame.bottom - 15, frame.left + 5,
                    frame.bottom, paint);
            // 右下角
            canvas.drawRect(frame.right - 15, frame.bottom - 5, frame.right,
                    frame.bottom, paint);
            canvas.drawRect(frame.right - 5, frame.bottom - 15, frame.right,
                    frame.bottom, paint);

            // 在扫描框中画出模拟扫描的线条
            // 设置扫描线条颜色为绿色
            paint.setColor(getResources().getColor(R.color.green));
            // 设置绿色线条的透明值
            paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
            // 透明度变化
            scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;

            // 画出固定在中部的线条
            // int middle = frame.height() / 2 + frame.top;
            // canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1,
            // middle + 2, paint);

            // 将扫描线修改为上下走的线
            if ((i += 5) < frame.bottom - frame.top) {
                /* 以下为用渐变线条作为扫描线 */
                // 渐变图为矩形
                // mDrawable.setShape(GradientDrawable.RECTANGLE);
                // 渐变图为线型
                // mDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                // 线型矩形的四个圆角半径
                // mDrawable
                // .setCornerRadii(new float[] { 8, 8, 8, 8, 8, 8, 8, 8 });
                // 位置边界
                // mRect.set(frame.left + 10, frame.top + i, frame.right - 10,
                // frame.top + 1 + i);
                // 设置渐变图填充边界
                // mDrawable.setBounds(mRect);
                // 画出渐变线条
                // mDrawable.draw(canvas);

				/* 以下为图片作为扫描线 */
                mRect.set(frame.left - 6, frame.top + i - 6, frame.right + 6,
                        frame.top + 6 + i);
                lineDrawable.setBounds(mRect);
                lineDrawable.draw(canvas);

                // 刷新
                invalidate();
            } else {
                i = 0;
            }

            // Request another update at the animation interval, but only repaint the laser line,
            // not the entire viewfinder mask.
            postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom);
        }
    }

    public void drawViewfinder() {
        resultBitmap = null;
        invalidate();
    }

    /**
     * Draw a bitmap with the result points highlighted instead of the live scanning display.
     *
     * @param barcode An image of the decoded barcode.
     */
    public void drawResultBitmap(Bitmap barcode) {
        resultBitmap = barcode;
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {
        possibleResultPoints.add(point);
    }

}
