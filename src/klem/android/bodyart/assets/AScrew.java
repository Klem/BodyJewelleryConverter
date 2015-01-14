package klem.android.bodyart.assets;

import java.io.Serializable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public abstract class AScrew extends View implements Serializable {
	public static final int ALIGN_TOP = 1;
	public static final int ALIGN_CENTER = 0;
	public static final int ALIGN_BOTTOM = -1;
	protected Paint mainPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	protected Paint debugPaint = null;
	protected Point centerPoint = null;
	protected Point connectionPoint = null;
	protected boolean debug = true;
	protected int color = Color.BLUE;
	protected LayoutParams layout;
	protected float size = 30;
	protected int align = ALIGN_CENTER;

	public AScrew(Context context) {
		super(context);
		layout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		this.setLayoutParams(layout);
		mainPaint.setAlpha(50);
		this.setOnTouchListener(new OnScrewTouchListener());
	}

	public abstract void displayDebugInfo(Canvas canvas, int deltaY);

	public abstract void setCenterPoint(Point centerPoint);

	public Point getCenterPoint() {
		return centerPoint;
	}

	public abstract void setSize(float size);

	public float getSize() {
		return size;
	}

	public void setAlign(int align) {
		this.align = align;
	}

	public int getAlign() {
		return this.align;
	}

	public MetaScrew getBundle() {
		return new MetaScrew(size, align, centerPoint.x, centerPoint.y, color);
	}

	public abstract Class getType();

	public class OnScrewTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// Toast.makeText(getContext(), "Screw editing mode",
			// Toast.LENGTH_SHORT).show();

			return false;
		}

		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// final int action = event.getAction();
		// AScrew screw = (AScrew) v;
		// double r = Math.atan2(event.getX() - screw.getCenterPoint().x,
		// screw.getCenterPoint().y - event.getY());
		// int rotation = (int) Math.toDegrees(r);
		//
		// switch (action) {
		// case MotionEvent.ACTION_DOWN: {
		// break;
		// }
		//
		// case MotionEvent.ACTION_MOVE: {
		// setAngle(rotation);
		// invalidate();
		// break;
		// }
		// }
		// return true;
		// }
	}
}
