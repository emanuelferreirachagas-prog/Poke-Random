package com.example.poke_random.telas

import android.media.AudioAttributes
import android.media.SoundPool
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.poke_random.R
import kotlinx.coroutines.delay
import kotlin.random.Random

// Dados dos Pokémons
data class PokemonData(
    val nome: String,
    val imagem: Int,
    val corFundo: Color,
    val route: String
)

@Composable
fun TelaRoll(onFinished: (String) -> Unit) {
    val context = LocalContext.current
    
    // 1. SOUNDPOOL (Ultra leve para não travar a navegação)
    val soundPool = remember {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        SoundPool.Builder().setMaxStreams(1).setAudioAttributes(attributes).build()
    }

    DisposableEffect(Unit) {
        onDispose { soundPool.release() }
    }

    val lista = remember {
        listOf(
            PokemonData("Charmander", R.drawable.gemini_generated_image_3v14hs3v14hs3v14_removebg_preview, Color(0xFFFF7043), "charmander"),
            PokemonData("Bulbasauro", R.drawable.bulba_removebg_preview, Color(0xFF66BB6A), "bulbasaur"),
            PokemonData("Squirtle", R.drawable.c4998971_b2a8_4000_ac87_c1457076a1d3_removebg_preview, Color(0xFF42A5F5), "squirtle")
        )
    }

    var indexAtual by remember { mutableIntStateOf(0) }
    
    // Animação de rotação da Pokébola de fundo
    val angle by rememberInfiniteTransition(label = "").animateFloat(
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing)), label = ""
    )

    // 2. LÓGICA DA ROLETA COM NAVEGAÇÃO FINAL
    LaunchedEffect(Unit) {
        delay(500) // Pequena pausa para carregar a tela
        
        var tempoDelay = 50L
        val totalDeTrocas = 35 // Aumentado um pouco para dar mais emoção

        repeat(totalDeTrocas) { i ->
            // Sorteia um novo index aleatório
            indexAtual = Random.nextInt(lista.size)

            // Efeito de frenagem (vai ficando mais lento)
            if (i > 25) tempoDelay += 60L
            if (i > 30) tempoDelay += 120L
            
            delay(tempoDelay)
        }

        // --- MOMENTO FINAL ---
        // Pegamos a rota do Pokémon que parou no sorteio
        val destinoFinal = lista[indexAtual].route
        
        delay(1000) // Pausa de 1 segundo para o usuário ver o Pokémon sorteado
        
        // Dispara a navegação
        onFinished(destinoFinal)
    }

    // 3. UI (Padronizada)
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        
        // Fundo
        Image(
            painter = painterResource(id = R.drawable.oficial_2),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.4f
        )
        
        // Brilho da cor do Pokémon atual
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(lista[indexAtual].corFundo.copy(alpha = 0.4f), Color.Transparent),
                        radius = 1200f
                    )
                )
        )

        // Pokébola giratória
        Image(
            painter = painterResource(id = R.drawable.d6c8052634e93ac1f777a3e38b2ebf8a_removebg_preview),
            contentDescription = null,
            modifier = Modifier
                .size(500.dp)
                .align(Alignment.Center)
                .offset(y = (-30).dp)
                .rotate(angle),
            alpha = 0.1f
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Imagem do Pokemon Gigante
            Image(
                painter = painterResource(id = lista[indexAtual].imagem),
                contentDescription = null,
                modifier = Modifier.size(300.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Nome do Pokémon
            Text(
                text = lista[indexAtual].nome.uppercase(),
                style = TextStyle(
                    fontSize = 42.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Monospace,
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, Color.LightGray, Color.Gray)
                    ),
                    shadow = Shadow(Color.Black, blurRadius = 15f)
                )
            )

            Text(
                "SORTEANDO...",
                color = Color.White.copy(alpha = 0.6f),
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp,
                letterSpacing = 4.sp
            )
        }
    }
}
