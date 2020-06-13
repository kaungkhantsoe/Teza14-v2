package com.kks.teza14.custom_control;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckBox;


public class MyanCheckBox extends AppCompatCheckBox {
    public MyanCheckBox(Context context) {
        super(context);
        setText(MyanTextProcessor.processText(getContext(), getText().toString()));
    }

    public MyanCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        setText(MyanTextProcessor.processText(getContext(), getText().toString()));
    }

    public MyanCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setText(MyanTextProcessor.processText(getContext(), getText().toString()));
    }

    public String getMyanmarText() {
        if (MyanmarZawgyiConverter.isZawgyiEncoded(getText().toString())) {
            return Rabbit.zg2uni(getText().toString());
        } else {
            return getText().toString();
        }
    }

    public void setMyanmarText(String text) {
        setText(MyanTextProcessor.processText(getContext(), text));
    }
}
