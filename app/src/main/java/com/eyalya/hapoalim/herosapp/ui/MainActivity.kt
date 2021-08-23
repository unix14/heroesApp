package com.eyalya.hapoalim.herosapp.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import com.eyalya.hapoalim.herosapp.R
import com.eyalya.hapoalim.herosapp.cache.LruCacheManager
import com.eyalya.hapoalim.herosapp.cache.LruCacheManagerImpl
import com.eyalya.hapoalim.herosapp.databinding.ActivityMainBinding
import com.eyalya.hapoalim.herosapp.navigation.NavigationManager
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding

    val navigationManager: NavigationManager by inject()

    val cacheManager : LruCacheManager<Bitmap> by inject()


    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var bitmapCacheManager : LruCacheManagerImpl
            private set
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bitmapCacheManager = cacheManager as LruCacheManagerImpl

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        setSupportActionBar(findViewById(R.id.toolbar))
        findNavController(binding.fragContainerView).addOnDestinationChangedListener(this)

        initObservers()
    }

    private fun initObservers() {
        navigationManager.navigationEventData.observe(this) { navEvent ->
            navEvent?.let {
                findNavController(binding.fragContainerView).let { navController: NavController ->
                    when (it) {
                        is NavigationManager.NavigationEvent.ShowHeroPage -> {
                            navController.navigate(
                                R.id.HeroPageFragment, null,
                                null, it.extras
                            )
                        }
                        is NavigationManager.NavigationEvent.ShowHomePage -> {
                            navController.navigate(R.id.HomeFragment)
                        }
                    }
                }
            }
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.HeroPageFragment -> {

            }
            R.id.HomeFragment -> {
                navigationManager.navigationEventData.postValue(null)
            }
        }
    }
}