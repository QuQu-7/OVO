package cn.itcase.ovo_ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentOneCenter extends androidx.fragment.app.Fragment {

    private GridView gridview;
    final int images[]={R.drawable.cat,R.drawable.cat,R.drawable.cat,R.drawable.cat};
    private ImageAdapterOne mImageAdapterOne;
    private boolean isShowDelete = false;
    private ArrayList<HashMap<String, Object>> myList;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_c, null);
        gridview = view.findViewById(R.id.gridview1);
        myList = getMenuAdapter(images);
        mImageAdapterOne=new ImageAdapterOne(getActivity(),myList);
        gridview.setAdapter(mImageAdapterOne);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(getActivity(),VideoPlay.class);
                String path="http://42.159.72.121/video/cat.mp4";
                intent.putExtra("videopath",path);
                startActivity(intent);
            }
        });

        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (isShowDelete) {
                    isShowDelete = false;
                } else {
                    isShowDelete = true;
                    mImageAdapterOne.setIsShowDelete(isShowDelete);
                    gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            delete(position);//删除选中项
                            mImageAdapterOne = new ImageAdapterOne(getActivity(), myList);//重新绑定一次adapter
                            gridview.setAdapter(mImageAdapterOne);
                            mImageAdapterOne.notifyDataSetChanged();//刷新gridview
                        }
                    });
                }
                mImageAdapterOne.setIsShowDelete(isShowDelete);//setIsShowDelete()方法用于传递isShowDelete值
                return true;
            }
        });

    }

    private void delete(int position) {//删除选中项方法
        ArrayList<HashMap<String, Object>> newList = new ArrayList<HashMap<String, Object>>();
        if (isShowDelete) {
            myList.remove(position);
            isShowDelete = false;
        }
        newList.addAll(myList);
        myList.clear();
        myList.addAll(newList);
    }

    private ArrayList<HashMap<String, Object>> getMenuAdapter(//将数据装入List
                                                              int[] menuImageArray) {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < menuImageArray.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("image", menuImageArray[i]);
            data.add(map);
        }
        return data;
    }

}
