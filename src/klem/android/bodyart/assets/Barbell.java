package klem.android.bodyart.assets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.util.TypedValue;

public class Barbell extends AJewellery {

	private float length;
	private int delta = 15;

	public Barbell(Context context, MetaJewellery jb) {
		super(context);
		this.size = jb.getSize(); // logical size
		this.color = jb.getColor();
		this.thickness = jb.getThickness(); // logical thickness
		this.length = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, size, getResources().getDisplayMetrics()); // onScreen
																														// size
		this.strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, thickness, getResources()
				.getDisplayMetrics());// onScreen size

		setCenterPoint(new Point(jb.getCenterX(), jb.getCenterY()));
		mPosX = jb.getCenterX();
		mPosY = jb.getCenterY();

		mainPaint.setColor(color);
		mainPaint.setStyle(Style.STROKE);
		mainPaint.setStrokeWidth(strokeWidth);

	}

	@Override
	protected void onDraw(Canvas canvas) {

		canvas.setDensity(getResources().getDisplayMetrics().DENSITY_HIGH);
		canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, mainPaint);

		this.refreshScrews();

		if (debug) {
			displayDebugInfo(canvas);
		}
	}

	@Override
	public void setColor(int color) {
		this.color = color;
		mainPaint.setColor(color);
	}

	@Override
	public void setSize(float size) {
		this.size = size;
		this.length = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, size, getResources().getDisplayMetrics());
		this.startPoint = computeStartPoint(centerPoint);
		this.endPoint = computeEndPoint(centerPoint);

	}

	@Override
	public void setAngle(float angle) {
		this.angle = angle;
		this.startPoint = computeStartPoint(centerPoint);
		this.endPoint = computeEndPoint(centerPoint);
	}

	@Override
	public void setCenterPoint(Point centerPoint) {
		this.centerPoint = centerPoint;
		this.endPoint = computeEndPoint(centerPoint);
		this.startPoint = computeStartPoint(centerPoint);
	}

	@Override
	public void setCenterPoint(int x, int y) {
		this.centerPoint.set(x, y);
		this.endPoint = computeEndPoint(centerPoint);
		this.startPoint = computeStartPoint(centerPoint);
	}

	/**
	 * Refresh the screws coordinatesnes
	 */
	@Override
	public void refreshScrews() {
		for (AScrew screw : screws) {
			if (screw.getAlign() == AScrew.ALIGN_BOTTOM) {
				screw.setCenterPoint(endPoint);
			}
			if (screw.getAlign() == AScrew.ALIGN_TOP) {
				screw.setCenterPoint(startPoint);
			}
		}
	}

	@Override
	public void displayDebugInfo(Canvas canvas) {

		debugPaint = debugPaint == null ? new Paint(Paint.ANTI_ALIAS_FLAG) : debugPaint;

		debugPaint.setColor(Color.WHITE);
		debugPaint.setStrokeWidth(1);
		canvas.drawText("Start " + startPoint.toString(), 0, 15, debugPaint);
		canvas.drawText("End " + endPoint.toString(), 0, 30, debugPaint);
		canvas.drawText("Screws " + screws.size(), 0, 45, debugPaint);

		for (AScrew screw : screws) {
			screw.displayDebugInfo(canvas, delta);
			delta = +delta;
		}
	}

	/**
	 * Compute the endPoint from the startPoint
	 * 
	 * @param centerPoint
	 * @return
	 */
	private Point computeEndPoint(Point centerPoint) {

		double cos = Math.cos(angle) * (length / 2);
		double sin = Math.sin(angle) * (length / 2);
		Point p;
		if (endPoint == null) {
			p = new Point((int) (centerPoint.x + cos), (int) (centerPoint.y + sin));
		} else {

			int x = (int) (centerPoint.x + cos);
			int y = (int) (centerPoint.y + sin);
			p = new Point(x, y);
		}
		return p;
	}

	private Point computeStartPoint(Point centerPoint) {

		double cos = Math.cos(angle) * (length / 2);
		double sin = Math.sin(angle) * (length / 2);
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

	@Override
	public Point getStartPoint() {
		return computeStartPoint(centerPoint);
	}

	@Override
	public Point getEndPoint() {
		return endPoint;
	}

	@Override
	public Point getCenterPoint() {
		return centerPoint;
	}

}
