package bupt.liao.fred.flipviewlibrary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by Fred.Liao on 2017/11/20.
 * Email:fredliaobupt@qq.com
 * Description: A view that has a flip animation
 */

public class FlipView extends View {

    //rotate angel in Y direction
    private float degreeY;
    //rotate angel in Y direction, the fix part of the picture
    private float fixDegreeY;
    //rotate angel in Z direction
    private float degreeZ;

    private Paint paint;
    private Bitmap bitmap;
    private Camera camera;

    final AnimatorSet animatorSet = new AnimatorSet();

    private Handler handler = new Handler();

    public FlipView(Context context) {
        this(context, null);
    }

    public FlipView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlipView);
        BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(R.styleable.FlipView_fv_background);
        a.recycle();

        if (drawable != null) {
            bitmap = drawable.getBitmap();
        } else {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flip_img);
        }
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        camera = new Camera();

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float newZ = -displayMetrics.density * 6;
        camera.setLocation(0, 0, newZ);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;

        //draw the filpping half
        //First rotate, then clip and use camera to operate 3D animation.
        //Then save the 3D animation, and rotate back in the end
        canvas.save();
        camera.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(-degreeZ);
        camera.rotateY(degreeY);
        camera.applyToCanvas(canvas);
        //Be careful, the canvas has been moved
        canvas.clipRect(0, -centerY, centerX, centerY);
        canvas.rotate(degreeZ);
        canvas.translate(-centerX, -centerY);
        camera.restore();
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        //Draw the fixed half
        canvas.save();
        camera.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(-degreeZ);
        //Be careful, the canvas has been moved
        canvas.clipRect(-centerX, -centerY, 0, centerY);
        camera.rotateY(fixDegreeY);
        camera.applyToCanvas(canvas);
        canvas.rotate(degreeZ);
        canvas.translate(-centerX, -centerY);
        camera.restore();
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();
    }

    /**
     * reset all parameters
     */
    public void reset() {
        degreeY = 0;
        fixDegreeY = 0;
        degreeZ = 0;
    }

    @Keep
    public void setFixDegreeY(float fixDegreeY) {
        this.fixDegreeY = fixDegreeY;
        invalidate();
    }

    @Keep
    public void setDegreeY(float degreeY) {
        this.degreeY = degreeY;
        invalidate();
    }

    @Keep
    public void setDegreeZ(float degreeZ) {
        this.degreeZ = degreeZ;
        invalidate();
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        invalidate();
    }

    public void startFlip(){
        if(animatorSet.isRunning()){
            animatorSet.end();
        }
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(this, "degreeY", 0, -45);
        animator1.setDuration(1000);
        animator1.setStartDelay(500);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(this, "degreeZ", 0, 270);
        animator2.setDuration(800);
        animator2.setStartDelay(500);

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(this, "fixDegreeY", 0, 30);
        animator3.setDuration(1500);
        animator3.setStartDelay(500);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                reset();
            }
        });
        animatorSet.playSequentially(animator1, animator2, animator3);
        animatorSet.start();
    }

    public void stopFlip(){
        if(animatorSet.isRunning()){
            animatorSet.end();
            reset();
        }
    }

}

