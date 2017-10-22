package jasonzou.demo.mview.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MFragment extends Fragment {
    String title;
    public MFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        title = getArguments().getString("title");
        TextView textView = new TextView(getActivity());
        textView.setGravity(Gravity.CENTER);
//        textView.setText(R.string.hello_blank_fragment);
        textView.setText(title);
        return textView;
    }

}
