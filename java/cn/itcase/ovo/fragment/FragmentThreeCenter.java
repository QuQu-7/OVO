package cn.itcase.ovo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import cn.itcase.ovo.R;

public class FragmentThreeCenter extends androidx.fragment.app.Fragment{

//    private GridView gridview;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three_c, null);
//        gridview = view.findViewById(R.id.gridview3);
        return view;

    }

//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        gridview.setAdapter(new ImageAdapterThree(getActivity()));
//
//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                Toast.makeText(getActivity(), "" + (position + 1),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

}
