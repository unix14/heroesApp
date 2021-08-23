package com.eyalya.hapoalim.herosapp.repos


import com.eyalya.hapoalim.herosapp.data.Constants
import com.eyalya.hapoalim.herosapp.models.*
import com.eyalya.hapoalim.herosapp.repos.abs.HeroRepository
import com.eyalya.hapoalim.herosapp.repos.abs.Top3Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Top3RepositoryImpl(private val heroApiRepo: HeroRepository) : Top3Repository {

    override val top3Heroes: Top3Heroes = Top3Heroes(
        TopHeroIdHolder(Constants.MY_FIRST_HERO_ID),
        TopHeroIdHolder(Constants.MY_SECOND_HERO_ID),
        TopHeroIdHolder(Constants.MY_THIRD_HERO_ID)
    )


    override suspend fun getTopHeroes(): TopHeroes {
        val heroes: ArrayList<Hero> = arrayListOf()
        for(hero in top3Heroes.getAsList()) {
            val job = CoroutineScope(Dispatchers.IO).launch {
//                    make API call to fetch models
                val request = heroApiRepo.searchById(hero.id)
                if (request.isSuccessful) {
                    val heroJsonObj = (request.body as HeroResponse).getShortHeroData()
                    heroes.add(heroJsonObj)
                } else {
                    request.exception?.printStackTrace()
                }
            }
            job.join()
        }
        return TopHeroes(heroes)
    }
}

