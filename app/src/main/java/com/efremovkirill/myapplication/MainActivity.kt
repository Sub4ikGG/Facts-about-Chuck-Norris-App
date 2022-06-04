package com.efremovkirill.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.efremovkirill.myapplication.ui.theme.ChuckNorrisFactsTheme
import com.efremovkirill.myapplication.view.MainScreen
import com.efremovkirill.myapplication.viewmodel.FactViewModel
import com.efremovkirill.myapplication.viewmodel.FactViewModelFactory
import com.efremovkirill.myapplication.viewmodel.HomeScreenViewModel

class MainActivity : ComponentActivity() {
    private val homeScreenViewModel: HomeScreenViewModel by viewModels()
    private lateinit var factViewModel: FactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChuckNorrisFactsTheme {
                factViewModel = viewModel(factory = FactViewModelFactory(application))
                MainScreen(homeScreenViewModel = homeScreenViewModel, factViewModel = factViewModel)
            }
        }
    }
}