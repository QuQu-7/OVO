package cn.itcase.ovo.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cn.itcase.ovo.R;

public class SetupActivity extends AppCompatActivity {

    private ListView mListView;
    private ImageView back;

    private String[] names = {"编辑资料","账户管理"};
    private int[] icons = {R.drawable.editor_icon,R.drawable.account_icon};

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);

        mListView=findViewById(R.id.setup_listview);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent =new Intent(SetupActivity.this, EditorActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(SetupActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                }
            }
        });


        MyBaseAdapter mAdapter = new MyBaseAdapter();
        mListView.setAdapter(mAdapter);

    }

    class MyBaseAdapter extends BaseAdapter{

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
            View view = View.inflate(SetupActivity.this,R.layout.setup_list_item,null);
            TextView mTextView = view.findViewById(R.id.item_tv);
            mTextView.setText(names[position]);
            ImageView imageView = view.findViewById(R.id.item_image);
            imageView.setBackgroundResource(icons[position]);
            return view;
        }
    }

}
