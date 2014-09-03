package views;

import com.allgoodpeopleus.evolution.GameOfLifeModel;
import com.allgoodpeopleus.evolution.MainActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class GameOfLifeView extends View {
	Rect rect = new Rect();
	Paint paint = new Paint();
	GameOfLifeModel model = MainActivity.gameOfLifeModel;
	
	static final int ALIVE_COLOR = android.graphics.Color.BLACK;
	static final int DEAD_COLOR = android.graphics.Color.WHITE;

	public GameOfLifeView(Context context){
        this(context, null);
    }
    
    public GameOfLifeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

    @Override
    protected void onDraw(Canvas canvas) {
    	super.onDraw(canvas);

    	int rectWidth = getWidth() / model.getWidth();
    	int rectHeight = getHeight() / model.getHeight();
        paint.setStyle(Paint.Style.FILL);

    	for (int x = 0; x < model.getWidth(); x++) {
    		for (int y = 0; y < model.getHeight(); y++) {
    			int rectX = x * rectWidth;
    			int rectY = y * rectHeight;
    			rect.set(rectX, rectY, rectX + rectWidth, rectY + rectHeight);
                paint.setColor(model.isAlive(x, y) ? ALIVE_COLOR : DEAD_COLOR);
                canvas.drawRect(rect, paint);
    		}
    	}
    }
}
