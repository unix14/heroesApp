package com.eyalya.hapoalim.herosapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eyalya.hapoalim.herosapp.common.LoadingProgressData
import com.eyalya.hapoalim.herosapp.common.runCoroutine
import com.eyalya.hapoalim.herosapp.models.Hero
import com.eyalya.hapoalim.herosapp.repos.abs.HeroRepository
import com.eyalya.hapoalim.herosapp.repos.abs.Top3Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay

class HomeViewModel constructor(private val repo: HeroRepository, private val top3Repository: Top3Repository) : ViewModel() {

    private var searchJob: Job? = null
    val topHeroesData: MutableLiveData<ArrayList<Hero>> = MutableLiveData()
    val heroesData: MutableLiveData<ArrayList<Hero>> = MutableLiveData()
    val topBarTitleData: MutableLiveData<String> = MutableLiveData()

    val progressData = LoadingProgressData()

    init {
        initTopHeroes()
    }


    private fun initTopHeroes() {
        runCoroutine {
            val result = top3Repository.getTopHeroes()
            if (!result.heroes.isNullOrEmpty()) {
                topHeroesData.postValue(result.heroes)
            }
        }
    }


    fun searchForHero(query: String = "") {
        searchJob?.cancel("Need to research")

        if(query.isEmpty()) {
            heroesData.postValue(arrayListOf())
            topBarTitleData.postValue("")
            progressData.endProgress()
            return
        }

        searchJob = runCoroutine {
            progressData.startProgress()
            topBarTitleData.postValue("Search: $query")
            delay(850)

            val request = repo.searchHero(query)
            if (request.isSuccessful) {
                val response = request.body
                val results = response.results
                if(results.isNullOrEmpty()) {
                    heroesData.postValue(arrayListOf())
                } else {
                    val heroesList = response.getHeroListForUi()
                    heroesData.postValue(heroesList)
                }
            } else {
                request.exception?.printStackTrace()
                heroesData.postValue(arrayListOf())
            }
            progressData.endProgress()
        }
    }
}