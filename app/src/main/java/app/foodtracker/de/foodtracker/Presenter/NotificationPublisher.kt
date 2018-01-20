package app.foodtracker.de.foodtracker.Presenter

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by normen on 20.01.18.
 */
class NotificationPublisher: BroadcastReceiver(){

    private val NOTIFICATION_ID = "notification_id"
    private val NOTIFICATION = "notification"

    override fun onReceive(context: Context?, intent: Intent?) {
        var notificationManager: NotificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var notifaction: Notification = intent!!.getParcelableExtra(NOTIFICATION)
        var notificationId = intent.getIntExtra(NOTIFICATION_ID,0)
        notificationManager.notify(notificationId, notifaction)

    }

}