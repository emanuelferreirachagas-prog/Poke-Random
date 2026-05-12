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
import androidx.compose.ui.draw.scale
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
import androidx.navigation.NavController
import com.example.poke_random.R
import kotlinx.coroutines.delay

@Composable
fun PerguntaScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    onSimClick: () -> Unit = {},
    onNaoClick: () -> Unit = {}
) {
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

    LaunchedEffect(Unit) { 
        startFade = true 
    }

    Box(modifier = modifier.fillMaxSize().background(Color.Black)) {

        // --- FUNDO: Pokébola Girando ---
        PokeBallSilhouetteBackground(
            modifier = Modifier
                .size(600.dp)
                .offset(x = (-150).dp, y = (-80).dp)
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
                    .fillMaxHeight(0.85f)
                    .fillMaxWidth()
                    .scale(1.2f) 
                    .align(Alignment.Center)
                    .offset(y = (-20).dp),
                contentScale = ContentScale.Fit
            )

            // Botões de Escolha
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 180.dp)
            ) {
                AnimatedVisibility(
                    visible = showButtons,
                    enter = fadeIn(animationSpec = tween(500))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        PokemonChoiceButton(text = "Sim", onClick = onSimClick)
                        PokemonChoiceButton(text = "Não", onClick = onNaoClick)
                    }
                }
            }

            // Caixa de Diálogo
            TypewriterDialogBox(
                text = stringResource(id = R.string.dialog_reroll_pokemon_novamente),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 50.dp)
                    .padding(horizontal = 20.dp),
                onFinished = { showButtons = true }
            )
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
            .height(140.dp)
            .background(Color.White, RoundedCornerShape(15.dp))
            .border(5.dp, Color(0xFF29B6F6), RoundedCornerShape(15.dp))
            .padding(20.dp)
    ) {
        Text(
            text = displayedText,
            color = Color.Black,
            fontSize = 22.sp,
            fontFamily = FontFamily.Monospace,
            lineHeight = 28.sp
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
            .border(4.dp, Color(0xFF29B6F6), RoundedCornerShape(30.dp))
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
