package com.efremovkirill.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.efremovkirill.myapplication.view.*
import com.efremovkirill.myapplication.viewmodel.FactViewModel
import com.efremovkirill.myapplication.viewmodel.HomeScreenViewModel

@Composable
fun NavGraph(navController: NavHostController, homeScreenViewModel: HomeScreenViewModel, factViewModel: FactViewModel) {
    NavHost(
        navController = navController,
        startDestination = NavScreen.Home.route,
        route = ROOT_ROUTE
    ) {
        composable(route = NavScreen.Home.route) {
            HomeScreen(homeScreenViewModel, factViewModel = factViewModel)
        }
        factNavGraph(navController = navController, factViewModel = factViewModel)
    }
}