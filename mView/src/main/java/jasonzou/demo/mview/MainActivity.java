package jasonzou.demo.mview;

import android.app.Activity;
import android.os.Bundle;
import jasonzou.demo.view.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_volume_control_bar);
    }
}
