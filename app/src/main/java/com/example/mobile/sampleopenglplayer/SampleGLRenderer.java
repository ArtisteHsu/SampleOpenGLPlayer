package com.example.mobile.sampleopenglplayer;

import android.content.res.Resources;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SampleGLRenderer implements GLSurfaceView.Renderer {
    private SampleGLES20Video sampleGL20Video;
    private SurfaceTexture surfaceTexture;
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        sampleGL20Video = new SampleGLES20Video();
        surfaceTexture = null;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if(surfaceTexture != null) {
            // Here is right context of calling SurfaceTexture.updateTexImage()
            surfaceTexture.updateTexImage();
            surfaceTexture = null;
        }
        sampleGL20Video.draw();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    public int getTextureHandle() {
        return sampleGL20Video.getTextureHandle();
    }

    public void updateTexture(SurfaceTexture st) {
        // Update SurfaceText pointer to update texture in next call of onDrawFrame()
        surfaceTexture = st;
    }
}
