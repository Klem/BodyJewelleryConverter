package klem.android.bodyart.assets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class CurvedBarbell extends Barbell {
	private final float sweep = 30f;
	private RectF oval = null;
	private final int curvature = 50;

	public CurvedBarbell(Context context, MetaJewellery jb) {
		super(context, jb);

		oval = new RectF(startPoint.x, startPoint.y - 50, endPoint.x, endPoint.y);
	}

	@Override
	public void onDraw(Canvas canvas) {

		for (AScrew screw : screws) {
			if (screw.getAlign() == AScrew.ALIGN_TOP) {
				oval.left = startPoint.x /*- screw.getSize()*/;
			}
			if (screw.getAlign() == AScrew.ALIGN_BOTTOM) {
				oval.right = endPoint.x /* + screw.getSize() */;
			}

		}

		oval.top = startPoint.y - curvature;
		oval.bottom = endPoint.y + curvature;
		canvas.drawArc(oval, 180, 180, false, mainPaint);
		// canvas.drawOval(oval, mainPaint);

		super.refreshScrews();

		if (debug) {
			displayDebugInfo(canvas);
		}

	}

	@Override
	public void displayDebugInfo(Canvas canvas) {

		debugPaint = debugPaint == null ? new Paint(Paint.ANTI_ALIAS_FLAG) : debugPaint;

		debugPaint.setColor(Color.WHITE);
		debugPaint.setStrokeWidth(1);
		canvas.drawText("left " + oval.left, 0, 15, debugPaint);
		canvas.drawText("top " + oval.top, 0, 30, debugPaint);
		canvas.drawText("right " + oval.right, 0, 45, debugPaint);
		canvas.drawText("bottom " + oval.bottom, 200, 15, debugPaint);
		canvas.drawText("sweepAngle " + sweep, 200, 30, debugPaint);

	}

}
