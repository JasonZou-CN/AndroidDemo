package jasonzou.demo.application;

import android.content.Context;
import android.util.AttributeSet;

//    compile 'com.android.support:appcompat-v7:23+'
public class EditTextCursorWatcher extends android.support.v7.widget.AppCompatEditText {


    public EditTextCursorWatcher(Context context) {
        super(context);
    }

    public EditTextCursorWatcher(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextCursorWatcher(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*@Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        Toast.makeText(getContext(), "selStart is " + selStart + "selEnd is " + selEnd, Toast.LENGTH_LONG).show();
    }*/
}
