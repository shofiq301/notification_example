package com.cibl.mylearning.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.cibl.mylearning.models.Albums
import com.cibl.mylearning.networking.AlbumService
import com.cibl.mylearning.networking.NetworkInstance
import retrofit2.Response

class AlbumRepository {
//    var networkService: AlbumService = NetworkInstance
//        .getNetworkInstance()
//        .create(AlbumService::class.java)
//    val responseData: LiveData<Response<Albums>> = liveData {
//        val response = networkService.getAlbums()
//        emit(response)
//    }
}