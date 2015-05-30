package com.example.mobile.sampleopenglplayer;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;


public class MainActivity extends Activity implements SurfaceTexture.OnFrameAvailableListener {
    private GLSurfaceView glSurfaceView;
    private SampleGLRenderer glRenderer;
    private SampleMediaCodec sampleMediaCodec;
    private SurfaceTexture surfaceTexture;
    private Surface surface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sampleMediaCodec = new SampleMediaCodec();
        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLContextClientVersion(2);
        glRenderer = new SampleGLRenderer();
        glSurfaceView.setRenderer(glRenderer);
        surfaceTexture = null;

        setContentView(glSurfaceView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_play) {
            // Create SurfaceTexture and set callback for frame update event triggered by
            // MediaCodec releaseOutputBuffer()
            if (surfaceTexture == null) {
                surfaceTexture = new SurfaceTexture(glRenderer.getTextureHandle());
                surfaceTexture.setOnFrameAvailableListener(this);
                surface = new Surface(surfaceTexture);
            }
            sampleMediaCodec.play(this, surface, "sdcard/Movies/h264_1080p.mp4");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        // SurfaceTexture.updateTexImage() cannot be called in non OpenGL context.
        glRenderer.updateTexture(surfaceTexture);
    }
}
