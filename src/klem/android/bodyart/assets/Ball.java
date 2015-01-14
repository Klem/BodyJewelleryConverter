package klem.android.bodyart.assets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.TypedValue;

public class Ball extends AScrew {

	private float radius;

	public Ball(Context context, MetaScrew sb) {
		super(context);
		this.centerPoint = new Point(sb.getCenterX(), sb.getCenterY());
		this.color = sb.getColor();
		this.setSize(sb.getSize());
		this.align = sb.getAlign();
		mainPaint.setColor(sb.getColor());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		int deltaX = 0;
		int deltaY = 0;
		switch (align) {
		case ALIGN_TOP:
			connectionPoint = computeConnectionPoint(centerPoint);
			deltaX = computeDelta(connectionPoint.x, centerPoint.x);
			deltaY = computeDelta(connectionPoint.y, centerPoint.y);
			canvas.drawCircle(centerPoint.x - deltaX, centerPoint.y - deltaY, radius, mainPaint);
			break;
		case ALIGN_CENTER:
			canvas.drawCircle(centerPoint.x, centerPoint.y, radius, mainPaint);
			break;
		case ALIGN_BOTTOM:
			connectionPoint = computeConnectionPoint(centerPoint);
			deltaX = computeDelta(connectionPoint.x, centerPoint.x);
			deltaY = computeDelta(connectionPoint.y, centerPoint.y);
			canvas.drawCircle(centerPoint.x + deltaX, centerPoint.y + deltaY, radius, mainPaint);
			break;
		default:
			break;
		}

	}

	@Override
	public void displayDebugInfo(Canvas canvas, int deltaY) {

		debugPaint = debugPaint == null ? new Paint(Paint.ANTI_ALIAS_FLAG) : debugPaint;

		debugPaint.setColor(Color.WHITE);
		debugPaint.setStrokeWidth(1);
		canvas.drawText("Center " + centerPoint.toString(), 200, deltaY, debugPaint);

	}

	@Override
	public void setCenterPoint(Point centerPoint) {
		this.centerPoint = centerPoint;

	}

	@Override
	public void setSize(float size) {
		this.size = size;
		// this.radius = size;
		this.radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, size / 2, getResources()
				.getDisplayMetrics());
	}

	@Override
	public Class getType() {
		return this.getClass();
	}

	private Point computeConnectionPoint(Point centerPoint) {

		double cos = Math.cos(AJewellery.angle) * (radius);
		double sin = Math.sin(AJewellery.angle) * (radius);
		Point p;
		if (connectionPoint == null) {
			p = new Point((int) (centerPoint.x + cos), (int) (centerPoint.y + sin));
		} else {

			int x = (int) (centerPoint.x + cos);
			int y = (int) (centerPoint.y + sin);
			p = new Point(x, y);
		}
		return p;
	}

	private int computeDelta(int x1, int x2) {
		return (x1 - x2);
	}

}
