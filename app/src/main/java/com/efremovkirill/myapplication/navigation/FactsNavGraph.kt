package com.efremovkirill.myapplication.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.efremovkirill.myapplication.data.FactModel
import com.efremovkirill.myapplication.view.FactDetailScreen
import com.efremovkirill.myapplication.view.FactsScreen
import com.efremovkirill.myapplication.viewmodel.FactViewModel

fun NavGraphBuilder.factNavGraph(
    navController: NavController,
    factViewModel: FactViewModel
) {
    navigation(
        startDestination = "facts_home",
        route = FACTS_ROUTE
    ) {
        composable(route = "facts_home") {
            FactsScreen(navController = navController, factViewModel = factViewModel)
        }
        composable(route = NavScreen.FactDetail.route) {
            navController.previousBackStackEntry?.arguments?.getParcelable<FactModel>("FACT_KEY")?.let {
                FactDetailScreen(navController = navController, it)
            }
        }
    }
}