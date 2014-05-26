package com.fanelli.crash;

import android.util.FloatMath;

public class Vector2D {
	public static float TO_RADIANS = (1 / 180.0f) * (float) Math.PI;
	public static float TO_DEGREES = (1 / (float) Math.PI) * 180;
	public float x, y;

	public Vector2D() {
	}

	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2D(Vector2D other) {
		this.x = other.x;
		this.y = other.y;
	}

	public Vector2D cpy() {
		return new Vector2D(x, y);
	}

	public Vector2D set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vector2D set(Vector2D other) {
		this.x = other.x;
		this.y = other.y;
		return this;
	}

	public Vector2D add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public Vector2D add(Vector2D other) {
		this.x += other.x;
		this.y += other.y;
		return this;
	}

	public Vector2D sub(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	public Vector2D sub(Vector2D other) {
		this.x -= other.x;
		this.y -= other.y;
		return this;
	}

	public Vector2D mul(float scalar) {
		this.x *= scalar;
		this.y *= scalar;
		return this;
	}

	public float len() {
		return FloatMath.sqrt(x * x + y * y);
	}

	public Vector2D nor() {
		float len = len();
		if (len != 0) {
			this.x /= len;
			this.y /= len;
		}
		return this;
	}

	public Vector2D rotate(float angle) {
		float rad = angle * TO_RADIANS;
		float cos = FloatMath.cos(rad);
		float sin = FloatMath.sin(rad);
		float newX = this.x * cos - this.y * sin;
		float newY = this.x * sin + this.y * cos;
		this.x = newX;
		this.y = newY;
		return this;
	}

	public float angle() {
		float angle = (float) Math.atan2(y, x) * TO_DEGREES;
		if (angle < 0)
			angle += 360;
		return angle;
	}

	public float dist(Vector2D other) {
		float distX = this.x - other.x;
		float distY = this.y - other.y;
		return FloatMath.sqrt(distX * distX + distY * distY);
	}

	public float dist(float x, float y) {
		float distX = this.x - x;
		float distY = this.y - y;
		return FloatMath.sqrt(distX * distX + distY * distY);
	}
}