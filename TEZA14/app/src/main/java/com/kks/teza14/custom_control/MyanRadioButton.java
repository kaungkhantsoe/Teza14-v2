package com.kks.teza14.custom_control;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatRadioButton;


public class MyanRadioButton extends AppCompatRadioButton {

    private Context context;

    public MyanRadioButton(Context context) {
        super(context);
        this.context = context;
        setMyanmarText(getText().toString());
    }

    public MyanRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMyanmarText(getText().toString());
    }

    public MyanRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMyanmarText(getText().toString());
    }

    public String getMyanmarText() {
        if (MyanmarZawgyiConverter.isZawgyiEncoded(getText().toString())) {
            return Rabbit.zg2uni(getText().toString());
        } else {
            return getText().toString();
        }
    }

    public void setMyanmarText(String text) {
        applyCustomFont(context);
        setText(MyanTextProcessor.processText(getContext(), text));
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("CJ_BOLD.ttf", context);
        setTypeface(customFont);
    }
}
