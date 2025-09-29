package com.todaylab.photodiary

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.todaylab.photodiary.presentation.navigation.AppNavGraph
import com.todaylab.photodiary.ui.theme.ExampleTheme
import com.todaylab.photodiary.ui.util.scheduleDailyReminder
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import android.Manifest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // 권한 요청 런처
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // 허용됨
                scheduleDailyReminder(this)
            } else {
                // 거부됨 → 알림을 못 쓸 수도 있음
                Timber.w("알림 권한이 거부됨")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Android 13 이상에서만 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // 이미 허용됨
                    scheduleDailyReminder(this)
                }
                else -> {
                    // 권한 요청
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            // Android 12 이하 → 바로 스케줄 등록 가능
            scheduleDailyReminder(this)
        }

        enableEdgeToEdge()
        setContent {
            ExampleTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}
