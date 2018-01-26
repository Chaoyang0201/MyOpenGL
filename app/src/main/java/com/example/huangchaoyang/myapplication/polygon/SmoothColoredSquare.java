package com.example.huangchaoyang.myapplication.polygon;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by huangchaoyang on 18-1-25.
 */

public class SmoothColoredSquare extends FlatColoredSquare {

    protected final FloatBuffer colorBuffer;

    float[] colors = {
            1f,0f,0f,1f, //v1
            0f,1f,0f,1f, //v2
            0f,0f,1f,1f, //v3
            1f,0f,1f,1f, //v4
    };

    public SmoothColoredSquare(){
        super();
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        colorBuffer = cbb.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);
    }

    @Override
    public void draw(GL10 gl) {
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,vertexBuffer);

        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glColorPointer(4,GL10.GL_FLOAT,0,colorBuffer);


        gl.glColor4f(.5f,.5f,1f,1f);
        super.draw(gl);

        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }
}
