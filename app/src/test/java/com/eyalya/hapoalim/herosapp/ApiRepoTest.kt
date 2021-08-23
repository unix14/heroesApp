package com.eyalya.hapoalim.herosapp

import com.eyalya.hapoalim.herosapp.models.Hero
import com.eyalya.hapoalim.herosapp.repos.abs.HeroRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class ApiRepoTest : KoinTest {

    val repo: HeroRepository by inject()

    @Test
    fun simpleSearchResult_isCorrect() {
        val heroName = "Spider-Man"

        CoroutineScope(Dispatchers.IO).launch {
            val heroResults = repo.searchHero(heroName)
            assert(heroResults.body.results.size == 3)        //there are 3 spider people in this API
            assert(heroResults.body.results[0].name == heroName)        //we check if it's the same name as we searched
        }
    }


    @Test
    fun simpleGetIdResult_isCorrect() {
        val heroId = "70"       //spider-man id

        CoroutineScope(Dispatchers.IO).launch {
            val heroResponse = repo.searchById(heroId)

            assert(heroResponse.isSuccessful)
            assert(heroResponse.body.id == heroId)
        }
    }

}