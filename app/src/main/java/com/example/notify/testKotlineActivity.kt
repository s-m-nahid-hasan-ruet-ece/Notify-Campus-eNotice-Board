package com.example.notify

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContentProviderCompat.requireContext

class testKotlineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        }
        setContentView(R.layout.search_fragment)


    }
}