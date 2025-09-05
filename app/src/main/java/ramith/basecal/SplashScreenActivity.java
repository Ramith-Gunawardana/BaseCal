package ramith.basecal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

/* loaded from: classes.dex */
public class SplashScreenActivity extends AppCompatActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash_screen);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        new Handler().postDelayed(new Runnable() { // from class: ramith.basecal.SplashScreenActivity.1
            @Override // java.lang.Runnable
            public void run() {
                SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this, (Class<?>) MainActivity.class));
                SplashScreenActivity.this.finish();
            }
        }, 200L);
    }
}
