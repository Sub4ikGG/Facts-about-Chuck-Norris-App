package com.efremovkirill.myapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

const val FACTS_ROUTE = "facts"
const val ROOT_ROUTE = "root"

sealed class NavScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home: NavScreen(
        route = "home",
        title = "Facts",
        icon = Icons.Default.Search
    )

    object Facts: NavScreen(
        route = "facts",
        title = "Facts",
        icon = Icons.Default.Favorite
    )

    object FactDetail: NavScreen(
        route = "fact_detail",
        title = "Facts_detail",
        icon = Icons.Default.Notifications
    )
}
