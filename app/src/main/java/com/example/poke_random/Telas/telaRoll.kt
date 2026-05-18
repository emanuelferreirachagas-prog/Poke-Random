package com.example.poke_random.telas

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.poke_random.R
import com.example.poke_random.ui.theme.PokeRandomTheme
import kotlin.random.Random
import kotlinx.coroutines.delay

// Modelo simples para a roleta
data class PokemonData(
    val nome: String,
    val imagem: Int,
    val corFundo: Color,
    val route: String
)

@Composable
fun TelaRoll(
    onFinished: (String) -> Unit
) {
    val listaPokemons = listOf(
        PokemonData("Charmander", R.drawable.ellipse_6, Color(0xFFFF7043), "charmander"),
        PokemonData("Bulbasauro", R.drawable.bulba_removebg_preview, Color(0xFF66BB6A), "bulbasaur"),
        PokemonData("Squirtle", R.drawable.c4998971_b2a8_4000_ac87_c1457076a1d3_removebg_preview, Color(0xFF42A5F5), "squirtle")
    )

    var indexAtual by remember { mutableIntStateOf(0) }
    val pokemonSorteado = listaPokemons[indexAtual]

    // Animação da Pokébola de fundo
    val infiniteTransition = rememberInfiniteTransition(label = "RotateBackground")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing), 
            repeatMode = RepeatMode.Restart
        ), label = "angle"
    )

    // LÓGICA DE SORTEIO ALEATÓRIO
    LaunchedEffect(Unit) {
        var tempoDelay = 60L 
        val totalDeTrocas = 35 
        
        repeat(totalDeTrocas) { i ->
            var proximoIndex: Int
            do {
                proximoIndex = Random.nextInt(listaPokemons.size)
            } while (proximoIndex == indexAtual)
            
            indexAtual = proximoIndex

            if (i > 25) tempoDelay += 40L
            if (i > 30) tempoDelay += 100L // Ficando bem lento no final

            delay(tempoDelay)
        }

        // --- O SEGREDO DO IMPACTO ---
        // Dá uma pausa curta de 800ms para o usuário ver quem ganhou
        delay(800)

        // Dispara o callback que o NavHost vai usar para animar a entrada da nova tela
        onFinished(pokemonSorteado.route)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 1. FUNDO DINÂMICO (Muda conforme o Pokémon que está passando)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 0.dp) // Ocupa tudo
        ) {
            Image(
                painter = painterResource(id = R.drawable.oficial_2), // Seu fundo padrão
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.8f
            )
            // Overlay de cor para o background mudar com o pokemon
            Box(modifier = Modifier.fillMaxSize().padding().background(pokemonSorteado.corFundo.copy(alpha = 0.5f)))
        }

        // 2. POKÉBOLA ROTACIONANDO GIGANTE (Ajustada para ficar atrás do ícone)
        Image(
            painter = painterResource(id = R.drawable.d6c8052634e93ac1f777a3e38b2ebf8a_removebg_preview),
            contentDescription = null,
            modifier = Modifier
                .size(600.dp)
                .align(Alignment.TopCenter) // Mudamos para o topo para poder controlar o offset
                .offset(y = 60.dp)           // Sobe a pokébola (diminua o valor de Y para subir mais)
                .rotate(angle),
            alpha = 0.2f
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 3. IMAGEM DO POKEMON GIGANTE (Troca rápido)
            Image(
                painter = painterResource(id = pokemonSorteado.imagem),
                contentDescription = null,
                modifier = Modifier.size(350.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(30.dp))

            // 4. NOME DO POKEMON (Troca rápido com gradiente)
            Text(
                text = pokemonSorteado.nome.uppercase(),
                style = TextStyle(
                    fontSize = 45.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Monospace,
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, Color(0xFFBDBDBD), Color(0xFF424242))
                    ),
                    shadow = Shadow(color = Color.Black, blurRadius = 15f)
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "SORTEANDO...",
                color = Color.White,
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaRollPreview() {
    PokeRandomTheme {
        TelaRoll(onFinished = {})
    }
}
