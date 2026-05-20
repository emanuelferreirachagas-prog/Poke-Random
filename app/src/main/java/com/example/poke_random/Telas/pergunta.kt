package com.example.poke_random.telas

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.poke_random.R
import com.example.poke_random.ui.theme.PokeRandomTheme
import kotlinx.coroutines.delay

@Composable
fun PerguntaScreen(
    navController: NavController, // Adicione o NavController aqui
    modifier: Modifier = Modifier,
    onNaoClick: () -> Unit = {}
){
    // 1. Estados de Animação e Controle
    var startFade by remember { mutableStateOf(false) }
    var showButtons by remember { mutableStateOf(false) }

    val blackOverlayOpacity by animateFloatAsState(
        targetValue = if (startFade) 0f else 1f,
        animationSpec = tween(1000),
        label = "FadeIn"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "Rotate")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "angle"
    )

    // Inicia a animação com um pequeno delay para garantir que o hardware processou a tela
    LaunchedEffect(Unit) {
        kotlinx.coroutines.yield() // Espera o processamento inicial
        startFade = true
    }

    Box(modifier = modifier.fillMaxSize().background(Color.Black)) {

        // --- FUNDO: Pokébola Girando ---
        PokeBallSilhouetteBackground(
            modifier = Modifier
                .size(500.dp)
                .offset(x = (-100).dp, y = (-100).dp)
                .rotate(angle)
                .align(Alignment.TopStart)
        )

        // --- CONTEÚDO ---
        Box(modifier = Modifier.fillMaxSize()) {
            
            // Professor Oak
            Image(
                painter = painterResource(id = R.drawable.professorv2_1),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .offset(y = (-10).dp),
                contentScale = ContentScale.Fit
            )

            // Bottom Content: Botões de Escolha e Caixa de Diálogo
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 60.dp)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = showButtons,
                    enter = fadeIn(animationSpec = tween(500))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // BOTÃO SIM SIMPLIFICADO
                        PokemonChoiceButton(
                            text = "Sim", 
                            onClick = { navController.navigate("tela_roll") } 
                        )

                        // BOTÃO NÃO
                        PokemonChoiceButton(
                            text = "Não", 
                            onClick = {navController.navigate("tela_inicial")}
                        )
                    }
                }

                TypewriterDialogBox(
                    text = stringResource(id = R.string.dialog_reroll_pokemon_novamente),
                    onFinished = { showButtons = true }
                )
            }
        }

        // Camada de Fade
        if (blackOverlayOpacity > 0f) {
            Box(Modifier.fillMaxSize().background(Color.Black.copy(alpha = blackOverlayOpacity)))
        }
    }
}

@Composable
fun TypewriterDialogBox(
    text: String, 
    modifier: Modifier = Modifier,
    onFinished: () -> Unit = {}
) {
    var displayedText by remember { mutableStateOf("") }

    LaunchedEffect(text) {
        delay(800)
        text.forEachIndexed { index, _ ->
            displayedText = text.substring(0, index + 1)
            delay(40)
        }
        onFinished()
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color.White, RoundedCornerShape(20.dp))
            .border(
                width = 6.dp,
                color = Color(0xFF00B0FF),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(20.dp)
    ) {
        Text(
            text = displayedText,
            color = Color.Black,
            fontSize = 22.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp
        )
    }
}

@Composable
fun PokemonChoiceButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(130.dp)
            .height(55.dp)
            .background(Color.White, RoundedCornerShape(30.dp))
            .border(4.dp, Color(0xFF00B0FF), RoundedCornerShape(30.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.Red,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
fun PokeBallSilhouetteBackground(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val radius = size.minDimension / 2
        val center = Offset(size.width / 2, size.height / 2)
        drawCircle(Color(0xFF121212), radius, center, style = Fill)
        drawCircle(Color(0xFF080808), radius, center, style = Stroke(25f))
        drawLine(Color(0xFF080808), Offset(center.x - radius, center.y), Offset(center.x + radius, center.y), strokeWidth = 25f)
        drawCircle(Color(0xFF080808), radius * 0.22f, center, style = Fill)
        drawCircle(Color(0xFF121212), radius * 0.12f, center, style = Fill)
    }
}

@Preview(showBackground = true)
@Composable
fun PerguntaScreenPreview() {
    PokeRandomTheme {
        PerguntaScreen(
            navController = rememberNavController(),
            onNaoClick = {}
        )
    }
}
