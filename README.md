# **Heroes App**
This app is a test project for Bank Hapoalim by Eyal Yaakobi

## Code Structure
The architecture i've used is MVVM, and the important repositories and classes in this code is as follows:

####Abstract: 
    HeroRepository - used to fetch and search heroes from API
####Implementation:
    HeroRepositoryImpl - implemented with safe Api calls

####Abstract:
    Top3Repository - used to fetch The Top 3 Heroes
####Implementations:
    Top3RepositoryImpl - brings hardcoded data from the app and API
    RandomTop3RepositoryImpl - brings random data from the API

####Abstract:
    NavigationManager - used to pass data objects between fragments
####Implementation:
    NavigationManagerImpl - mainly moves hero data when clicked from lists


###Special Classes:
SimpleResponse - is used to wrap basic response from retrofit and generalize the response to one object
LoadingProgressData - helps to distinct events of loading start and end

## Adapters:
Abstract:
    BasicHeroesAdapter - makes all the general info about the hero appear on screen
Implementations:
    HeroesAdapter - for search list in main screen
    TopHeroesAdapter - for the top 3 heroes preselected 

HeroDataAdapter - Adapter for hero data inside of hero screen
BaseHeroDataFragment - represents the basic functionalities of each Hero Data card view presented in hero screen


## Used Libraries
In this project I've used the following libs:
* Koin - for Dependency Injection
* Coil - for fast image requests using coroutines
* LruCache - To cache images for approximately 1 day 
* Retrofit, OkHttp Gson - for networking
* Chrome Custom Tabs - for fast internet search
* Lottie - for cool animations in .JSON format



### Summary
I've wrote 2 UnitTests named: ApiRepoTest and TopRepoTest
Regarding Caching Images - I did tried to implement a much more suitable solution using Room and SQLite but it took me too much time to implement so i switched to LRU Cache
I know this solution does not solve caching images for 1 day, because data is not persisted on LruCache after the app has been killed. 

[Click here to see more cool code repos by Eyal Yaakobi](https://bitbucket.org/3pCupsDev/)
