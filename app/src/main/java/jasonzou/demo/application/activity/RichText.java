package jasonzou.demo.application.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.*;
import android.text.method.LinkMovementMethod;
import android.text.style.*;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import jasonzou.demo.application.R;

import static android.content.ContentValues.TAG;

public class RichText extends Activity {

    @BindView(R.id.richText)
    TextView richText;
    @BindView(R.id.etRichText)
    EditText etRichText;
    @BindView(R.id.mEdittext)
    EditText mEdittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rich_text);
        ButterKnife.bind(this);

        initView();
        mInitView();
        initEeitText();

    }

    private void initEeitText() {
        mEdittext.addTextChangedListener(new TextWatcher() {
            public CharSequence string;
            int start;
            int end;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //                前景色背景色相对哈哈大小删除线下划线上标小上标斜体显示图片点击超链接 23 0 1
                Log.i(TAG, "beforeTextChanged: " + s + " " + start + " " + count + " " + after);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {//count > before : 输入字符  ，反之 ：删除字符
                // 前景色背景色相对哈哈大小删除线下划线上标小上标了斜体显示图片点击超链接 23 0 1
                Log.i(TAG, "onTextChanged: " + s + " " + start + " " + before + " " + count);
                if (count > before) {
                    string = s.subSequence(start, start + count);
                    this.start = start;
                    this.end = start + count;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //                前景色背景色相对哈哈大小删除线下划线上标小上标了斜体显示图片点击超链接
                Log.i(TAG, "afterTextChanged: " + s);
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#FF0000"));
                if (string.toString().contains("j"))
                    s.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            }
        });
    }

    private void mInitView() {
        SpannableString spannableString = new SpannableString("前景色背景色相对大小删除线下划线" +
                "上标小上标下标粗体斜体显示图片点击超链接");
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(Color.parseColor("#AC00FF30"));
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(2f);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        UnderlineSpan underlineSpan = new UnderlineSpan();
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        RelativeSizeSpan relativeSizeSpan2 = new RelativeSizeSpan(0.5f);
        SubscriptSpan subscriptSpan = new SubscriptSpan();
        StyleSpan styleSpan_B = new StyleSpan(Typeface.BOLD);
        StyleSpan styleSpan_I = new StyleSpan(Typeface.ITALIC);

        ImageSpan imageSpan = new ImageSpan(this, R.mipmap.ic_launcher);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(RichText.this, "点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                // 文字不变色
                ds.setUnderlineText(false);
            }
        };
        URLSpan urlSpan = new URLSpan("http://www.sdwfqin.com");

        spannableString.setSpan(foregroundColorSpan, 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(backgroundColorSpan, 3, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(relativeSizeSpan, 6, 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(strikethroughSpan, 10, 13, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(underlineSpan, 13, 16, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(superscriptSpan, 16, 21, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(relativeSizeSpan2, 18, 21, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(subscriptSpan, 21, 23, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan_B, 23, 25, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan_I, 25, 27, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(imageSpan, 29, 31, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickableSpan, 31, 33, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(urlSpan, 33, 36, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        // 可以点击
        etRichText.setMovementMethod(LinkMovementMethod.getInstance());
        // 点击背景色
        // spanString.setHighlightColor(Color.parseColor("#36969696"));

        etRichText.setText(spannableString);

        etRichText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i(TAG, "beforeTextChanged: " + s + " " + start + " " + count + " " + after);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {//count > before : 输入字符  ，反之 ：删除字符
                Log.i(TAG, "onTextChanged: " + s + " " + start + " " + before + " " + count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "afterTextChanged: " + s);

            }
        });
    }

    void initView() {
        SpannableString spannableString = new SpannableString("前景色背景色相对大小删除线下划线" +
                "上标小上标下标粗体斜体显示图片点击超链接");
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(Color.parseColor("#AC00FF30"));
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(2f);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        UnderlineSpan underlineSpan = new UnderlineSpan();
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        RelativeSizeSpan relativeSizeSpan2 = new RelativeSizeSpan(0.5f);
        SubscriptSpan subscriptSpan = new SubscriptSpan();
        StyleSpan styleSpan_B = new StyleSpan(Typeface.BOLD);
        StyleSpan styleSpan_I = new StyleSpan(Typeface.ITALIC);

        ImageSpan imageSpan = new ImageSpan(this, R.mipmap.ic_launcher);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(RichText.this, "点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                // 文字不变色
                ds.setUnderlineText(false);
            }
        };
        URLSpan urlSpan = new URLSpan("http://www.sdwfqin.com");

        spannableString.setSpan(foregroundColorSpan, 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(backgroundColorSpan, 3, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(relativeSizeSpan, 6, 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(strikethroughSpan, 10, 13, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(underlineSpan, 13, 16, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(superscriptSpan, 16, 21, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(relativeSizeSpan2, 18, 21, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(subscriptSpan, 21, 23, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan_B, 23, 25, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan_I, 25, 27, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(imageSpan, 29, 31, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickableSpan, 31, 33, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(urlSpan, 33, 36, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        // 可以点击
        richText.setMovementMethod(LinkMovementMethod.getInstance());
        // 点击背景色
        // spanString.setHighlightColor(Color.parseColor("#36969696"));

        richText.setText(spannableString);
    }
}
