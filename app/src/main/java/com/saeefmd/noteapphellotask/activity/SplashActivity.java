package com.saeefmd.noteapphellotask.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.saeefmd.noteapphellotask.R;

public class SplashActivity extends AppCompatActivity {

    private TextView easyNoteTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        easyNoteTv = findViewById(R.id.tv_easy_note);

        String text = "Easy Note";
        SpannableString spannableString = new SpannableString(text);

        ForegroundColorSpan firstSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorAccent));
        ForegroundColorSpan secondSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorAccent));

        spannableString.setSpan(firstSpan, 0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(secondSpan, 5,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        easyNoteTv.setText(spannableString);

        crossFade();

        Button startBt = findViewById(R.id.bt_get_started);

        startBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void crossFade() {

        easyNoteTv.setAlpha(0f);
        easyNoteTv.setVisibility(View.VISIBLE);

        easyNoteTv.animate()
                .alpha(1f)
                .setDuration(3000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        easyNoteTv.setVisibility(View.VISIBLE);
                    }
                });

    }
}