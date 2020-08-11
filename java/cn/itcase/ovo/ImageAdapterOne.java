package cn.itcase.ovo_ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;

public class ImageAdapterOne extends BaseAdapter {

    private ArrayList<HashMap<String, Object>> myList;
    private Context mContext;
    private ImageView img;
    private View deleteView;
    private boolean isShowDelete;

    public ImageAdapterOne(Context mContext,ArrayList<HashMap<String, Object>> myList) {
        this.mContext = mContext;
        this.myList = myList;
    }
    public void setIsShowDelete(boolean isShowDelete){
        this.isShowDelete=isShowDelete;
        notifyDataSetChanged();
    }

    public int getCount() {
        return myList.size();
    }

    public Object getItem(int position) {
        return myList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item, null);
        img = convertView.findViewById(R.id.grid_item_image);
        deleteView=convertView.findViewById(R.id.grid_item_delete);
        deleteView.setVisibility(isShowDelete?View.VISIBLE:View.GONE);//设置删除按钮是否显示
        img.setBackgroundResource(myList.get(position).get("image").hashCode());
        return convertView;
    }


}
