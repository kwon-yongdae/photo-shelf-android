package com.yongdae.photoshelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.yongdae.photoshelf.ui.PhotoShelfApp
import com.yongdae.photoshelf.ui.theme.PhotoShelfTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhotoShelfTheme {
                PhotoShelfApp()
            }
        }
    }
}