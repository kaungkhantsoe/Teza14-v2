package com.kks.teza14.custom_control;

import android.content.Context;


public class MyanTextProcessor {

    public static String processText(Context context, String original_text) {

        switch (AndroidCommonSetup.getInstance().getZgOrMM3()) {
            case "zg":
//                original_text = Rabbit.uni2zg(original_text);
                break;
            default:
                original_text = Rabbit.zg2uni(original_text);
                break;

        }

        return original_text;
    }

}
