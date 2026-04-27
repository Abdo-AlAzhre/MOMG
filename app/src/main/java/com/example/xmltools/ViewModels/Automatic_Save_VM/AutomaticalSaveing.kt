package com.example.xmltools.ViewModels.Automatic_Save_VM

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.concurrent.TimeUnit
import androidx.lifecycle.AndroidViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.money.trackpay.R
import com.example.xmltools.creating_realm_data.Realm_Withdrawal_LocalData.SavingWithdrawalData
import java.io.File

// viewModel to Automatic save :
class BackupViewModel(application: Application) : AndroidViewModel(application) {

    private val workManager = WorkManager.getInstance(application)

    fun startDailyBackup() {
        val workRequest = PeriodicWorkRequestBuilder<AutomaticSaving>(
            24, TimeUnit.HOURS
        ).build()

        workManager.enqueueUniquePeriodicWork(
            "realmBackupWork",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

}

class AutomaticSaving(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        return try {
            outBackupRealmDatabase(applicationContext)
//            showNotification(applicationContext)
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

    // to save backup after 24 hour :
    private fun outBackupRealmDatabase(context: Context) {

        val realm = SavingWithdrawalData.realmWithdrawal
        val realmFile = File(realm.configuration.path)
        // المجلد اللي هتنسخ فيه
        val backupDir = File(context.getExternalFilesDir(null), "RealmBackup")
        if (!backupDir.exists()) backupDir.mkdirs()
        // اسم الملف الاحتياطي
        val backupFile = File(backupDir, "realm_backup.realm")
        realmFile.copyTo(backupFile, overwrite = true)
    }
    // to show Notification :
    @SuppressLint("MissingPermission")
    private fun showNotification(context: Context) {
        val channelId = "backup_channel_id"
        val notificationId = 1001

        // إنشاء قناة الإشعار (لأندرويد 8 وأعلى)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Backup Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for Realm database backups"
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.currency)
            .setContentTitle("✅ تم إنشاء النسخة الاحتياطية")
            .setContentText("")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        // عرض الإشعار
        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, notification)
        }

    }

}