package com.eyalya.hapoalim.herosapp.ui.home

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eyalya.hapoalim.herosapp.ui.MainActivity
import com.eyalya.hapoalim.herosapp.adapters.BasicHeroesAdapter
import com.eyalya.hapoalim.herosapp.adapters.HeroesAdapter
import com.eyalya.hapoalim.herosapp.adapters.TopHeroesAdapter
import com.eyalya.hapoalim.herosapp.common.hideKeyboard
import com.eyalya.hapoalim.herosapp.common.showKeyboard
import com.eyalya.hapoalim.herosapp.databinding.HomeFragmentBinding
import com.eyalya.hapoalim.herosapp.models.Hero
import com.eyalya.hapoalim.herosapp.navigation.NavigationManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), BasicHeroesAdapter.HeroClickCallback {

    private lateinit var binding: HomeFragmentBinding
    private val viewModel by viewModel<HomeViewModel>()

    private val navigationManager: NavigationManager
        get() = (activity as MainActivity).navigationManager

    private var didSearchAlready: Boolean = false
    override fun getNavController(): NavController = findNavController()

    private var searchQuery: String
        get() = binding.mainFrame.searchBox.text.toString()
        set(value) {
            binding.mainFrame.searchBox.setText(value)
        }

    private val adapter: HeroesAdapter
        get() {
            return binding.mainFrame.heroesRecycler.adapter as HeroesAdapter
        }


    private val top3adapter: TopHeroesAdapter
        get() {
            return binding.topHeroesRecycler.adapter as TopHeroesAdapter
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(!::binding.isInitialized) {
            //to make sure it happens once
            binding = HomeFragmentBinding.inflate(LayoutInflater.from(context), container, false)
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclers()
        initObservers()
        initSearch()
    }

    private fun initSearch() = with(binding) {
        mainFrame.clearSearch.setOnClickListener {
            mainFrame.searchBox.text.clear()
            mainFrame.clearSearch.isVisible = false
        }
        fab.setOnClickListener {
            mainFrame.apply {
                searchBox.isVisible = !searchBox.isVisible
                clearSearch.isVisible = searchBox.isVisible && didSearchAlready && searchQuery.isNotEmpty()
                emptyScreenPlaceholder.isVisible = !searchBox.isVisible
                superHeroAnim.isVisible = emptyScreenPlaceholder.isVisible
                heroesRecycler.isVisible = searchBox.isVisible
                noResultsPlacholder.isVisible = searchBox.isVisible && adapter.list.isEmpty() && didSearchAlready && searchQuery.isNotEmpty()
                noResultsAnim.isVisible = noResultsPlacholder.isVisible

                if(!searchBox.isVisible) {
                    hideKeyboard()
                } else {
                    searchBox.requestFocus()
                    showKeyboard(searchBox)
                }
            }
        }

        mainFrame.searchBox.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus) {
                showKeyboard(mainFrame.searchBox)
            } else {
                hideKeyboard()
            }
        }

        mainFrame.searchBox.doAfterTextChanged {
            if(isResumed) {
                mainFrame.clearSearch.isVisible = searchQuery.isNotEmpty() && didSearchAlready
                doSearch()
            }
        }

        mainFrame.searchBox.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener if(event?.action == EditorInfo.IME_ACTION_GO) {
                doSearch()
                hideKeyboard()
                true
            } else false
        }
    }

    private fun doSearch() {
        if(isResumed) {
            viewModel.searchForHero(searchQuery)
            if(!didSearchAlready){
                didSearchAlready = true
            }
        }
    }

    private fun initRecyclers() = with(binding) {
        mainFrame.heroesRecycler.apply {
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            adapter = HeroesAdapter(listener= this@HomeFragment)
        }

        topHeroesRecycler.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = TopHeroesAdapter(listener= this@HomeFragment)
        }
    }

    private fun initObservers() = with(viewModel) {
        progressData.observe(viewLifecycleOwner) {
            binding.mainFrame.progressBar.isVisible = it
        }
        topBarTitleData.observe(viewLifecycleOwner) {
            binding.toolbarLayout.title = it
        }
        topHeroesData.observe(viewLifecycleOwner) {
            if(!it.isNullOrEmpty()) {
                binding.topProgressBar.isVisible = false
                binding.topHeroesLayout.isVisible = true
                top3adapter.submitList(it)
            } else {
                binding.topProgressBar.isVisible = true
                binding.topHeroesLayout.isVisible = false
            }
        }
        heroesData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.mainFrame.noResultsPlacholder.isVisible = it.isNullOrEmpty() && didSearchAlready && searchQuery.isNotEmpty()
            binding.mainFrame.noResultsAnim.isVisible = binding.mainFrame.noResultsPlacholder.isVisible
        }
    }

    override fun onHeroClick(hero: Hero, extras: FragmentNavigator.Extras) {
        navigationManager.onShowHero(hero, extras)
    }
}