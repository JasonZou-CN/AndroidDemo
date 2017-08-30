package jasonzou.demo.mview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import jasonzou.demo.mview.view.ArcCamera;
import jasonzou.demo.view.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_volume_control_bar);

        ArcCamera arcCamera = new ArcCamera(this);
        arcCamera.setArcColor(Color.parseColor("#1e9fa8ad"));
        arcCamera.setStartAngle(30);
        arcCamera.setSweepAngel(120);
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(arcCamera);
    }
}
