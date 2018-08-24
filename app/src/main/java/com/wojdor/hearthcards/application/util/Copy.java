package com.wojdor.hearthcards.application.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.wojdor.hearthcards.R;

public class Copy {

    public void applyOnLongClick(View view, String text) {
        view.setOnLongClickListener(clickedView -> {
            Context context = clickedView.getContext();
            ClipboardManager cManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData cData = ClipData.newPlainText(text, text);
            cManager.setPrimaryClip(cData);
            Toast.makeText(context, context.getString(R.string.text_copied), Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}
