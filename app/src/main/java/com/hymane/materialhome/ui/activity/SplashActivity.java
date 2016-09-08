package com.hymane.materialhome.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.hymane.materialhome.R;

import me.wangyuwei.particleview.ParticleView;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/8
 * Description:
 */

public class SplashActivity extends AppCompatActivity {
    private ParticleView pv_logo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        pv_logo = (ParticleView) findViewById(R.id.pv_logo);
        pv_logo.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        });
        pv_logo.startAnim();
    }
}
