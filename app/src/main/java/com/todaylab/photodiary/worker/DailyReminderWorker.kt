package com.todaylab.photodiary.worker

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.todaylab.photodiary.R

class DailyReminderWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        showNotification("오늘 하루도 그림일기를 남겨보세요 🌿")
        return Result.success()
    }

    private fun showNotification(message: String) {
        val builder = NotificationCompat.Builder(applicationContext, "daily_reminder")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // 앱 아이콘으로 교체 가능
            .setContentTitle("그림일기")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, builder.build())
    }
}