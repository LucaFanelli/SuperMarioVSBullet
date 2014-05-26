package com.fanelli.crash;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLSurfaceView;

class SquareRenderer implements GLSurfaceView.Renderer {
	private boolean mTranslucentBackground;
	private Square[] mSquare = new Square[3];
	private Wall[] brick = new Wall[8];
	private Bullet bullet;
	private float mTransY;
	private float mTransX;
	private float bulletTransX;
	private Context context;
	private boolean crash = false;
	private boolean up = false;
	private boolean left = false;
	private boolean right = false;
	private float space = .08f;
	private float spaceBullet = .07f;

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	private float verticesMario[] = {

	-1.0f, -3.0f, 0.0f, // V1 - bottom left

			-1.0f, -1.0f, 0.0f, // V2 - top left

			1.0f, -3.0f, 0.0f, // V3 - bottom right

			1.0f, -1.0f, 0.0f // V4 - top right
	};
	private float verticesBullet[] = {

	-6.0f, -2.0f, 0.0f, // V1 - bottom left

			-6.0f, -1.0f, 0.0f, // V2 - top left

			-4.0f, -2.0f, 0.0f, // V3 - bottom right

			-4.0f, -1.0f, 0.0f // V4 - top right
	};
	private float verticesDown2[] = {

	-1.0f, -4.0f, 0.0f, // V1 - bottom left

			-1.0f, -3.0f, 0.0f, // V2 - top left

			1.0f, -4.0f, 0.0f, // V3 - bottom right

			1.0f, -3.0f, 0.0f // V4 - top right
	};
	private float verticesDown1[] = {

	-3.0f, -4.0f, 0.0f, // V1 - bottom left

			-3.0f, -3.0f, 0.0f, // V2 - top left

			-1.0f, -4.0f, 0.0f, // V3 - bottom right

			-1.0f, -3.0f, 0.0f // V4 - top right
	};
	private float verticesDown3[] = {

	1.0f, -4.0f, 0.0f, // V1 - bottom left

			1.0f, -3.0f, 0.0f, // V2 - top left

			3.0f, -4.0f, 0.0f, // V3 - bottom right

			3.0f, -3.0f, 0.0f // V4 - top right
	};
	private float verticesRight[] = {

	3.0f, -3.0f, 0.0f, // V1 - bottom left

			3.0f, -2.0f, 0.0f, // V2 - top left

			5.0f, -3.0f, 0.0f, // V3 - bottom right

			5.0f, -2.0f, 0.0f // V4 - top right
	};

	private float verticesLeft[] = {

	-5.0f, -3.0f, 0.0f, // V1 - bottom left

			-5.0f, -2.0f, 0.0f, // V2 - top left

			-3.0f, -3.0f, 0.0f, // V3 - bottom right

			-3.0f, -2.0f, 0.0f // V4 - top right
	};

	private float verticesWall1[] = {

	-1.0f, 4.0f, 0.0f, // V1 - bottom left

			-1.0f, 5.0f, 0.0f, // V2 - top left

			1.0f, 4.0f, 0.0f, // V3 - bottom right

			1.0f, 5.0f, 0.0f // V4 - top right
	};
	private float verticesWall2[] = {

	1.0f, 4.0f, 0.0f, // V1 - bottom left

			1.0f, 5.0f, 0.0f, // V2 - top left

			3.0f, 4.0f, 0.0f, // V3 - bottom right

			3.0f, 5.0f, 0.0f // V4 - top right
	};
	private float verticesWall3[] = {

	-3.0f, 4.0f, 0.0f, // V1 - bottom left

			-3.0f, 5.0f, 0.0f, // V2 - top left

			-1.0f, 4.0f, 0.0f, // V3 - bottom right

			-1.0f, 5.0f, 0.0f // V4 - top right
	};

	public SquareRenderer(boolean useTranslucentBackground, Context c) {
		mTranslucentBackground = useTranslucentBackground;
		context = c;
		mSquare[0] = new Square(verticesMario);
		bullet = new Bullet(verticesBullet);
		brick[0] = new Wall(verticesWall1);
		brick[1] = new Wall(verticesWall2);
		brick[2] = new Wall(verticesWall3);
		brick[3] = new Wall(verticesDown2);
		brick[4] = new Wall(verticesRight);
		brick[5] = new Wall(verticesLeft);
		brick[6] = new Wall(verticesDown1);
		brick[7] = new Wall(verticesDown3);
	}

	public void onDrawFrame(GL10 gl) {

		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		// BULLET
		if (crashRectangles(mSquare[0].getRect(), bullet.getRect())) {
			gl.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
		} else
			gl.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
		if (bulletTransX + spaceBullet >= 12) {
			bullet.resetRect();
		} else
			bullet.moveRect(+spaceBullet, 0);
		bulletTransX = (bulletTransX + spaceBullet) % 12;

		gl.glTranslatef(bulletTransX, 0, 0.0f);
		bullet.draw(gl);
		// BULLET
		gl.glLoadIdentity();
		if (up == true) {
			if (crash == false) {

				mTransY += space;
				gl.glTranslatef(0, mTransY, 0.0f);
				mSquare[0].moveRect(0, +space);
				if (crashRectangles(mSquare[0].getRect(), brick[1].getRect())) {
					crash = true;

				}

			} else {

				mTransY -= space;
				gl.glTranslatef(0, mTransY, 0.0f);
				mSquare[0].moveRect(0, -space);
				if (mSquare[0].getRect().lowerLeft.y <= -3) {
					crash = false;

					up = false;

				}
			}

		} else if (right == true) {
			if (crash == false) {

				mTransX += space;
				gl.glTranslatef(mTransX, 0, 0.0f);
				mSquare[0].moveRect(+space, 0);
				if (crashRectangles(mSquare[0].getRect(), brick[4].getRect())) {
					crash = true;

				}

			} else {

				mTransX -= space;
				gl.glTranslatef(mTransX, 0, 0.0f);
				mSquare[0].moveRect(-space, 0);
				if (mSquare[0].getRect().lowerLeft.x <= -1) {
					crash = false;

					right = false;

				}
			}

		} else if (left == true) {
			if (crash == false) {

				mTransX -= space;
				gl.glTranslatef(mTransX, 0, 0.0f);
				mSquare[0].moveRect(-space, 0);
				if (crashRectangles(mSquare[0].getRect(), brick[5].getRect())) {
					crash = true;

				}

			} else {

				mTransX += space;
				gl.glTranslatef(mTransX, 0, 0.0f);
				mSquare[0].moveRect(+space, 0);
				if (mSquare[0].getRect().lowerLeft.x >= -1) {
					crash = false;

					left = false;

				}
			}

		}
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		mSquare[0].draw(gl);
		gl.glLoadIdentity();
		brick[0].draw(gl);
		gl.glLoadIdentity();
		brick[1].draw(gl);
		gl.glLoadIdentity();
		brick[2].draw(gl);
		gl.glLoadIdentity();
		brick[3].draw(gl);
		gl.glLoadIdentity();
		brick[4].draw(gl);
		gl.glLoadIdentity();
		brick[5].draw(gl);
		gl.glLoadIdentity();
		brick[6].draw(gl);
		gl.glLoadIdentity();
		brick[7].draw(gl);

		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(-5, 5, -5, 5, 1, -1);// parallel projection
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Load the texture for the square
		mSquare[0].loadGLTexture(gl, this.context);
		brick[0].loadGLTexture(gl, this.context);
		brick[1].loadGLTexture(gl, this.context);
		brick[2].loadGLTexture(gl, this.context);
		brick[3].loadGLTexture(gl, this.context);
		brick[4].loadGLTexture(gl, this.context);
		brick[5].loadGLTexture(gl, this.context);
		brick[6].loadGLTexture(gl, this.context);
		brick[7].loadGLTexture(gl, this.context);
		bullet.loadGLTexture(gl, this.context);

		gl.glEnable(GL10.GL_TEXTURE_2D); // Enable Texture Mapping ( NEW )
		gl.glShadeModel(GL10.GL_SMOOTH); // Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClearDepthf(1.0f); // Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST); // Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL); // The Type Of Depth Testing To Do

		// Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}

	public boolean detectCollissions(Thing from) {

		return true;
	}

	public boolean crashRectangles(Rectangle r1, Rectangle r2) {
		float r1Vertex1X = r1.lowerLeft.x;// up left
		float r1Vertex2X = r1.lowerLeft.x;// bottom left
		float r1Vertex3X = r1.lowerLeft.x + r1.width;// bottom right
		float r1Vertex4X = r1.lowerLeft.x + r1.width;// up right
		float r1Vertex1Y = r1.height + r1.lowerLeft.y;
		float r1Vertex2Y = r1.lowerLeft.y;
		float r1Vertex3Y = r1.lowerLeft.y;
		float r1Vertex4Y = r1.height + r1.lowerLeft.y;

		float r2Vertex1X = r2.lowerLeft.x;// up left
		float r2Vertex2X = r2.lowerLeft.x;// bottom left
		float r2Vertex3X = r2.lowerLeft.x + r2.width;// bottom right
		float r2Vertex4X = r2.lowerLeft.x + r2.width;// up right
		float r2Vertex1Y = r2.height + r2.lowerLeft.y;
		float r2Vertex2Y = r2.lowerLeft.y;
		float r2Vertex3Y = r2.lowerLeft.y;
		float r2Vertex4Y = r2.height + r2.lowerLeft.y;
		// analysis of vertices of the first rect
		
		if ((r1Vertex1X >= r2Vertex1X && r1Vertex1X <= r2Vertex3X)
				&& (r1Vertex1Y >= r2Vertex2Y && r1Vertex1Y <= r2Vertex1Y))
			return true;

		else if ((r1Vertex2X >= r2Vertex1X && r1Vertex2X <= r2Vertex3X)
				&& (r1Vertex2Y >= r2Vertex2Y && r1Vertex2Y <= r2Vertex1Y))
			return true;
		else if ((r1Vertex3X >= r2Vertex1X && r1Vertex3X <= r2Vertex3X)
				&& (r1Vertex3Y >= r2Vertex2Y && r1Vertex3Y <= r2Vertex1Y))
			return true;

		else if ((r1Vertex4X >= r2Vertex1X && r1Vertex4X <= r2Vertex3X)
				&& (r1Vertex4Y >= r2Vertex2Y && r1Vertex4Y <= r2Vertex1Y))
			return true;

		// analysis of vertices of the second rect

		else if ((r2Vertex1X >= r1Vertex1X && r2Vertex1X <= r1Vertex3X)
				&& (r2Vertex1Y >= r1Vertex2Y && r2Vertex1Y <= r1Vertex1Y))
			return true;

		else if ((r2Vertex2X >= r1Vertex1X && r2Vertex2X <= r1Vertex3X)
				&& (r2Vertex2Y >= r1Vertex2Y && r2Vertex2Y <= r1Vertex1Y))
			return true;
		else if ((r2Vertex3X >= r1Vertex1X && r2Vertex3X <= r1Vertex3X)
				&& (r2Vertex3Y >= r1Vertex2Y && r2Vertex3Y <= r1Vertex1Y))
			return true;

		else if ((r2Vertex4X >= r1Vertex1X && r2Vertex4X <= r1Vertex3X)
				&& (r2Vertex4Y >= r1Vertex2Y && r2Vertex4Y <= r1Vertex1Y))
			return true;

		else

			return false;
	}

}