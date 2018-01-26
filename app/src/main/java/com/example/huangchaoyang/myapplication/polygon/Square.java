package com.example.huangchaoyang.myapplication.polygon;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by huangchaoyang on 18-1-16.
 * @author huangchaoyang
 * Square
 */

public class Square {

    /**
     * 顶点
     */
    protected float[] vertices = {
            -1.0f,1.0f,0.0f,
            -1.0f,-1.0f,0.0f,
            1.0f,-1.0f,0.0f,
            1.0f,1.0f,0.0f,
    };

    /**顶点连接顺序 三个一组*/
    protected short[] indices = {
            0, 1, 2,
            0, 2, 3
    };

    /**顶点的缓冲区*/
    protected FloatBuffer vertexBuffer;
    /**索引缓冲区*/
    protected ShortBuffer indexBuffer;

    public Square(){
        /*
         a float is 4 bytes, therefore we
         multiply the number if
         vertices with 4.
         */
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer =vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        /*
         short is 2 bytes, therefore we multiply
         the number if
         indices with 2.
          */
        ByteBuffer ibb
                = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);

    }

    public void draw(GL10 gl){
        /*
        * 作用是控制多边形的正面是如何决定的。在默认情况下，mode是GL_CCW
        * mode的值为：
        * GL_CCW 表示窗口坐标上投影多边形的顶点顺序为逆时针方向的表面为正面。
        * GL_CW 表示顶点顺序为顺时针方向的表面为正面。
        * */
        gl.glFrontFace(GL10.GL_CCW);
        /*
        * 根据函数glCullFace要求启用隐藏图形材料的面。
        * */
        gl.glEnable(GL10.GL_CULL_FACE);

        /*
        * glEnable(GL_CULL_FACE) 开启剔除操作效果
        * glDisable(GL_CULL_FACE)关闭剔除操作效果
        * glCullFace()参数包括
        * GL_FRONT
        * GL_BACK
        * 表示禁用多边形正面或者背面上的光照、阴影和颜色计算及操作，消除不必要的渲染计算。
        * */
        gl.glCullFace(GL10.GL_BACK);
        /*
        * glEnableClientState和glDisableClientState启用或禁用客户端的单个功能。默认的，所有客户端功能禁用。
        * GL_COLOR_ARRAY
        * 如果启用，颜色矩阵可以用来写入以及调用glDrawArrays方法或者glDrawElements方法时进行渲染。详见glColorPointer。
        * GL_NORMAL_ARRAY
        * 如果启用，法线矩阵可以用来写入以及调用glDrawArrays方法或者glDrawElements方法时进行渲染。详见glNormalPointer。
        * GL_TEXTURE_COORD_ARRAY
        * 如果启用，纹理坐标矩阵可以用来写入以及调用glDrawArrays方法或者glDrawElements方法时进行渲染。详见glTexCoordPointer。
        * GL_VERTEX_ARRAY
        * 如果启用，顶点矩阵可以用来写入以及调用glDrawArrays方法或者glDrawElements方法时进行渲染。详见glVertexPointer。
        * GL_POINT_SIZE_ARRAY_OES(OES_point_size_arrayextension)
        * 如果启用，点大小矩阵控制大小以渲染点和点sprites。
        * 这时由glPointSize定义的点大小将被忽略，由点大小矩阵提供的大小将被用来渲染点和点sprites。详见glPointSize。
        * */
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        /*
        * 定义一个顶点坐标矩阵。
        * size：指定了每个顶点对应的坐标个数，只能是2,3,4中的一个，默认值是4
        * type：指定了数组中每个顶点坐标的数据类型，可取常量:GL_BYTE, GL_SHORT,GL_FIXED(0x10000),GL_FLOAT;
        * stride:指定了连续顶点间的字节排列方式，如果为0，数组中的顶点就会被认为是按照紧凑方式排列的，默认值为0;
        * pointer:顶点数组
        * */
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        /*
        * 由矩阵数据渲染图元。
        * mode指定绘制图元的类型，它应该是下列值之一:
        * GL_POINTS, GL_LINE_STRIP, GL_LINE_LOOP, GL_LINES, GL_TRIANGLE_STRIP, GL_TRIANGLE_FAN,
        * GL_TRIANGLES, GL_QUAD_STRIP, GL_QUADS, and GL_POLYGON.
        * count为绘制图元的数量乘上一个图元的顶点数。
        * type为索引值的类型，只能是下列值之一：
        * GL_UNSIGNED_BYTE, GL_UNSIGNED_SHORT。
        * indices：指向索引存贮位置的指针。
        * */
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
                GL10.GL_UNSIGNED_SHORT, indexBuffer);

        //close资源

        /*客户端禁用GL_VERTEX_ARRAY*/
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        /*服务端禁用*/
        gl.glDisable(GL10.GL_CULL_FACE);

    }


}
