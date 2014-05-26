package com.fanelli.crash;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

class Square {
	private FloatBuffer mFVertexBuffer;
	private ByteBuffer mColorBuffer;
	private ByteBuffer mIndexBuffer;
	private FloatBuffer textureBuffer;
	private int[] textures = new int[1];
	private float vertices[];
	private Rectangle rect;

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public Square(float[] vertices) {
		this.vertices = vertices;

		float textureVertices[] = { 0.0f, 1.0f, // top left (V2)
				0.0f, 0.0f, // bottom left (V1)
				1.0f, 1.0f, // top right (V4)
				1.0f, 0.0f // bottom right (V3)
		};
		byte maxColor = (byte) 255;

		byte colors[]=  { maxColor, maxColor, maxColor, maxColor, maxColor,
				maxColor, maxColor, maxColor, maxColor, maxColor, maxColor,
				maxColor, maxColor, maxColor, maxColor, maxColor };

		byte indices[] = { 0, 3, 1, 0, 2, 3 };
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		mFVertexBuffer = vbb.asFloatBuffer();
		mFVertexBuffer.put(vertices);
		mFVertexBuffer.position(0);
		mColorBuffer = ByteBuffer.allocateDirect(colors.length);
		mColorBuffer.put(colors);
		mColorBuffer.position(0);
		mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
		mIndexBuffer.put(indices);
		mIndexBuffer.position(0);
		vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		textureBuffer = vbb.asFloatBuffer();
		textureBuffer.put(textureVertices);
		textureBuffer.position(0);
		// collision shap
		rect = new Rectangle(vertices[0], vertices[1], Math.abs(vertices[0]
				- vertices[6]), Math.abs(vertices[4] - vertices[1]));
	}

	public void draw(GL10 gl) {
		// bind the previously generated texture
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

		// Point to our buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		// Set the face rotation
		gl.glFrontFace(GL10.GL_CW);

		// Point to our vertex buffer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mFVertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		

		// Draw the vertices as triangle strip
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

		// Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, 0, mColorBuffer);
	}

	public void loadGLTexture(GL10 gl, Context context) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.mario2);
		gl.glGenTextures(1, textures, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		bitmap.recycle();
	}
	
	public void moveRect(float x, float y){
		rect.lowerLeft.add(x, y); 
	}
	public float getXLowerLeftRect(){
		return rect.lowerLeft.x;
	}
	public float getYLowerLeftRect(){
		return rect.lowerLeft.y;
	}

}
