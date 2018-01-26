package com.example.huangchaoyang.myapplication.polygon;

import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author huangchaoyang
 * @date 18-1-26.
 */

public class Mesh {

    private static final String TAG = "Mesh";
    /**vertex*/
    private FloatBuffer verticesBuffer = null;

    /**vertex index*/
    private ShortBuffer indicesBuffer = null;

    /**The num of indices.*/
    private int numOfIndices = -1;

    /**Flat Color*/
    private float[]  rgba = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };

    /**Smooth Color*/
    private FloatBuffer colorBuffer = null;

    /**Translate params*/
    public float tX = 0;
    public float tY = 0;
    public float tZ = 0;

    /**Rotate params*/
    public float rX = 0;
    public float rY = 0;
    public float rZ = 0;

    public void draw(GL10 gl) {
        //counter-clockwise winding
        gl.glFrontFace(GL10.GL_CCW);
        //enable face culling
        gl.glEnable(GL10.GL_CULL_FACE);
        //which face will be remove
        gl.glCullFace(GL10.GL_BACK);

        //
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);
        gl.glColor4f(rgba[0], rgba[1], rgba[2], rgba[3]);
        if (colorBuffer != null) {
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
        }

        gl.glTranslatef(tX, tY, tZ);
        gl.glRotatef(rX, 1, 0, 0);
        gl.glRotatef(rY, 0, 1, 0);
        gl.glRotatef(rZ, 0, 0, 1);

        //draw vertices
        gl.glDrawElements(GL10.GL_TRIANGLES, numOfIndices, GL10.GL_UNSIGNED_SHORT, indicesBuffer);
        //close client
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        //close face culling
        gl.glDisable(GL10.GL_CULL_FACE);
    }

    protected void setVertices(float[] vertices){
        Log.d(TAG, "setVertices() called with: vertices = [" + Arrays.toString(vertices) + "]");
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        verticesBuffer = vbb.asFloatBuffer();
        verticesBuffer.put(vertices);
        verticesBuffer.position(0);
    }

    protected void setIndices(short[] indices){
        Log.d(TAG, "setIndices() called with: indices = [" + Arrays.toString(indices) + "]");
        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 4);
        ibb.order(ByteOrder.nativeOrder());
        indicesBuffer = ibb.asShortBuffer();
        indicesBuffer.put(indices);
        indicesBuffer.position(0);
        numOfIndices = indices.length;
    }

    protected void setColor(float r, float g, float b, float a){
        rgba[0] = r;
        rgba[1] = g;
        rgba[2] = b;
        rgba[3] = a;
    }

    protected void setColors(float[] colors){
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        colorBuffer = cbb.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);
    }



}
