package cn.itcase.ovo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.GridView;



public class ImageAdapterTwo extends BaseAdapter {
    private Context mContext;

    public ImageAdapterTwo(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) { // if it's not recycled, initialize some
            // attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(360, 480));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    private Integer[] mThumbIds = { R.drawable.cat,R.drawable.cat,
            R.drawable.image,R.drawable.image,
            R.drawable.image,R.drawable.image,
            R.drawable.image,R.drawable.image,
            R.drawable.image,R.drawable.image,
            R.drawable.image,R.drawable.image,
            R.drawable.image,R.drawable.image,
            R.drawable.image,R.drawable.image,
            R.drawable.image,R.drawable.image,
            R.drawable.image,R.drawable.image,
            R.drawable.image,R.drawable.image,
            R.drawable.image,R.drawable.image,
            R.drawable.image,R.drawable.image,
            R.drawable.image,R.drawable.image,
            R.drawable.image,R.drawable.image,
            R.drawable.image,R.drawable.image,
            R.drawable.image,R.drawable.image,
            R.drawable.image,R.drawable.image,
            R.drawable.image,R.drawable.image
    };


}