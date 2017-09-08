package jasonzou.demo.application.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jasonzou.demo.application.R;

import static android.content.ContentValues.TAG;

/**
 * EditText "@XXX" 高亮,连带删除 ： 没有完成，长按选择的处理（屏蔽了）
 */
public class RichText extends Activity {

    @BindView(R.id.mEdittext)
    EditText mEdittext;
    @BindView(R.id.choose)
    Button choose;
    MTextWatcher textWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rich_text);
        ButterKnife.bind(this);

        initEeitText();

    }

    /**
     * 实际测试 - 禁用了长按后的选择菜单
     */
    private void initEeitText() {
        // 可以点击
        textWatcher = new MTextWatcher();
        mEdittext.setMovementMethod(LinkMovementMethod.getInstance());
        //        mEdittext.setHighlightColor(Color.CYAN);//ClickableSpan 点击后的背景色
        //        mEdittext.setLongClickable(false);//取消长按后的菜单
        mEdittext.addTextChangedListener(textWatcher);
    }

    class MTextWatcher implements TextWatcher {
        CharSequence string;
        boolean haveChosen = true;//是否已经选择了某个人，控制输入@后的操作
        int mStart;//从0开始
        int mCount;
        //            int end;
        boolean isAdd;
        int foregroundSpanStart;//需要删除的span的开始位置
        int foregroundSpanEnd;//需要删除的span的开始位置+1
        int afterDelIndex;//上一次删除后，其前一个字符的位置

        public void setHaveChosen(boolean haveChosen) {
            this.haveChosen = haveChosen;
        }

        public boolean getHaveChosen() {
            return haveChosen;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {//处理删除
            Log.i(TAG, "beforeTextChanged: " + s + " " + start + " " + count + " " + after);
            //start初始光标位置   after增加    count减少
            if (after > 0)
                return;
            isAdd = count > 0 ? false : true;
            if (!isAdd) {//删除操作
                this.mStart = start;
                this.mCount = count;
                string = s;

                Editable beforeSpan = mEdittext.getText();
                ForegroundColorSpan[] spans = mEdittext.getText().getSpans(0, beforeSpan.length(), ForegroundColorSpan.class);
                afterDelIndex = -1;//重置为-1
                for (ForegroundColorSpan mSpan : spans) {
                    if (mSpan instanceof ForegroundColorSpan) {
                        foregroundSpanStart = beforeSpan.getSpanStart(mSpan);
                        foregroundSpanEnd = beforeSpan.getSpanEnd(mSpan);//end - 1才是下标
                        //                            int flag = beforeSpan.getSpanFlags(mSpan);
                        if (mStart <= foregroundSpanEnd - 1 && mStart >= foregroundSpanStart) {
                            afterDelIndex = mStart - 1;
                            break;
                        }
                    }
                }
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
                this.mStart = start;
                this.mCount = count;
                string = s;
            }
        }


        @Override
        public void afterTextChanged(Editable mSpanStr) {//文本效果
            //                前景色背景色相对哈哈大小删除线下划线上标小上标了斜体显示图片点击超链接
            Log.i(TAG, "afterTextChanged: " + mSpanStr);
            if (isAdd && haveChosen && mStart > 0 && string.charAt(mStart - 1) == '@') {
                //                    mSpanStr.append(" ");
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#FF0000"));
                BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(Color.CYAN);
                MClickableSpan mClickableSpan = new MClickableSpan();

                mSpanStr.setSpan(foregroundColorSpan, mStart - 1, mStart + mCount, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                //                mSpanStr.setSpan(backgroundColorSpan, mStart - 1, mStart + mCount, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//看不见光标位置
                mSpanStr.setSpan(mClickableSpan, mStart - 1, mStart + mCount, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


            } else if (!isAdd && afterDelIndex != -1) {
                mEdittext.removeTextChangedListener(textWatcher);//避免递归调用
                mSpanStr.delete(foregroundSpanStart, afterDelIndex + 1);//span相关的方法end参数为 "下标+1"
                mEdittext.addTextChangedListener(textWatcher);
            }
        }
    }

    @OnClick(R.id.choose)
    public void onViewClicked() {
        if (textWatcher.getHaveChosen()) {
            textWatcher.setHaveChosen(false);
            choose.setText("未选择");
        } else {
            choose.setText("已选择");
            textWatcher.setHaveChosen(true);
        }
    }

    /**
     * 修改点击后的光标位置    (“前提：不被悬选中删除其中之一”)
     */
    class MClickableSpan extends ClickableSpan {
        int start;//从0开始
        int end;//以length()结束

        @Override
        public void onClick(final View widget) {
          /*  ((EditText) widget).getSelectionStart();//这个位置拿不到实际值，一直是0,在view*/
            start = mEdittext.getText().getSpanStart(this);
            end = mEdittext.getText().getSpanEnd(this);

            widget.post(new Runnable() {//获取光标的位置，修改到前面或者，后面
                @Override
                public void run() {
                    Log.i(TAG, "run: before" + ((EditText) widget).getSelectionStart());
                    int i = ((EditText) widget).getSelectionStart();
                    if (i - start > end - i)
                        ((EditText) widget).setSelection(end);
                    else
                        ((EditText) widget).setSelection(start);
                }
            });

        }

        @Override
        public void updateDrawState(TextPaint ds) {
            // 文字不变色
            ds.setUnderlineText(false);
        }
    }
}
