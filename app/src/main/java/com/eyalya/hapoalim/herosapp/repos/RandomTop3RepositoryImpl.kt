package com.eyalya.hapoalim.herosapp.repos

import com.eyalya.hapoalim.herosapp.data.Constants
import com.eyalya.hapoalim.herosapp.models.Hero
import com.eyalya.hapoalim.herosapp.models.Top3Heroes
import com.eyalya.hapoalim.herosapp.models.TopHeroIdHolder
import com.eyalya.hapoalim.herosapp.models.TopHeroes
import com.eyalya.hapoalim.herosapp.repos.abs.HeroRepository
import com.eyalya.hapoalim.herosapp.repos.abs.Top3Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class RandomTop3RepositoryImpl(private val heroApiRepo: HeroRepository): Top3Repository {

    override val top3Heroes: Top3Heroes
        get() = Top3Heroes(
            TopHeroIdHolder(Random.nextInt(1, Constants.MAX_HERO_ID).toString()),
            TopHeroIdHolder(Random.nextInt(1, Constants.MAX_HERO_ID).toString()),
            TopHeroIdHolder(Random.nextInt(1, Constants.MAX_HERO_ID).toString())
        )

    override suspend fun getTopHeroes(): TopHeroes {
        val heroes: ArrayList<Hero> = arrayListOf()

        for(hero in top3Heroes.getAsList()) {
            val job = CoroutineScope(Dispatchers.IO).launch {
                val request = heroApiRepo.searchById(hero.id)
                if (request.isSuccessful) {
                    val heroJsonObj = request.body.getShortHeroData()
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