package com.fanelli.crash;

import android.app.Activity;
import android.app.Fragment;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class MainActivity extends Activity {
	private SquareRenderer squareRenderer;
	private float xBefore = 0;
	private float yBefore = 0;
	private float xAfter = 0;
	private float yAfter = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		squareRenderer = new SquareRenderer(true, getApplicationContext());
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		GLSurfaceView Gview = new GLSurfaceView(this);
		Gview.setRenderer(squareRenderer);
		setContentView(Gview);
		

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action = MotionEventCompat.getActionMasked(event);
		switch (action) {
		case (MotionEvent.ACTION_DOWN):
			xBefore = event.getX();
			yBefore = event.getY();

			return true;
		case (MotionEvent.ACTION_MOVE):

			return true;
		case (MotionEvent.ACTION_UP):
			xAfter = event.getX();
			yAfter = event.getY();
			System.out.println("----" + xBefore + "----" + yBefore + "----"
					+ xAfter + "----" + yAfter);
			if (yAfter < yBefore
					&& (Math.abs(yBefore - yAfter) > Math.abs(xAfter - xBefore)))
				squareRenderer.setUp(true);
			else if (xAfter > xBefore
					&& (Math.abs(xAfter - xBefore) > Math.abs(yAfter - yBefore)))
				squareRenderer.setRight(true);
			else if (xAfter < xBefore
					&& (Math.abs(xBefore - xAfter) > Math.abs(yAfter - yBefore)))
				squareRenderer.setLeft(true);
			return true;
		case (MotionEvent.ACTION_CANCEL):

			return true;
		case (MotionEvent.ACTION_OUTSIDE):

			return true;
		default:
			return super.onTouchEvent(event);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
