package com.nagwa.instabugchallenge.modules.words.presentation.view.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.nagwa.instabugchallenge.R
import com.nagwa.instabugchallenge.core.di.module.InstaViewModelFactory
import com.nagwa.instabugchallenge.databinding.ActivityMainBinding
import com.nagwa.instabugchallenge.modules.words.presentation.viewmodel.WordsViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {


    @Inject
    lateinit var viewModelFactory: InstaViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(WordsViewModel::class.java)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniViews()
        initActions()
    }

    private fun initActions() {
        binding.imgSearch.setOnClickListener {
            openSearchFragment()
        }
        binding.imgSort.setOnClickListener {
            val latestValue = viewModel.sortClickEvent.value ?: false
            viewModel.sortClickEvent.value = !latestValue
            binding.imgSort.isSelected = !latestValue

        }
    }

    private fun iniViews() {

    }
    private fun openSearchFragment() {
        if (findMainNavController().currentDestination?.id != R.id.searchFragment) {
            findMainNavController().navigate(R.id.searchFragment)
        }
    }

    override fun onBackPressed() {
        if (findMainNavController().currentDestination?.id != R.id.homeFragment) {
            super.onBackPressed()
        } else {
            finish()
        }
    }

    private fun findMainNavController(): NavController {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_main_fragment) as NavHostFragment
        return navHostFragment.navController
    }
}