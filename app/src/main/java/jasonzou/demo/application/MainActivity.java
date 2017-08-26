package jasonzou.demo.application;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.shadow)
    View shadow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tv.setShadowLayer(20, 10, 5, Color.parseColor("#FF0000"));
    }


    @OnClick(R.id.shadow)
    public void onViewClicked() {
    }
}
