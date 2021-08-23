# **Heroes App**
This app is a test project for Bank Hapoalim by Eyal Yaakobi

### Notes
I've wrote 2 UnitTests named: ApiRepoTest and TopRepoTest <br/>
Regarding Caching Images - I did tried to implement a much more suitable solution using Room and SQLite but it took me too much time to implement so i switched to LRU Cache, I know this solution does not solve caching images for 1 day, because data is not persisted on LruCache after the app has been killed. 


## Used Libraries
In this project I've used the following libs:
* [Koin](https://github.com/InsertKoinIO/koin) - for Dependency Injection
* [Coil](https://github.com/coil-kt/coil) - for fast image requests using coroutines
* [LruCache](https://developer.android.com/reference/androidx/collection/LruCache) - To cache images for approximately 1 day 
* [Retrofit](https://github.com/square/retrofit), OkHttp Gson - for networking
* [Chrome Custom Tabs](https://developer.android.com/reference/androidx/browser/customtabs/CustomTabsIntent) - for fast internet search
* [Lottie](https://github.com/airbnb/lottie-android) - for cool animations in .JSON format

[Click here to see more cool code repos by Eyal Yaakobi](https://bitbucket.org/3pCupsDev/)

## Code Structure
The architecture i've used is MVVM, and the important repositories and classes in this code is as follows:

### HeroRepository
**Abstract:** 
    HeroRepository - used to fetch and search heroes from API <br/> <br/>
**Implementations:**
    HeroRepositoryImpl - implemented with safe Api calls <br/>

### Top3Repository
**Abstract:**
    Top3Repository - used to fetch The Top 3 Heroes <br/> <br/>
**Implementations:**
    Top3RepositoryImpl - brings hardcoded data from the app and API <br/>
    RandomTop3RepositoryImpl - brings random data from the API <br/>

### NavigationManager
**Abstract:**
    **NavigationManager** - used to pass data objects between fragments <br/> <br/>
**Implementation:**
    **NavigationManagerImpl** - mainly moves hero data when clicked from lists


### Special Classes:
**SimpleResponse** - is used to wrap basic response from retrofit and generalize the response to one object. <br/>
**LoadingProgressData** - helps to distinct events of loading start and end <br/>

## Adapters:
**Abstract:**
    **BasicHeroesAdapter** - makes all the general info about the hero appear on screen. <br/> <br/>
**Implementation:**
    **HeroesAdapter** - for search list in main screen. <br/>
    **TopHeroesAdapter** - for the top 3 heroes preselected. <br/>

**HeroDataAdapter** - Adapter for hero data inside of hero screen <br/>
**BaseHeroDataFragment** - represents the basic functionalities of each Hero Data card view presented in hero screen

[Click here to visit my website](https://3p-cups.com/)
