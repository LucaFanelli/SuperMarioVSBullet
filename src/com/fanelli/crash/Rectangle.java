package com.fanelli.crash;
// this is the shape to recognize collision events
public class Rectangle {
public final Vector2D lowerLeft;
public float width, height;
public Rectangle(float x, float y, float width, float height) {
this.lowerLeft = new Vector2D(x,y);
this.width = width;
this.height = height;
}
}