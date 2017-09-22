package test.ceshi.titlebar_gradient;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wangchang on 2017/9/21.
 */

class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    private ArrayList<ItemModel> data = new ArrayList<>();

    public void setData(ArrayList<ItemModel> list) {
        if (list != null && list.size() > 0) {
            data.clear();
            data.addAll(list);
            notifyDataSetChanged();
        }
    }

    private Context context;

    public TestAdapter(Context context) {
        this.context = context;
    }

    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ItemModel.Type.IMAGE:
                return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
            case ItemModel.Type.TEXT:
                return new TextViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(TestAdapter.ViewHolder holder, int position) {
        holder.setData(data.get(position), position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        void setData(Object o, int position) {
        }
    }

    public interface GetHeight {
        void getImageHeight(int height);
    }

    private GetHeight getHeight;

    public void setGetHeight(GetHeight getHeight) {
        this.getHeight = getHeight;
    }

    private class ImageViewHolder extends ViewHolder {
        private ImageView imageView;

        public ImageViewHolder(View view) {
            super(view);
            imageView = itemView.findViewById(R.id.image);
            ViewTreeObserver vto = imageView.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    int imageHeight = imageView.getHeight();
                    getHeight.getImageHeight(imageHeight);
                }
            });
        }

        @Override
        void setData(Object o, int position) {
            super.setData(o, position);
        }
    }

    private class TextViewHolder extends ViewHolder {
        private TextView textView;

        public TextViewHolder(View view) {
            super(view);
            textView = itemView.findViewById(R.id.tv_text);
        }

        @Override
        void setData(Object o, int position) {
            super.setData(o, position);
            textView.setText("" + position);
            if (position % 2 == 0) {
                textView.setBackgroundColor(Color.YELLOW);
            } else {
                textView.setBackgroundColor(Color.CYAN);
            }
        }
    }
}
