package com.efremovkirill.myapplication.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.efremovkirill.myapplication.navigation.NavGraph
import com.efremovkirill.myapplication.navigation.NavScreen
import com.efremovkirill.myapplication.viewmodel.FactViewModel
import com.efremovkirill.myapplication.viewmodel.HomeScreenViewModel

@Composable
fun MainScreen(homeScreenViewModel: HomeScreenViewModel, factViewModel: FactViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        NavGraph(navController = navController, homeScreenViewModel = homeScreenViewModel, factViewModel = factViewModel)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        modifier = Modifier.background(Color.Red),
        backgroundColor = Color(40,4,4)
    ) {
        AddItem(
            navScreen = NavScreen.Home,
            currentDestination = currentDestination,
            navController = navController
        )

        AddItem(
            navScreen = NavScreen.Facts,
            currentDestination = currentDestination,
            navController = navController
        )
    }
}

@Composable
fun RowScope.AddItem(
    navScreen: NavScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        icon = {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = navScreen.icon,
                contentDescription = "Navigation icon",
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == navScreen.route
        } == true,
        onClick = {
            navController.navigate(navScreen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        selectedContentColor = Color.LightGray,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
    )
}