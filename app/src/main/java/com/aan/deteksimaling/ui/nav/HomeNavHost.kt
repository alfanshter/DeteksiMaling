package com.aan.deteksimaling.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aan.deteksimaling.ui.AppScreen
import com.aan.deteksimaling.ui.home.CctvScreen
import com.aan.deteksimaling.ui.home.HomeScreen
import com.aan.deteksimaling.ui.home.MalingScreen

@Composable
fun HomeNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Dashboard.route
    ) {
        composable(AppScreen.Dashboard.route) {
            HomeScreen(navController)
        }

        composable(AppScreen.Dashboard.Cctv.route) {
            CctvScreen(navController = navController)
        }

        composable(AppScreen.Dashboard.Maling.route) {
            MalingScreen(navController = navController)
        }

    }
}