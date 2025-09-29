package com.todaylab.photodiary.ui.util

import android.content.Context
import com.todaylab.photodiary.worker.DailyReminderWorker
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder


fun scheduleDailyReminder(context: Context) {
    val workRequest = PeriodicWorkRequestBuilder<DailyReminderWorker>(
        1, TimeUnit.DAYS
    )
        .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS) // 원하는 시간에 맞추기
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "daily_reminder_work",
        ExistingPeriodicWorkPolicy.REPLACE,
        workRequest
    )

    // 테스트용 WorkManager
//    WorkManager.getInstance(context).enqueueUniqueWork(
//        "test_reminder",
//        ExistingWorkPolicy.REPLACE,
//        OneTimeWorkRequestBuilder<DailyReminderWorker>()
//            .setInitialDelay(5, TimeUnit.SECONDS)
//            .build()
//    )
}

// 예: 매일 저녁 9시에 울리도록 초기 딜레이 계산
fun calculateInitialDelay(): Long {
    val now = LocalDateTime.now()
    val targetTime = now.withHour(21).withMinute(0).withSecond(0).withNano(0) // 21시
    var delay = Duration.between(now, targetTime).toMillis()
    if (delay < 0) {
        delay += Duration.ofDays(1).toMillis() // 이미 지난 시간이면 내일로 설정
    }
    return delay
}
