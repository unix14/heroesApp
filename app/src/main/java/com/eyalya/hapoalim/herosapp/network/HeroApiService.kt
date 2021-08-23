package com.eyalya.hapoalim.herosapp.network

import com.eyalya.hapoalim.herosapp.data.Constants
import com.eyalya.hapoalim.herosapp.models.HeroListResponse
import com.eyalya.hapoalim.herosapp.models.HeroResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HeroApiService {

    //search characters by name
    @GET("{key}/search/{name}")
    suspend fun searchHero(
        @Path("key") key: String = Constants.SUPERHEROS_API_KEY,
        @Path("name") query: String,
    ): Response<HeroListResponse>

    //get character by id
    @GET("{key}/{id}")
    suspend fun getHeroById(
        @Path("key") key: String = Constants.SUPERHEROS_API_KEY,
        @Path("id") heroId: String
    ): Response<HeroResponse>

}