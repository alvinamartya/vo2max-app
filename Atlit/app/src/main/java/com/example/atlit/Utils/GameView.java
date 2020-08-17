package com.example.atlit.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.atlit.R;

import java.util.List;

public class GameView extends SurfaceView implements Runnable {

    private Thread gameThread;
    private SurfaceHolder ourHolder;
    private volatile boolean playing;
    private Canvas canvas;
    private int shuttle = 0;
    private Bitmap bitmapRunningMan;
    private Bitmap bitmapRunningMan2;
    private boolean isMoving;
    private float runSpeedPerSecond = 1018;
    private float manXPos = 10, manYPos = 10;
    private int frameWidth = 130, frameHeight = 174;
    private int frameCount = 8;
    private int currentFrame = 0;
    private long fps;
    private long timeThisFrame;
    private long lastFrameChangeTime = 0;
    private int frameLengthInMillisecond = 50;
    private List<Float> seconds;

    private Rect frameToDraw = new Rect(0, 0, frameWidth, frameHeight);

    private RectF whereToDraw = new RectF(manXPos, manYPos, manXPos + frameWidth, frameHeight);

    public GameView(Context context, List<Float> seconds) {
        super(context);
        ourHolder = getHolder();
        bitmapRunningMan = BitmapFactory.decodeResource(getResources(), R.drawable.running_man);
        bitmapRunningMan = Bitmap.createScaledBitmap(bitmapRunningMan, frameWidth * frameCount, frameHeight, false);

        bitmapRunningMan2 = BitmapFactory.decodeResource(getResources(), R.drawable.running_man2);
        bitmapRunningMan2 = Bitmap.createScaledBitmap(bitmapRunningMan2, frameWidth * frameCount, frameHeight, false);
        this.seconds = seconds;
    }

    @Override
    public void run() {
        while (playing) {
            long startFrameTime = System.currentTimeMillis();
            update();
            draw();

            timeThisFrame = System.currentTimeMillis() - startFrameTime;

            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    public void update() {
        if (isMoving) {
            runSpeedPerSecond = ((float)getWidth() - (float) 10 - (float) 10) / seconds.get(shuttle);
            if (shuttle % 2 == 0) {
                manXPos = manXPos + runSpeedPerSecond / fps;
                if (manXPos >= getWidth() - 10) {
                    manYPos += frameHeight;
                    shuttle = shuttle + 1;
                    manXPos = getWidth() - 10;
                }
            } else {
                manXPos = manXPos - runSpeedPerSecond / fps;
                if (manXPos <= 10) {
                    manYPos += frameHeight;
                    shuttle = shuttle + 1;
                    manXPos = 10;
                }
            }
        }
    }

    public void manageCurrentFrame() {
        long time = System.currentTimeMillis();

        if (isMoving) {
            if (time > lastFrameChangeTime + frameLengthInMillisecond) {
                lastFrameChangeTime = time;
                currentFrame++;

                if (currentFrame >= frameCount) {
                    currentFrame = 0;
                }
            }
        }

        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;
    }

    public void draw() {
        if (ourHolder.getSurface().isValid() && shuttle % 2 == 0) {
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            whereToDraw.set((int) manXPos, (int) manYPos, (int) manXPos + frameWidth, (int) manYPos + frameHeight);
            manageCurrentFrame();
            canvas.drawBitmap(bitmapRunningMan, frameToDraw, whereToDraw, null);
            ourHolder.unlockCanvasAndPost(canvas);
            Log.e("right to left", "right to left");
        } else if (ourHolder.getSurface().isValid() && shuttle % 2 != 0) {
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            whereToDraw.set((int) manXPos, (int) manYPos, (int) manXPos + frameWidth, (int) manYPos + frameHeight);
            manageCurrentFrame();
            canvas.drawBitmap(bitmapRunningMan2, frameToDraw, whereToDraw, null);
            ourHolder.unlockCanvasAndPost(canvas);
            Log.e("left to right", "left to right");
        }
    }

    public void pause() {
        playing = false;

        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("ERR", "Joining Thread");
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void isRunning() {
        isMoving = !isMoving;
    }
}