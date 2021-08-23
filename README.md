Heroes App

This app is a test project for Bank Hapoalim by Eyal Yaakobi

In this project I've used the following libs:
Koin - for Dependency Injection
Coil - for fast image requests using coroutines
LruCache - To cache images for approximately 1 day 
Retrofit, OkHttp Gson - for networking
Chrome Custom Tabs - for fast internet search
Lottie - for cool animations in .JSON format

The architecture i've used is MVVM

The important Repos in this code are as follows:

Abs:
    HeroRepository - used to fetch and search heroes from API
Impl:
    HeroRepositoryImpl - implemented with safe Api calls

Abs:
    Top3Repository - used to fetch The Top 3 Heroes
Impls:
    Top3RepositoryImpl - brings hardcoded data from the app and API
    RandomTop3RepositoryImpl - brings random data from the API

Abs:
    NavigationManager - used to pass data objects between fragments
Impl:
    NavigationManagerImpl - mainly moves hero data when clicked from lists


Special Classes:
SimpleResponse - is used to wrap basic response from retrofit and generalize the response to one object
LoadingProgressData - helps to distinct events of loading start and end

Adapters:
Abs:
    BasicHeroesAdapter - makes all the general info about the hero appear on screen
Impls:
    HeroesAdapter - for search list in main screen
    TopHeroesAdapter - for the top 3 heroes preselected 

HeroDataAdapter - Adapter for hero data inside of hero screen

I've wrote 2 UnitTests named: ApiRepoTest and TopRepoTest
Regarding Caching Images - I did tried to implement a much more suitable solution using Room and SQLite but it took me too much time to implement so i switched to LRU Cache
I know this solution does not solve caching images for 1 day, because data is not persisted on LruCache after the app has been killed. 