package com.example.poke_random.telas

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.poke_random.R
import com.example.poke_random.ui.theme.PokeRandomTheme
import kotlinx.coroutines.delay

@Composable
fun MenuScreen(navController: NavController, modifier: Modifier = Modifier) {
    // 1. Estado para controlar quando a animação começa
    var startFade by remember { mutableStateOf(false) }

    // 2. Animação da opacidade (vai de 1.0/Preto para 0.0/Transparente)
    val blackOverlayOpacity by animateFloatAsState(
        targetValue = if (startFade) 0f else 1f,
        animationSpec = tween(durationMillis = 5000), // 5 segundos de duração
        label = "FadeInMenu"
    )

    // Inicia a animação com um pequeno delay para garantir que o hardware processou a tela
    LaunchedEffect(Unit) {
        kotlinx.coroutines.yield() // Espera o processamento inicial
        startFade = true
    }

    // Animação da rotação da Pokébola
    val infiniteTransition = rememberInfiniteTransition(label = "PokeBallRotation")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "angle"
    )
    
    // Texto que será exibido
    val fullText = stringResource(id = R.string.oak_dialog_text)

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            // Pokébola Girando
            PokeBallSilhouette(
                modifier = Modifier
                    .size(500.dp)
                    .align(Alignment.TopStart)
                    .offset(x = (-100).dp, y = (-100).dp)
                    .rotate(angle)
            )

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

            // Caixa de Diálogo com callback de finalização
            OakDialogBox(
                fullText = fullText,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 60.dp)
                    .padding(horizontal = 24.dp),
                onTextFinished = {
                    // Navega para a próxima tela (ex: "pergunta")
                    // O popupTo garante que o usuário não volte para o menu ao clicar em "voltar"
                    navController.navigate("pergunta") {
                        popUpTo("menu") { inclusive = true }
                    }
                }
            )
        }

        // Camada de Fade
        if (blackOverlayOpacity > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = blackOverlayOpacity))
            )
        }
    }
}

@Composable
fun OakDialogBox(
    fullText: String, 
    modifier: Modifier = Modifier, 
    onTextFinished: () -> Unit
) {
    var displayedText by remember { mutableStateOf("") }

    LaunchedEffect(fullText) {
        // 1. Efeito de Digitação
        fullText.forEachIndexed { index, _ ->
            displayedText = fullText.substring(0, index + 1)
            delay(50) 
        }
        
        // 2. Pausa para leitura após terminar
        delay(1500) 

        // 3. Chama a função de navegação
        onTextFinished()
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            // Fundo branco com cantos arredondados
            .background(Color.White, RoundedCornerShape(20.dp))
            // Borda azul vibrante grossa (Padronizada com as outras telas)
            .border(
                width = 6.dp,
                color = Color(0xFF00B0FF), // Azul vibrante padronizado
                shape = RoundedCornerShape(20.dp)
            )
            .padding(20.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = displayedText,
            color = Color.Black,
            fontSize = 22.sp, // Tamanho ajustado para legibilidade
            fontFamily = FontFamily.Monospace, // Fonte estilo retro/pixel
            fontWeight = FontWeight.Bold, // Texto em negrito como na imagem
            lineHeight = 30.sp // Espaçamento entre linhas
        )
    }
}

@Composable
fun PokeBallSilhouette(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val radius = size.minDimension / 2
        val center = Offset(size.width / 2, size.height / 2)
        val strokeWidth = 30f

        // Círculo principal (Sombra)
        drawCircle(
            color = Color(0xFF1A1A1A),
            radius = radius,
            center = center,
            style = Fill
        )

        // Borda
        drawCircle(
            color = Color(0xFF0D0D0D),
            radius = radius,
            center = center,
            style = Stroke(width = strokeWidth)
        )

        // Linha Central
        drawLine(
            color = Color(0xFF0D0D0D),
            start = Offset(center.x - radius, center.y),
            end = Offset(center.x + radius, center.y),
            strokeWidth = strokeWidth
        )

        // Botão Central (Camadas)
        drawCircle(
            color = Color(0xFF0D0D0D),
            radius = radius * 0.25f,
            center = center,
            style = Fill
        )
        drawCircle(
            color = Color(0xFF1A1A1A),
            radius = radius * 0.15f,
            center = center,
            style = Fill
        )
        drawCircle(
            color = Color(0xFF0D0D0D),
            radius = radius * 0.08f,
            center = center,
            style = Fill
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    PokeRandomTheme {
        MenuScreen(navController = rememberNavController())
    }
}
