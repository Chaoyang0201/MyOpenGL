package com.example.huangchaoyang.myapplication;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.huangchaoyang.myapplication.polygon.SimplePlane;
import com.google.gson.Gson;

/**
 * @author huangchaoyang
 *
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActionBar().hide();
        GLSurfaceView view = new GLSurfaceView(this);
        OpenGLRenderer renderer = new OpenGLRenderer();
        view.setRenderer(renderer);
        setContentView(view);
        SimplePlane plane = new SimplePlane(1, 1);

        // Move and rotate the plane.
        plane.z = 1.7f;
        plane.rx = -65;

        // Load the texture.
        plane.loadBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher));

        // Add the plane to the renderer.
        renderer.addMesh(plane);

    }
}
