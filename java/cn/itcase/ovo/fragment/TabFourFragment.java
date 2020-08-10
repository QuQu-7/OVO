package cn.itcase.ovo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import cn.itcase.ovo.R;
import cn.itcase.ovo.RoundImageView;

public class TabFourFragment extends androidx.fragment.app.Fragment {
    private ListView mListView;

    private TextView name;
    private TextView message;
    private RoundImageView icon;

    private int[] icons = {R.drawable.image,R.drawable.image};
    private String[] names = {"快乐的用户001","快乐的用户002"};
    private String[] messages = {"发来一条新消息！","该用户点赞了您！"};

    private MyBaseAdapterTwo mAdapter;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message, null);

        mListView = view.findViewById(R.id.message_lv);

        mAdapter = new MyBaseAdapterTwo();
        mListView.setAdapter(mAdapter);

        return view;
    }

    class MyBaseAdapterTwo extends BaseAdapter {

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return names[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getActivity(),R.layout.message_list_item,null);
            name = view.findViewById(R.id.message_item_name);
            name.setText(names[position]);
            message = view.findViewById(R.id.message_item_message);
            message.setText(messages[position]);
            icon = view.findViewById(R.id.message_item_icon);
            icon.setBackgroundResource(icons[position]);
            return view;
        }
    }

}