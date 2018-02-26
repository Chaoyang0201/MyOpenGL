package com.example.huangchaoyang.myapplication;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.view.View;

import com.example.huangchaoyang.myapplication.polygon.Cube;
import com.example.huangchaoyang.myapplication.polygon.Group;
import com.example.huangchaoyang.myapplication.polygon.Mesh;
import com.example.huangchaoyang.myapplication.polygon.SimplePanel;
import com.example.huangchaoyang.myapplication.polygon.SmoothColoredSquare;
import com.example.huangchaoyang.myapplication.polygon.Square;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by huangchaoyang on 18-1-12.
 * @author huangchaoyang
 * OpenGLRenderer
 */

class OpenGLRenderer implements GLSurfaceView.Renderer {
    private final Group root;
    Square square = new SmoothColoredSquare();
    Mesh mesh = new Cube(2,2,2);
    private FloatBuffer textureBuffer;
    private SimplePanel panel = new SimplePanel(200,200);

    public OpenGLRenderer(){
        root = new Group();
    }
    /**
     * Called when the surface is created or recreated.
     * @param gl the GL interface. Use <code>instanceof</code> to
     * test if the interface supports GL11 or higher interfaces.
     * @param eglConfig the EGLConfig of the created surface. Can be used
     * to create matching pbuffers.
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
            // Set the background color to black ( rgba ).
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        // Enable Smooth Shading, default not really needed.
        //mode   指明使用哪种着色技术，可以取值GL_FLAT和GL_SMOOTH。默认取值是GL_SMOOTH。
        gl.glShadeModel(GL10.GL_SMOOTH);
        // Depth buffer setup.
        gl.glClearDepthf(1.0f);
        // Enables depth testing. 启用了之后，OpenGL在绘制的时候就会检查，当前像素前面是否有别的像素，如果别的像素挡道了它，那它就不会绘制，也就是说，OpenGL就只绘制最前面的一层。
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // The type of depth testing to do. OpenGL的一个函数，用于指定深度缓冲比较值。
        /*  GL_NEVER,GL_LESS,GL_EQUAL,GL_LEQUAL,GL_GREATER,GL_NOTEQUAL,GL_GEQUAL,GL_ALWAYS,缺省值GL_LESS。
            GL_NEVER,不通过（输入的深度值不取代参考值）
            GL_LESS,如果输入的深度值小于参考值，则通过
            GL_EQUAL,如果输入的深度值等于参考值，则通过
            GL_LEQUAL,如果输入的深度值小于或等于参考值，则通过
            GL_GREATER,如果输入的深度值大于参考值，则通过
            GL_NOTEQUAL,如果输入的深度值不等于参考值，则通过
            GL_GEQUAL,如果输入的深度值大于或等于参考值，则通过
            GL_ALWAYS,总是通过（输入的深度值取代参考值）*/
        gl.glDepthFunc(GL10.GL_LEQUAL);
        // Really nice perspective calculations.
        /* param 1 target :
        GL_FOG_HINT 指示雾计算的准确性。
        如果 OpenGL 实现不有效地支持每像素雾计算，提示 GL_DONT_CARE 或 GL_FASTEST 会导致每个顶点雾化效果计算。
        GL_LINE_SMOOTH_HINT 指示是锯消除行的采样质量。
        如果应用了一个较大的筛选器函数，则将提示 GL_NICEST 可能会导致生成过程中栅格化，更多像素碎片。
        GL_PERSPECTIVE_CORRECTION_HINT 表示颜色和纹理坐标插补的质量。
        如果角度更正参数插值不有效地支持由 OpenGL 实现，提示 GL_DONT_CARE 或 GL_FASTEST 可以导致简单线性插值的颜色和/或纹理坐标。
        GL_POINT_SMOOTH_HINT 表示是锯消除点采样的质量。
        如果应用了一个较大的筛选器函数， 则将提示 GL_NICEST 可能会导致生成 过程中栅格化，更多像素碎片。
        GL_POLYGON_SMOOTH_HINT 指示是锯消除多边形的采样质量 。
        如果应用了一个较大的筛选器函数，则将提示GL_NICEST 可能会导致生 成过程中栅格化， 更多像素碎片。*/
        /* param 2 mode :
        GL_FASTEST
        应选择最有效的选项。
        GL_NICEST
        应选择最正确或最高质量的选项。
        GL_DONT_CARE
        客户端没有一个首选项。
         */
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, 
                GL10.GL_NICEST);
    }

    /**
     * Called when the surface changed size.
     *
     *  Called after the surface is created and whenever
     * the OpenGL ES surface size changes.
     *
     * @param gl the GL interface. Use <code>instanceof</code> to
     * test if the interface supports GL11 or higher interfaces.
     * @param width
     * @param height
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        // Sets the current view port to the new size.
        /*
        X，Y————以像素为单位，指定了视口的左下角（在第一象限内，以（0，0）为原点的）位置。
        width，height————表示这个视口矩形的宽度和高度，根据窗口的实时变化重绘窗口。
        */
        gl.glViewport(0, 0, width, height);
        // Select the projection matrix
        /*
        mode 指定哪一个矩阵堆栈是下一个矩阵操作的目标,可选值: GL_MODELVIEW、GL_PROJECTION、GL_TEXTURE.
        说明
        glMatrixMode设置当前矩阵模式:
        GL_MODELVIEW,对模型视景矩阵堆栈应用随后的矩阵操作.
        GL_PROJECTION,对投影矩阵应用随后的矩阵操作.
        GL_TEXTURE,对纹理矩阵堆栈应用随后的矩阵操作.
        与glLoadIdentity()一同使用
        glLoadIdentity():将当前的用户坐标系的原点移到了屏幕中心：类似于一个复位操作
        在glLoadIdentity()之后我们为场景设置了透视图。glMatrixMode(GL_MODELVIEW)设置当前矩阵为模型视图矩阵，模型视图矩阵储存了有关物体的信息。
        */
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // Reset the projection matrix
        /*
        将当前的用户坐标系的原点移到了屏幕中心：类似于一个复位操作
        1.X坐标轴从左至右，Y坐标轴从下至上，Z坐标轴从里至外。
        2.OpenGL屏幕中心的坐标值是X和Y轴上的0.0f点。
        3.中心左面的坐标值是负值，右面是正值。
        移向屏幕顶端是正值，移向屏幕底端是负值。
        移入屏幕深处是负值，移出屏幕则是正值。
        */
        gl.glLoadIdentity();
        // Calculate the aspect ratio of the window
        /*
         * Set up a perspective projection matrix
         *
         * @param gl a GL10 interface
         * @param fovy specifies the field of view angle, in degrees, in the Y
         *        direction.
         * @param aspect specifies the aspect ration that determins the field of
         *        view in the x direction. The aspect ratio is the ratio of x
         *        (width) to y (height).
         * @param zNear specifies the distance from the viewer to the near clipping
         *        plane (always positive).
         * @param zFar specifies the distance from the viewer to the far clipping
         *        plane (always positive).
         */
        GLU.gluPerspective(gl, 45.0f,
                (float) width / (float) height,
                0.1f, 100.0f);
        // Select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // Reset the modelview matrix
        gl.glLoadIdentity();

    }
    private float angle = 0f;

    int[] textures = new int[1];

    /**
     * Called to draw the current frame.
     * @param gl the GL interface. Use <code>instanceof</code> to
     * test if the interface supports GL11 or higher interfaces.
     */
    @Override
    public void onDrawFrame(GL10 gl) {
        // Clears the screen and depth buffer.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT |
                GL10.GL_DEPTH_BUFFER_BIT);


        //重置坐标系
        gl.glLoadIdentity();
        /*
        * 沿着 X, Y 和 Z 轴移动。
        * 注意在glTranslatef(x, y, z)中,移动的时候，并不是相对屏幕中心移动，而是相对与当前所在的屏幕位置。
        * 其作用就是将你绘点坐标的原点在当前原点的基础上平移一个(x,y,z)向量。
        * */

        gl.glTranslatef(0,0,-10);
        root.draw(gl);

    /**
     * Adds a mesh to the root.
     *
     * @param mesh
     *            the mesh to add.
     */
    public void addMesh(Mesh mesh) {
        root.add(mesh);
    }
}
