package com.wojdor.hearthcards.application.update;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;

public class UpdateResultReceiver extends ResultReceiver {

    public static final int RESULT_SUCCESS = 1;
    public static final int RESULT_ERROR = 0;

    private Receiver receiver;

    @SuppressLint("RestrictedApi")
    public UpdateResultReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (receiver == null) return;
        receiver.onReceiveResult(resultCode, resultData);
    }

    public interface Receiver {
        void onReceiveResult(int resultCode, Bundle resultData);
    }
}
