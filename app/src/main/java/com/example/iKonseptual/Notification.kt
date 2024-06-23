package com.example.iKonseptual

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val notificationID = 1
const val channelID = "channel1"

class Notification : BroadcastReceiver()
{
    override fun onReceive(context: Context, intent: Intent){
        val action = "count"
        getDataCount(action,context)
    }
    private fun showNotification(context: Context,count:List<CountDays>){
        val notificationIntent = Intent(context, LoginActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val contentText = count.joinToString(separator = "\n") { "${it.Keterangan}: ${it.Value}" }

        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("i-Konseptual")
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_HIGH) // High priority for heads-up notification
            .setDefaults(NotificationCompat.DEFAULT_ALL) // Default sound, vibration, and lights
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val  manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)
    }

    private fun getDataCount(action:String,context: Context){
        PenyelidikanPenyidikanClient.countInstance.getCountData(action).enqueue(object : Callback<CountResponse>{
            override fun onResponse(call: Call<CountResponse>, response: Response<CountResponse>) {
                if(response.isSuccessful && response.body() != null){
                    val data = response.body()?.data ?: emptyList()
                    showNotification(context, data)
                }
            }
            override fun onFailure(call: Call<CountResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}