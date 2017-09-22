package test.ceshi.titlebar_gradient;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by wangchang on 2017/9/21.
 */

public class ScrollActivity extends AppCompatActivity implements NestedScrollView.OnScrollChangeListener {

    private RelativeLayout layTitle;//标题栏
    private NestedScrollView scrollView;
    private ImageView imageView;
    private TextView textView;
    private int imageHeight;
    @ColorInt
    private int colors[] = {0xFF24AAFF, Color.argb(0, 36, 170, 255)};
    private GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        layTitle = (RelativeLayout) findViewById(R.id.layTitle);
        scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        imageView = (ImageView) findViewById(R.id.image);
        textView = (TextView) findViewById(R.id.text);
        ViewTreeObserver vto = imageView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                imageHeight = imageView.getHeight() - layTitle.getHeight();
                scrollView.setOnScrollChangeListener(ScrollActivity.this);
            }
        });
        scrollView.setOnScrollChangeListener(this);
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY <= 0) {
            colors[1] = Color.argb(0, 255, 255, 255);
            textView.setTextColor(Color.WHITE);
        } else if (scrollY > 0 && scrollY <= imageHeight) {
            float scale = (float) scrollY / imageHeight;
            int alpha = (int) (scale * 255);
            colors[1] = Color.argb(alpha, 36, 170, 255);
            textView.setTextColor(Color.argb(alpha, 1, 24, 28));
        } else {
            colors[1] = Color.argb(255, 36, 170, 255);
            textView.setTextColor(Color.BLACK);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (scrollY > 0) {
                gradientDrawable.setColors(colors);
                layTitle.setBackground(gradientDrawable);
            } else {
                layTitle.setBackgroundColor(Color.TRANSPARENT);
            }

        }
        Log.e("scrollY--->", scrollY + "");
    }
}
