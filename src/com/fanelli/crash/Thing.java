package com.fanelli.crash;

public class Thing {
	private float X;
	private float Y;
	private Vector2D vel;
	private Vector2D acc;
	
	public float getX() {
		return X;
	}
	public void setX(float x) {
		X = x;
	}
	public float getY() {
		return Y;
	}
	public void setY(float y) {
		Y = y;
	}
	public Vector2D getVel() {
		return vel;
	}
	public void setVel(Vector2D vel) {
		this.vel = vel;
	}
	public Vector2D getAcc() {
		return acc;
	}
	public void setAcc(Vector2D acc) {
		this.acc = acc;
	}
	public Thing(float x, float y, Vector2D vel, Vector2D acc) {
		super();
		X = x;
		Y = y;
		this.vel = vel;
		this.acc = acc;
	}
	public Thing(float x, float y) {
		super();
		X = x;
		Y = y;
	}
	
	 

}
