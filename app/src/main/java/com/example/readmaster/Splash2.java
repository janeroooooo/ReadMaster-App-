package com.example.readmaster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

public class Splash2 extends AppCompatActivity {
    private static final String TAG = "Splash2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash2);

        Log.d(TAG, "Activity started");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            Log.d(TAG, "Window insets applied");
            return insets;
        });

        Button button = findViewById(R.id.button);
        if (button == null) {
            Log.e(TAG, "Button not found");
        } else {
            Log.d(TAG, "Button initialized");
            button.setOnClickListener(v -> {
                Log.d(TAG, "Button clicked!");
                Intent intent = new Intent(Splash2.this, MainActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }
}
