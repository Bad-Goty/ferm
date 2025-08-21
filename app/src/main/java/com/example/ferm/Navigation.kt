package com.example.ferm

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ferm.presentation.screen.MainScreen
import com.example.ferm.presentation.screen.StatisticsScreen

@Composable
fun NavScreen(modifier: Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "statistics"
    ) {
        composable("home") { MainScreen(modifier = modifier, navController = navController) }
        composable("statistics") { StatisticsScreen(modifier = modifier, values = listOf(10f, 30f, 20f, 50f)) }
    }

}