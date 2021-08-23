package com.eyalya.hapoalim.herosapp.repos.abs

import com.eyalya.hapoalim.herosapp.models.Top3Heroes
import com.eyalya.hapoalim.herosapp.models.TopHeroes


interface Top3Repository : BasicRepository {

    val top3Heroes : Top3Heroes

    suspend fun getTopHeroes() : TopHeroes
}

