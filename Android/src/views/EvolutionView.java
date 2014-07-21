package views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.allgoodpeopleus.evolution.MainActivity;

import evolution.shared.Utils;

public class EvolutionView extends View {
	int viewWidth = 64;
    int viewHeight = 64;
	Button startButton;
    Rect[] rectangles;
    Paint paint = new Paint();
    
    public EvolutionView(Context context){
        this(context, null);
    }
    
    public EvolutionView(Context context, AttributeSet attrs) {
		super(context, attrs);
		rectangles = new Rect[400];
	}

    @Override
    protected void onDraw(Canvas canvas){
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                int index = (i * 20) + j;
                rectangles[index] = new Rect(viewWidth * j, viewHeight * i, viewWidth + (viewWidth * j), viewHeight + (viewHeight * i));
                paint.setColor(evolution.shared.Color.GetAndroidColor(MainActivity.evolutionModel.ParentColors.get(index)));
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(rectangles[index], paint);
            }
        }
    }
}
