package com.example.poke_random.nav

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.poke_random.Telas.TelaInicial

@Composable
fun AppNavigation(){

    var navController = rememberNavController()
    NavHost(navController, startDestination = "Login",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn() },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut() }
    ) {
        composable("Login") { TelaInicial(navController = navController) }
    }
}