package com.example.huangchaoyang.myapplication.polygon;

/**
 * @author huangchaoyang
 * @date 18-1-26.
 */

public class Plane extends Mesh {


    public Plane() {
        this(1, 1, 1, 1);
    }

    public Plane(float width, float height) {
        this(width, height, 1, 1);
    }

    public Plane(float width, float height, int sgWidth, int sgHeight) {
        int numOfVertex = (sgWidth + 1) * (sgHeight + 1);
        float[] vertices = new float[numOfVertex * 3];
        short[] indices = new short[numOfVertex * 6];
        float xOffset = width / -2;
        float yOffset = height / -2;
        float xWidth = width / sgWidth;
        float yHeight = height / sgHeight;
        int currentVertex = 0;
        int currentIndex = 0;
        short w = (short) (sgWidth + 1);
        for (int y = 0; y < sgHeight + 1; y++) {
            for (int x = 0; x < sgWidth + 1; x++) {
                //x
                vertices[currentVertex] = xOffset + x*xWidth;
                //y
                vertices[currentVertex + 1] = yOffset + y*yHeight;
                //z
                vertices[currentVertex + 2] = 0;
                currentVertex += 3;

                int n = y * (sgWidth + 1) + x;

                if(y < sgHeight && x < sgWidth) {
                    //set index
                    //piece 1
                    indices[currentIndex] = (short) n;
                    indices[currentIndex + 1] = (short) (n + 1);
                    indices[currentIndex + 2] = (short) (n + w);
                    //piece 2
                    indices[currentIndex + 3] = (short) (n + 1);
                    indices[currentIndex + 4] = (short) (n + 1 + w);
                    indices[currentIndex + 5] = (short) (n + w);

                    currentIndex += 6;
                }
            }
        }
        setColor(1f,1,1,1);
        setColors(new float[]{
                1f, 0f, 0f, 1f, //v1
                0f, 0f, 1f, 1f, //v2
                0f, 1f, 0f, 1f, //v3
                1f, 0f, 1f, 1f, //v4
        });
        setIndices(indices);
        setVertices(vertices);




    }
}
