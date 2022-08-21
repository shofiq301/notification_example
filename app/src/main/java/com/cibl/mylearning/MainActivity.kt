package com.cibl.mylearning

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.core.content.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.cibl.mylearning.databinding.ActivityMainBinding
import com.cibl.mylearning.models.AlbumItem
import com.cibl.mylearning.models.Albums
import com.cibl.mylearning.networking.AlbumService
import com.cibl.mylearning.networking.NetworkInstance
import okhttp3.internal.notify
import retrofit2.Response
import android.app.PendingIntent.FLAG_UPDATE_CURRENT as FLAG_UPDATE_CURRENT

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val channelID = "com.cibl.mylearning.channel1"
    private var notificationManager: NotificationManager? = null
    companion object{
        const val KEY_REPLY = "key_reply"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelID, "Demo Channel", "Demo description")

        binding.btnNotif.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                displayNotification()
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.S)
    private fun displayNotification(){
        val notificationId = 50
        val tapIntent = Intent(this, SecondActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =  PendingIntent.getActivity(
            this,
            0,
            tapIntent,
            FLAG_MUTABLE
        )

        //Action button 1
        val intent1 = Intent(this, SettingsActivity::class.java)
        val pendingIntent1: PendingIntent =  PendingIntent.getActivity(
            this,
            0,
            intent1,
            FLAG_MUTABLE
        )
        //Action button 2
        val intent2 = Intent(this, DetailsActivity::class.java)
        val pendingIntent2: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent2,
            FLAG_MUTABLE
        )

        //Remote input
        val remoteInput = RemoteInput.Builder(KEY_REPLY).run {
            setLabel("Input your name")
            build()
        }
        val replyAction = NotificationCompat.Action.Builder(
            0,
            "Reply",
            pendingIntent
        ).addRemoteInput(remoteInput)
            .build()


        val action1 = NotificationCompat.Action.Builder(0,"Settings",pendingIntent1).build()
        val action2 = NotificationCompat.Action.Builder(0,"Details",pendingIntent2).build()
        val notification = NotificationCompat.Builder(this, channelID)
            .setContentTitle("Demo notification")
            .setContentText("This is a learning application with notification manager")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(replyAction)
            .addAction(action1)
            .addAction(action2)
            .build()
        notificationManager?.notify(notificationId,notification)
    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).also {
                it.description = channelDescription
            }
            notificationManager?.createNotificationChannel(channel)
        }
    }


    private fun performGetRequest(){
        val networkInstance = NetworkInstance(this)

        val networkService: AlbumService = networkInstance
            .getNetworkInstance()
            .create(AlbumService::class.java)
        val responseData: LiveData<Response<AlbumItem>> = liveData {
            val album = AlbumItem(0, "My title", 3)
            val response =networkService.postAlbum(album)
            emit(response)
        }

        responseData.observe(this){
            Log.i("DATA_BACK", "onCreate: ${it.message()}")
        }
    }
}