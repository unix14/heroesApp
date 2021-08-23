package com.eyalya.hapoalim.herosapp.models


abstract class UiModel() {}


data class Hero(
    val id: String,
    val name: String,
    val image: String,

    val powerStats: PowerStatsData? = null,

    val biography: BiographyData? = null,

    val appearance: HeroAppearance? = null,

    val work: HeroWork? = null,

    val connections: HeroConnections? = null,


    ) : UiModel(), SharableObject {

    override val shareText: String
        get() {
            var text = ""
           if(powerStats != null) {
               text += powerStats.shareText + "\n"
            }
            if(biography != null) {
                text += biography.shareText + "\n"
            }
            if(appearance != null) {
                text += appearance.shareText + "\n"
            }
            if(work != null) {
                text += work.shareText + "\n"
            }
            if(connections != null) {
                text += connections.shareText + "\n"
            }
            return text
        }
}

data class TopHeroes(val heroes: ArrayList<Hero>) : UiModel()



interface SharableObject  {
    val shareText: String
}

fun SharableObject.toString() : String{
    return shareText
}

