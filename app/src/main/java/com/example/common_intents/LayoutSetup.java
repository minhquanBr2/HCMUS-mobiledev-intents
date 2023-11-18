package com.example.common_intents;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class LayoutSetup {

    public static void createButtonGridLayout(Context context, LinearLayout gridLayout, int rows, int cols, int idOffset, String[] btnName) {
        int cnt = 0;
        for (int i = 0; i < rows; i++) {
            LinearLayout row = new LinearLayout(context);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1
            ));
            for (int j = 0; j < cols; j++) {
                int id = idOffset + i * cols + j;
                Button btn = new Button(context);
                btn.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        1
                ));
                btn.setText(btnName[cnt++]);
                btn.setId(++id);
                row.addView(btn);
            }
            gridLayout.addView(row);
        }
    }
}
