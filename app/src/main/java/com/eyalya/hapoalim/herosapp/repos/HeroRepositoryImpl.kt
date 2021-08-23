package com.eyalya.hapoalim.herosapp.repos

import com.eyalya.hapoalim.herosapp.common.safeApiCall
import com.eyalya.hapoalim.herosapp.models.HeroListResponse
import com.eyalya.hapoalim.herosapp.models.HeroResponse
import com.eyalya.hapoalim.herosapp.models.SimpleResponse
import com.eyalya.hapoalim.herosapp.network.HeroApiService
import com.eyalya.hapoalim.herosapp.repos.abs.HeroRepository

class HeroRepositoryImpl(private val apiService: HeroApiService) : HeroRepository {


    override suspend fun searchHero(query: String): SimpleResponse<HeroListResponse> {
        return safeApiCall { apiService.searchHero(query= query) }
    }


    override suspend fun searchById(id: String): SimpleResponse<HeroResponse> {
        return safeApiCall { apiService.getHeroById(heroId= id) }
    }


}