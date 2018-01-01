package com.lcoce.www.pdfviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static final String api="https://dcsapi.com/?k=174476559&url=";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this,lookDoc.class);
    }

    public void pdf(View view) {
        Toast.makeText(this, "pdf", Toast.LENGTH_SHORT).show();
        intent.putExtra("url", api+"https://oa.lcoce.com/file/case/20171227/775a34ff0400d35f44cc821d558e6b0f.pdf");
        startActivity(intent);
    }

    public void doc(View view) {
        Toast.makeText(this, "doc", Toast.LENGTH_SHORT).show();
        intent.putExtra("url", api+"https://oa.lcoce.com/file/case/20171227/e9ff3055d1a271db73d48f3b5caf7cd0.docx");
        startActivity(intent);
    }

    public void excel(View view) {
        Toast.makeText(this, "excel", Toast.LENGTH_SHORT).show();
        intent.putExtra("url", api+"https://oa.lcoce.com/file/case/20171227/b5f941af871853651c9e54c6a4a63221.xlsx");
        startActivity(intent);
    }

    public void ppt(View view) {
        Toast.makeText(this, "ppt", Toast.LENGTH_SHORT).show();
        intent.putExtra("url", api+"https://oa.lcoce.com/file/case/20171227/5bd5fadd508bae8f0072bae79547c954.ppt");
        startActivity(intent);
    }
}
