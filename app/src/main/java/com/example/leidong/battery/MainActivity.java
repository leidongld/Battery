package com.example.leidong.battery;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.leidong.battery.view.Battery;

public class MainActivity extends AppCompatActivity {
    private Battery mBattery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBattery = findViewById(R.id.battery);
    }

    /**
     * Click battery
     *
     * @param view
     */
    public void onClickBattery(View view) {
        // 设置属性动画并播放
        ObjectAnimator animator = ObjectAnimator.ofInt(mBattery, "power", 0, 100);
        animator.setDuration(10000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }
}
