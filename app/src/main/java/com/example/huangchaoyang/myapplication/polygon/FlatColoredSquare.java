package com.example.huangchaoyang.myapplication.polygon;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by huangchaoyang on 18-1-25.
 */

public class FlatColoredSquare extends Square {

    public FlatColoredSquare(){
        super();
    }
    @Override
    public void draw(GL10 gl) {
        gl.glColor4f(.5f,.5f,1f,1f);
        super.draw(gl);
    }
}
