package klem.android.bodyart.assets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

public abstract class AJewellery extends View implements Serializable {
	public static float angle = 0;
	protected boolean debug = true;
	protected float size;
	protected int color;
	protected float thickness;
	protected float strokeWidth;
	protected Point startPoint = null;
	protected Point centerPoint = null;
	protected Point endPoint = null;
	protected List<AScrew> screws = new ArrayList<AScrew>();
	protected FrameLayout viewContainer = null;
	protected Paint mainPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	protected Paint debugPaint = null;
	protected LayoutParams layout;
	protected int mPosX;
	protected int mPosY;
	protected int mLastTouchX;
	protected int mLastTouchY;

	public AJewellery(Context context) {
		super(context);
		mainPaint.setAlpha(50);
		layout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		this.setLayoutParams(layout);
		this.setOnTouchListener(new OnJewelleryTouchListener());
	}

	public abstract void setAngle(float angle);

	public abstract void displayDebugInfo(Canvas canvas);

	public abstract void setCenterPoint(Point centerPoint);

	public abstract void setCenterPoint(int x, int y);

	public abstract void setSize(float diameter);

	public abstract void refreshScrews();

	public MetaJewellery getBundle() {
		return new MetaJewellery(size, thickness, centerPoint.x, centerPoint.y, color);
	}

	public void setColor(int color) {
		this.color = color;
		mainPaint.setColor(color);
	}

	public void setThickness(float thickness) {
		this.thickness = thickness;
		this.strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, thickness, getResources()
				.getDisplayMetrics());
		mainPaint.setStrokeWidth(strokeWidth);
	}

	public void setViewContainer(FrameLayout viewContainer) {
		this.viewContainer = viewContainer;
		this.viewContainer.addView(this);

	}

	public void removeFromContainer() {
		viewContainer.removeView(this);
	}

	public void addScrew(AScrew screw) {
		screws.add(screw);
		viewContainer.addView(screw);
	}

	public void removeScrew(AScrew screw) {
		screws.remove(screw);
		viewContainer.removeView(screw);
	}

	public void removeScrews() {
		for (AScrew screw : screws) {
			viewContainer.removeView(screw);
		}

		screws.clear();
	}

	public float getStrokeWidth() {
		return strokeWidth;
	}

	public void setStrokeWidth(float strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	public float getSize() {
		return size;
	}

	public int getColor() {
		return color;
	}

	public float getThickness() {
		return thickness;
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public List<AScrew> getScrews() {
		return screws;
	}

	public Point getCenterPoint() {
		return centerPoint;
	}

	public Point getEndPoint() {
		return startPoint;
	}

	public FrameLayout getViewContainer() {
		return viewContainer;
	}

	public class OnJewelleryTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			final int action = event.getAction();

			switch (action) {

			case MotionEvent.ACTION_DOWN: {
				final int x = (int) event.getX();
				final int y = (int) event.getY();

				// Remember where we started
				mLastTouchX = x;
				mLastTouchY = y;
				setCenterPoint(x, y);
				break;
			}

			case MotionEvent.ACTION_MOVE: {
				final int x = (int) event.getX();
				final int y = (int) event.getY();

				// Calculate the distance moved
				final float dx = x - mLastTouchX;
				final float dy = y - mLastTouchY;

				// Move the object
				mPosX += dx;
				mPosY += dy;
				setCenterPoint(mPosX, mPosY);
				// Remember this touch position for the next move event
				mLastTouchX = x;
				mLastTouchY = y;

				// Invalidate to request a redraw
				invalidate();
				break;
			}
			}
			return true;
		}
	}

	public class OnJewelleryClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// Toast.makeText(getContext(), "Jewellery editing mode",
			// Toast.LENGTH_SHORT).show();
		}

	}

}