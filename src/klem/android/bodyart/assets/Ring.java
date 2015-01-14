package klem.android.bodyart.assets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.util.TypedValue;

public class Ring extends AJewellery {
	private float radius;
	private final float strokeWidth;
	private Paint debugPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

	public Ring(Context context, MetaJewellery jb) {
		super(context);
		this.size = jb.getSize(); // logical size
		this.color = jb.getColor();
		this.thickness = jb.getThickness(); // logical thickness
		this.strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, thickness, getResources()
				.getDisplayMetrics());

		this.radius = (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, size, getResources().getDisplayMetrics()) + strokeWidth) / 2;

		setCenterPoint(new Point(jb.getCenterX(), jb.getCenterY()));
		mPosX = jb.getCenterX();
		mPosY = jb.getCenterY();

		mainPaint.setColor(color);
		mainPaint.setStyle(Style.STROKE);
		mainPaint.setStrokeWidth(strokeWidth);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawCircle(centerPoint.x, centerPoint.y, radius, mainPaint);

		this.refreshScrews();
		if (debug) {
			displayDebugInfo(canvas);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see klem.android.bodyart.assets.AJewellery#setSize(float)
	 */
	@Override
	public void setSize(float size) {
		this.size = size;
		this.radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, size + thickness, getResources()
				.getDisplayMetrics()) / 2;
		this.startPoint = computeStartPoint(centerPoint);
	}

	@Override
	public void setThickness(float thickness) {
		super.setThickness(thickness);
		this.radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, size + thickness, getResources()
				.getDisplayMetrics()) / 2;
		this.startPoint = computeStartPoint(centerPoint);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see klem.android.bodyart.assets.AJewellery#setThickness(float)
	 */

	@Override
	public void setCenterPoint(Point centerPoint) {
		this.centerPoint = centerPoint;
		this.startPoint = computeStartPoint(centerPoint);

	}

	@Override
	public void setCenterPoint(int x, int y) {
		this.centerPoint.set(x, y);
		this.startPoint = computeStartPoint(centerPoint);
	}

	@Override
	public void setAngle(float angle) {
		this.angle = angle;
		this.startPoint = computeStartPoint(centerPoint);
	}

	@Override
	public void refreshScrews() {
		for (AScrew screw : screws) {
			screw.setCenterPoint(startPoint);
		}
	}

	@Override
	public void displayDebugInfo(Canvas canvas) {
		debugPaint = debugPaint == null ? new Paint(Paint.ANTI_ALIAS_FLAG) : debugPaint;
		debugPaint.setColor(Color.WHITE);
		debugPaint.setStrokeWidth(1);
		canvas.drawText("Radius " + radius, 0, 15, debugPaint);
		canvas.drawText("Center " + centerPoint.toString(), 0, 30, debugPaint);
		canvas.drawText("Screws " + screws.size(), 0, 45, debugPaint);

	}

	@Override
	public Point getEndPoint() {
		return startPoint;
	}

	private Point computeStartPoint(Point centerPoint) {

		double cos = Math.cos(angle) * (radius);
		double sin = Math.sin(angle) * (radius);
		Point p;
		if (startPoint == null) {
			p = new Point((int) (centerPoint.x - cos), (int) (centerPoint.y - sin));
		} else {

			int x = (int) (centerPoint.x - cos);
			int y = (int) (centerPoint.y - sin);
			p = new Point(x, y);
		}
		return p;
	}

}
