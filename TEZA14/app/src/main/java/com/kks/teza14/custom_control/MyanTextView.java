package com.kks.teza14.custom_control;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;


public class MyanTextView extends AppCompatTextView {

    private Context context;

    public MyanTextView(Context context) {
        super(context);
        this.context = context;
        applyCustomFont(context);
        setText(MyanTextProcessor.processText(getContext(), getText().toString()));
    }

    public MyanTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
        setText(MyanTextProcessor.processText(getContext(), getText().toString()));
    }

    public MyanTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context);
        setText(MyanTextProcessor.processText(getContext(), getText().toString()));
    }

    public void setMyanmarText(final String text) {
        applyCustomFont(context);

        this.post(new Runnable() {
            @Override
            public void run() {

                if(text != null)
                {
                    if(!text.isEmpty())
                    {
                        String myanText = MyanTextProcessor.processText(getContext(), text);
                        setText(myanText);
                    }
                    else
                    {
                        setText("-");
                    }
                }
                else
                {
                    setText("-");
                }

            }

        });

    }

    public String getMyanmarText() {
        if (MyanmarZawgyiConverter.isZawgyiEncoded(getText().toString())) {
            return Rabbit.zg2uni(getText().toString());
        } else {
            return getText().toString();
        }
    }

    public void setMyanmarToastText(String text){
        String myanText = MyanTextProcessor.processText(getContext(), text);
        setText(myanText);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("CJ_LIGHT.ttf", context);
        setTypeface(customFont);
    }

}
