package jasonzou.demo.application.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
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
    private TextWatcher textWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rich_text);
        ButterKnife.bind(this);

        initView();
        mInitView();
        initEeitText();

    }

    /**
     * 实际测试
     */
    private void initEeitText() {
        // 可以点击
        //        mEdittext.setMovementMethod(LinkMovementMethod.getInstance());
        textWatcher = new TextWatcher() {
            public CharSequence string;
            int start;//从0开始
            int count;
            //            int end;
            boolean isAdd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {//处理删除
                Log.i(TAG, "beforeTextChanged: " + s + " " + start + " " + count + " " + after);
                //start初始光标位置   after增加    count减少
                if (after > 0)
                    return;
                isAdd = count > 0 ? false : true;
                if (!isAdd) {//删除操作
                    this.start = start;
                    this.count = count;
                    string = s;
                }


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {//处理增加
                Log.i(TAG, "onTextChanged: " + s + " " + start + " " + before + " " + count);
                //start初始光标位置   count增加    before减少
                if (before > 0)
                    return;
                isAdd = count > 0 ? true : false;
                if (isAdd) {//添加操作
                    string = s.subSequence(start, start + count);
                    this.start = start;
                    this.count = count;
                    string = s;
                }
            }


            @Override
            public void afterTextChanged(Editable mSpanStr) {//文本效果
                //                前景色背景色相对哈哈大小删除线下划线上标小上标了斜体显示图片点击超链接
                Log.i(TAG, "afterTextChanged: " + mSpanStr);
                if (isAdd && start > 0 && string.charAt(start - 1) == '@') {
                    //                    mSpanStr.append(" ");
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#FF0000"));
                    //                    BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(Color.parseColor("#6DCBFA"));
                    //                    MClickableSpan mClickableSpan = new MClickableSpan(start - 1, start + count);

                    mSpanStr.setSpan(foregroundColorSpan, start - 1, start + count, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    //                    s.setSpan(backgroundColorSpan, start - 1, start + count, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    //                    mSpanStr.setSpan(mClickableSpan, start - 1, start + count, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


                } else if (!isAdd) {
                    ForegroundColorSpan[] spans = mSpanStr.getSpans(0, mSpanStr.length(), ForegroundColorSpan.class);
                    for (ForegroundColorSpan mSpan : spans) {
                        if (mSpan instanceof ForegroundColorSpan) {
                            int sta = mSpanStr.getSpanStart(mSpan);
                            int end = mSpanStr.getSpanEnd(mSpan);//end - 1才是下标
                            int flag = mSpanStr.getSpanFlags(mSpan);
                            Log.i("SpannableString Spans", "Found ForegroundColorSpan at:\n" + "Start: " + sta + "\n End: " + end + "\n Flag(s): " + flag);

                            int left = start - count + 1;//删除范围 - 左侧下标
                            Log.i(TAG, "afterTextChanged: sta="+sta+" end="+end+" left="+left);
                            if (left <= end-1-1 && left >= sta) {
                                //                                    mSpanStr.removeSpan(mSpan);
                                mEdittext.removeTextChangedListener(textWatcher);
                                mSpanStr.delete(sta, end-1);
                                mEdittext.addTextChangedListener(textWatcher);
                                break;
                            }
                        }
                    }
                }


                //                mEdittext.setText(s);
            }
        };
        mEdittext.addTextChangedListener(textWatcher);
    }

    private void mInitView() {
        SpannableString spannableString = new SpannableString("前景色背景色相对大小删除线下划线" + "上标小上标下标粗体斜体显示图片点击超链接");
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
            int end = 4;

            @Override
            public void onClick(final View widget) {
                Toast.makeText(RichText.this, "点击...", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onClick: " + "点击...");
                etRichText.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "run: before" + ((EditText) widget).getSelectionStart());
                        ;
                        ((EditText) widget).setSelection(etRichText.getText().length());
                        Log.i(TAG, "run: after" + ((EditText) widget).getSelectionStart());
                        ;
                    }
                });
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
                Toast.makeText(RichText.this, "text change", Toast.LENGTH_SHORT).show();
                etRichText.setSelection(etRichText.getText().length());
            }
        });
    }

    void initView() {
        SpannableString spannableString = new SpannableString("前景色背景色相对大小删除线下划线" + "上标小上标下标粗体斜体显示图片点击超链接");
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

    /**
     * 修改点击后的光标位置
     */
    class MClickableSpan extends ClickableSpan {
        int start;//从1开始0
        int end;//以length()结束

        public MClickableSpan(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void onClick(final View widget) {
            Toast.makeText(RichText.this, "click", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "run: before" + ((EditText) widget).getSelectionStart());
            widget.post(new Runnable() {
                @Override
                public void run() {
                    ((EditText) widget).setSelection(end);//越界
                }
            });
            Log.i(TAG, "run: after" + ((EditText) widget).getSelectionStart());
        }
    }
}
