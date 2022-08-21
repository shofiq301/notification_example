package com.cibl.mylearning.networking

import com.cibl.mylearning.models.AlbumItem
import com.cibl.mylearning.models.Albums
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AlbumService {
    @GET("/albums")
    suspend fun getAlbums(): Response<Albums>

    @POST("/albums")
    suspend fun postAlbum(@Body album: AlbumItem): Response<AlbumItem>
}