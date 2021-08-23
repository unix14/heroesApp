package com.eyalya.hapoalim.herosapp

import com.eyalya.hapoalim.herosapp.models.Hero
import com.eyalya.hapoalim.herosapp.repos.abs.Top3Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class TopRepoTest : KoinTest {

    val repo: Top3Repository by inject()

    @Test
    fun repoDataSize_isCorrect() {
        val top3Heroes = repo.top3Heroes.getAsList()
        assert(top3Heroes.size == 3)
    }


    @Test
    fun repoResponseSize_isCorrect() {
        val top3Heroes = repo.top3Heroes.getAsList()

        CoroutineScope(Dispatchers.IO).launch {
            val heroList: ArrayList<Hero> = arrayListOf()
            val response = repo.getTopHeroes()
            if (response.heroes.isNotEmpty()) {
                heroList.addAll(response.heroes)
            }

            assert(heroList.size == 3)
            assert(heroList.size == top3Heroes.size)

        }
    }

    @Test
    fun results_isMatching() {
        val top3Heroes = repo.top3Heroes.getAsList()

        CoroutineScope(Dispatchers.IO).launch {
            val heroList: ArrayList<Hero> = arrayListOf()
            val response = repo.getTopHeroes()
            if (response.heroes.isNotEmpty()) {
                heroList.addAll(response.heroes)
            }

            for (hero in top3Heroes) {
                for (i in 0..2) {
                    assert(heroList[i].id == top3Heroes[i].id)
                }
            }
        }
    }
}