package com.example.poke_random.nav

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.poke_random.telas.BulbasaurScreen
import com.example.poke_random.telas.CharmanderScreen
import com.example.poke_random.telas.PerguntaScreen
import com.example.poke_random.telas.SquirtleScreen
import com.example.poke_random.telas.TelaRoll

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    SetupNavGraph(navController = navController)
}

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "tela_pergunta" // Sua tela inicial
    ) {
        composable("tela_pergunta") {
            // Sua tela de início onde clica em "Sim"
            PerguntaScreen(
                navController = navController,
                onSimClick = { navController.navigate("tela_roll") },
                onNaoClick = { /* Ação para não */ }
            )
        }

        composable(
            route = "tela_roll",
            exitTransition = { fadeOut(animationSpec = tween(500)) } // Esmaece ao sair
        ) {
            TelaRoll(onFinished = { rota ->
                navController.navigate(rota) {
                    // Limpa a roleta da memória para não voltar nela ao apertar "back"
                    popUpTo("tela_roll") { inclusive = true }
                }
            })
        }

        // --- AS TELAS DE RESULTADO COM ANIMAÇÃO DE ENTRADA ---
        
        composable(
            route = "charmander",
            enterTransition = { 
                scaleIn(initialScale = 0.8f) + fadeIn(animationSpec = tween(600)) 
            }
        ) {
            CharmanderScreen(
                onSimClick = { navController.navigate("tela_roll") },
                onNaoClick = { navController.navigate("tela_pergunta") }
            )
        }

        composable(
            route = "bulbasaur",
            enterTransition = { 
                scaleIn(initialScale = 0.8f) + fadeIn(animationSpec = tween(600)) 
            }
        ) {
            BulbasaurScreen(
                onSimClick = { navController.navigate("tela_roll") },
                onNaoClick = { navController.navigate("tela_pergunta") }
            )
        }

        composable(
            route = "squirtle",
            enterTransition = { 
                scaleIn(initialScale = 0.8f) + fadeIn(animationSpec = tween(600)) 
            }
        ) {
            SquirtleScreen(
                onSimClick = { navController.navigate("tela_roll") },
                onNaoClick = { navController.navigate("tela_pergunta") }
            )
        }
    }
}
