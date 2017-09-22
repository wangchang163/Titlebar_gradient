package test.ceshi.titlebar_gradient;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wangchang on 2017/9/21.
 */

public class RecyclerActivity extends AppCompatActivity {
    private RelativeLayout layTitle;//标题栏
    private RecyclerView recyclerView;
    private TextView textView;
    private int imageHeight,mDistance,barHeight;
    private TestAdapter adapter;
    @ColorInt
    private int colors[] = {0xFF24AAFF, Color.argb(0, 36, 170, 255)};
    private GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        layTitle = (RelativeLayout) findViewById(R.id.layTitle);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter=new TestAdapter(this));
        textView = (TextView) findViewById(R.id.text);
        ViewTreeObserver vto = layTitle.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layTitle.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                barHeight = layTitle.getHeight();
            }
        });
        adapter.setGetHeight(new TestAdapter.GetHeight() {
            @Override
            public void getImageHeight(int height) {
                imageHeight=height-barHeight;
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mDistance+=dy;
                if (mDistance <= 0) {
                    colors[1] = Color.argb(0, 255, 255, 255);
                    textView.setTextColor(Color.WHITE);
                } else if (mDistance > 0 && mDistance <= imageHeight) {
                    float scale = (float) mDistance / imageHeight;
                    int alpha = (int) (scale * 255);
                    colors[1] = Color.argb(alpha, 36, 170, 255);
                    textView.setTextColor(Color.argb(alpha, 1, 24, 28));
                } else {
                    colors[1] = Color.argb(255, 36, 170, 255);
                    textView.setTextColor(Color.BLACK);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (mDistance > 0) {
                        gradientDrawable.setColors(colors);
                        layTitle.setBackground(gradientDrawable);
                    } else {
                        layTitle.setBackgroundColor(Color.TRANSPARENT);
                    }

                }
                Log.e("scrollY--->", dy + "");
            }
        });
        getData();
    }

    private void getData(){
        ArrayList<ItemModel> data=new ArrayList<>();
        data.add(new ItemModel(ItemModel.Type.IMAGE,null));
        data.add(new ItemModel(ItemModel.Type.TEXT,null));
        data.add(new ItemModel(ItemModel.Type.TEXT,null));
        data.add(new ItemModel(ItemModel.Type.TEXT,null));
        data.add(new ItemModel(ItemModel.Type.TEXT,null));
        data.add(new ItemModel(ItemModel.Type.TEXT,null));
        data.add(new ItemModel(ItemModel.Type.TEXT,null));
        data.add(new ItemModel(ItemModel.Type.TEXT,null));
        data.add(new ItemModel(ItemModel.Type.TEXT,null));
        data.add(new ItemModel(ItemModel.Type.TEXT,null));
        data.add(new ItemModel(ItemModel.Type.TEXT,null));
        data.add(new ItemModel(ItemModel.Type.TEXT,null));
        adapter.setData(data);
    }

}
