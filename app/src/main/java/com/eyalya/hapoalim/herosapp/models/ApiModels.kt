package com.eyalya.hapoalim.herosapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*
import kotlin.random.Random

abstract class BasicHeroApiResponse : Serializable {
    abstract val response: String
}

abstract class BasicMultiHeroResponse<T> : BasicHeroApiResponse() {
    abstract val resultsFor: String      ///query
    abstract val results: ArrayList<T>
}

data class HeroListResponse(
    @SerializedName("response")
    override val response: String,
    @SerializedName("results-for")
    override val resultsFor: String,
    @SerializedName("results")
    override val results: ArrayList<HeroResponse>
) : BasicMultiHeroResponse<HeroResponse>() {

    fun getHeroListForUi(): ArrayList<Hero> {
        val heroes: ArrayList<Hero> = arrayListOf()

        for (hero in results) {
            heroes.add(hero.getShortHeroData())
        }
        return heroes
    }
}

data class HeroResponse(
    override val response: String,
    val id: String? = null,
    val name: String? = null,

    val powerStats: PowerStatsData? = null,
    val biography: BiographyData? = null,
    val appearance: HeroAppearance? = null,
    val work: HeroWork? = null,
    val connections: HeroConnections? = null,

    val image: HeroImage


) : BasicHeroApiResponse() {
    fun getShortHeroData(): Hero {
        return Hero(
            id ?: Random.nextInt(999, 2000).toString(),
            name ?: "Unknown",
            image.url ?: "https://picsum.photos/200"// random placeholder,
            , powerStats,
            biography,
            appearance,
            work,
            connections
        )
    }
}

data class HeroConnections(
    @SerializedName("group-affiliation")
    val groups: String? = null,
    val relatives: String? = null
) : SharableObject {
    override val shareText
        get() = "This hero knows the groups $groups, and has the relatives of $relatives"
}


data class HeroWork(
    val occupation: String? = null,
    val base: String? = null
) : SharableObject {
    override val shareText
        get() = "In the day-today the Hero works at $occupation located in $base"
}

data class HeroAppearance(
    val gender: String? = null,
    val race: String? = null,

    val height: ArrayList<String?>,
    val weight: ArrayList<String?>,

    @SerializedName("eye-color")
    val eyeColor: String? = null,

    @SerializedName("hair-color")
    val hairColor: String? = null,
) : SharableObject {

    override val shareText: String
        get() = when {
            gender?.toLowerCase(Locale.ROOT)?.contains("female") == true -> {
                "She has a $hairColor colored hair with $eyeColor eyes!"
            }
            gender?.toLowerCase(Locale.ROOT)?.contains("male") == true -> {
                "He's hair is $hairColor and he's $race"
            }
            else -> {
                "It's gender is unknown to mankind, while it's hair is $hairColor and its a $race\""
            }
        }
}


data class PowerStatsData(
    val intelligence: String? = null,
    val strength: String? = null,
    val speed: String? = null,
    val durability: String? = null,
    val combat: String? = null
) : Serializable, SharableObject {
    override val shareText
        get() = "This Superhero capabilities is as follows\nintelligence: $intelligence\nstrength: $strength\nspeed: $speed\ndurability: $durability\ncombat: $combat\n"

}


data class BiographyData(

    @SerializedName("full-name")
    val fullName: String? = null,

    @SerializedName("alter-egos")
    val alterEgos: String? = null,

    @SerializedName("aliases")
    val aliases: ArrayList<String?>,

    @SerializedName("place-of-birth")
    val placeOfBirth: String? = null,

    @SerializedName("first-appearance")
    val firstAppearance: String? = null,

    val publisher: String? = null,

    val alignment: String? = null,
) : Serializable, SharableObject {

    val getAliases: String = aliases.joinToString()

    override val shareText
        get() = "$fullName is a $alignment fella who was born in $placeOfBirth. The first time on screen was on $firstAppearance. Also known as $getAliases. Hero is published by $publisher."
}


data class HeroImage(
    val url: String? = null
)

data class TopHeroIdHolder(val id: String)

data class Top3Heroes(
    private val firstHero: TopHeroIdHolder,
    private val secondHero: TopHeroIdHolder,
    private val thirdHero: TopHeroIdHolder
) {
    private val triple: Triple<TopHeroIdHolder, TopHeroIdHolder, TopHeroIdHolder> =
        Triple(firstHero, secondHero, thirdHero)

    companion object {
        const val FIRST = 0
        const val SECOND = 1
        const val THIRD = 2
    }

    private fun getByIndex(index: Int): TopHeroIdHolder {
        return when (index) {
            FIRST -> {
                triple.first
            }
            SECOND -> {
                triple.second
            }
            else -> {
                triple.third
            }
        }
    }

    fun getFirstHero(): TopHeroIdHolder {
        return getByIndex(FIRST)
    }

    fun getSecondHero(): TopHeroIdHolder {
        return getByIndex(SECOND)
    }

    fun getThirdHero(): TopHeroIdHolder {
        return getByIndex(THIRD)
    }

    fun getAsList(): ArrayList<TopHeroIdHolder> {
        val list = arrayListOf<TopHeroIdHolder>()
        list.add(triple.first)
        list.add(triple.second)
        list.add(triple.third)
        return list
    }

}