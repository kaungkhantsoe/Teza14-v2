package com.kks.teza14.custom_control;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;


public class MyanButton extends AppCompatButton {

    private Context context;

    public MyanButton(Context context) {
        super(context);

        this.context = context;

        setMyanmarText(getText().toString());
        
    }

    public MyanButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        setMyanmarText(getText().toString());
    }

    public MyanButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setMyanmarText(getText().toString());
    }

    public void setMyanmarText(String text) {
        applyCustomFont(context);
        setText(MyanTextProcessor.processText(getContext(), text));
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("CJ_LIGHT.ttf", context);
        setTypeface(customFont);
    }

}
