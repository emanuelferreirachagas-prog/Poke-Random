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
import com.example.poke_random.telas.MenuScreen
import com.example.poke_random.telas.PerguntaScreen
import com.example.poke_random.telas.SquirtleScreen
import com.example.poke_random.telas.TelaInicial
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
        startDestination = "tela_inicial"
    ) {
        composable("tela_inicial") { TelaInicial( navController = navController) }
        // 1. TELA DO PROFESSOR OAK
        // No NavHost
        composable("pergunta") {
            PerguntaScreen(navController = navController)
        }

        // 2. TELA DE SORTEIO (ROLETA)
        composable(
            route = "tela_roll",
            exitTransition = { fadeOut(animationSpec = tween(500)) }
        ) {
            TelaRoll(
                onFinished = { rotaSorteada ->
                navController.navigate(rotaSorteada) {
                    // Limpa a roleta da memória para não bugar ao voltar
                    popUpTo("tela_roll") { inclusive = true }
                }
            })
        }

        // 3. RESULTADO: CHARMANDER
        composable(
            route = "charmander",
            enterTransition = { scaleIn(initialScale = 0.8f) + fadeIn(tween(600)) }
        ) {
            CharmanderScreen(
                onSimClick = { navController.navigate("tela_roll") },
                onNaoClick = { navController.navigate("pergunta") }
            )
        }

        // 4. RESULTADO: BULBASAURO
        composable(
            route = "bulbasaur",
            enterTransition = { scaleIn(initialScale = 0.8f) + fadeIn(tween(600)) }
        ) {
            BulbasaurScreen(
                onSimClick = { navController.navigate("tela_roll") },
                onNaoClick = { navController.navigate("pergunta") }
            )
        }

        // 5. RESULTADO: SQUIRTLE
        composable(
            route = "squirtle",
            enterTransition = { scaleIn(initialScale = 0.8f) + fadeIn(tween(1600)) }
        ) {
            SquirtleScreen(
                onSimClick = { navController.navigate("tela_roll") },
                onNaoClick = { navController.navigate("pergunta") }
            )
        }

        // 6. ROTA DE MENU (Caso precise usar o botão "Não")
        composable("menu") {
            MenuScreen(navController = navController)
        }
    }
}