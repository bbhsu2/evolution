package views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

public class BoidsView extends View {
	Paint paint = new Paint();

	public BoidsView(Context context) {
		this(context, null);
	}

	public BoidsView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		paint.setStrokeWidth(10);
		paint.setColor(android.graphics.Color.RED);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setAntiAlias(true);

		Point a = new Point(0, 0);
		Point b = new Point(0, 100);
		Point c = new Point(87, 50);

		for (int i = 0; i < 10; i++) {
			// Path path = new Path();
			// path.setFillType(FillType.EVEN_ODD);
			// path.lineTo(b.x * i, b.y * i);
			// path.lineTo(c.x * i, c.y * i);
			// path.lineTo(a.x * i, a.y * i);
			// path.close();
			// canvas.drawPath(path, paint);
			canvas.drawLine(0 + 100 * i, 0 + 100 * i, 100 + 100 * i, 0 + 100 * i, paint);
			canvas.drawLine(100 + 100 * i, 0 + 100 * i, 50 + 100 * i, 100 + 100 * i, paint);
			canvas.drawLine(50 + 100 * i, 100 + 100 * i, 0 + 100 * i, 0 + 100 * i, paint);
		}
	}

	public class BoidShape extends View {
		ShapeDrawable mDrawable;
		int x = 10;
		int y = 10;
		int width = 300;
		int height = 50;

		public BoidShape(Context context) {
			this(context, null);
		}

		public BoidShape(Context context, AttributeSet attrs) {
			super(context, attrs);

			mDrawable = new ShapeDrawable(new OvalShape());
			mDrawable.getPaint().setColor(0xff74AC23);
			mDrawable.setBounds(x, y, x + width, y + height);
		}

		protected void onDraw(Canvas canvas) {
			mDrawable.draw(canvas);
		}
	}
}
