package com.eyalya.hapoalim.herosapp.repos.abs

import com.eyalya.hapoalim.herosapp.models.HeroListResponse
import com.eyalya.hapoalim.herosapp.models.HeroResponse
import com.eyalya.hapoalim.herosapp.models.SimpleResponse

interface HeroRepository : BasicRepository {

    suspend fun searchHero(query: String): SimpleResponse<HeroListResponse>

    suspend fun searchById(id: String): SimpleResponse<HeroResponse>

}