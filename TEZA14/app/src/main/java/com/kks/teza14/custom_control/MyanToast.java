package com.kks.teza14.custom_control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.kks.teza14.R;

public class MyanToast {


    public static void makeText(Context context, String text) {

        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the Layout
        View layout = inflater.inflate(R.layout.toast_layout, null);

        MyanTextView textView = layout.findViewById(R.id.txtToastMessage);
        // Set the Text to show in TextView
        textView.setMyanmarToastText(text);

        Toast.makeText(context, textView.getText(), Toast.LENGTH_SHORT).show();
    }
}
