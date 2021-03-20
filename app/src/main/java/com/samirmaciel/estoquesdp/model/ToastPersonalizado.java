package com.samirmaciel.estoquesdp.model;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.samirmaciel.estoquesdp.R;

public class ToastPersonalizado {
    private static final int SUCCESS = 1;
    private static final int WARNING = 2;
    private static final int ERROR = 3;

    public static void showToast(int type, String message, Activity activity, Context context){
        ViewGroup view = activity.findViewById(R.id.container_toast);
        View v = activity.getLayoutInflater().inflate(R.layout.custom_toast, view);

        TextView text_message = v.findViewById(R.id.text_message);
        ImageView image_icon = v.findViewById(R.id.toast_icon);

        switch (type){
            case SUCCESS:
                v.setBackground(ContextCompat.getDrawable(context,R.drawable.toast_success));
                break;
            case WARNING:
                image_icon.setImageResource(R.drawable.ic_baseline_priority_high_24);
                v.setBackground(ContextCompat.getDrawable(context, R.drawable.toast_warning));

                break;
            case ERROR:
                image_icon.setImageResource(R.drawable.ic_baseline_close_24);
                v.setBackground(ContextCompat.getDrawable(context, R.drawable.toast_error));

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        Toast toast = new Toast(context);
        text_message.setText(message);
        toast.setView(v);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}