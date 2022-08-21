package com.cibl.mylearning

import android.app.NotificationManager
import android.app.RemoteInput
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.app.NotificationCompat
import com.cibl.mylearning.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    lateinit var binding: ActivitySecondBinding
    companion object{
        const val KEY_REPLY = "key_reply"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        receiveInput()
    }

    private fun receiveInput() {
        val intent = this.intent
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput != null){
            val inputString = remoteInput.getString(KEY_REPLY)
            binding.txtInput.text = inputString

            val channelID = "com.cibl.mylearning.channel1"
            val notificationId = 50
            val notification = NotificationCompat.Builder(this, channelID)
                .setContentTitle("Demo notification")
                .setContentText("We received your reply")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
             notificationManager.notify(notificationId,notification)
        }
    }
}