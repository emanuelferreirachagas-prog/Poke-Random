package com.example.poke_random.telas

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.poke_random.R
import com.example.poke_random.ui.theme.PokeRandomTheme
import kotlinx.coroutines.yield
import kotlinx.coroutines.delay

@Composable
fun BulbasaurScreen(
    onSimClick: () -> Unit = {},
    onNaoClick: () -> Unit = {}
) {
    // Adicionado para otimização de transição
    LaunchedEffect(Unit) {
        yield()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "Rotate")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "angle"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. FUNDO VERDE (Substitua R.drawable.fundo_verde pelo seu recurso de imagem de folhas/verde)
        Image(
            painter = painterResource(id = R.drawable.tela_inicial_6__1_), // Use seu recurso de fundo verde aqui
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 2. POKÉBOLA NO TOPO (Agora com tonalidade esverdeada/escura conforme imagem)
        Image(
            painter = painterResource(id = R.drawable.d6c8052634e93ac1f777a3e38b2ebf8a_removebg_preview),
            contentDescription = null,
            modifier = Modifier
                .size(450.dp)
                .offset(y = (-100).dp, x = (-50).dp)
                .rotate(angle)
                .align(Alignment.TopCenter),
            alpha = 0.4f,
            colorFilter = ColorFilter.tint(Color(0xFF09330E), blendMode = BlendMode.Modulate)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            // 3. IMAGEM DO BULBASAURO
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(220.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bulba_removebg_preview), // Recurso da imagem do Bulbasauro
                    contentDescription = "Bulbasauro",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 4. TITULO COM GRADIENTE (Metálico)
            Text(
                text = "Bulbasaur",
                style = TextStyle(
                    fontSize = 35.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Monospace,
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, Color(0xFFBDBDBD), Color(0xFF4AFF50))
                    ),
                    shadow = Shadow(color = Color.Black, blurRadius = 10f)
                )
            )

            // 5. BADGES DE TIPO (GRASS e POISON)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // Badge Grass (Verde)
                TypeBadge(text = "GRASS", gradientColors = listOf(Color(0xFF2ECC71), Color(0xFF27AE60), Color(0xFF1B5E20)))
                // Badge Poison (Roxo)
                TypeBadge(text = "POISON", gradientColors = listOf(Color(0xFF9B59B6), Color(0xFF8E44AD), Color(0xFF4A235A)))
            }

            Spacer(modifier = Modifier.height(15.dp))

            // 6. DESCRIÇÃO
            Text(
                text = "conhecido como o primeiro monstro na Pokédex Nacional. Ele possui um bulbo verde nas costas que absorve luz solar e armazena energia",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 26.sp,
                    fontWeight = FontWeight.Bold,
                    shadow = Shadow(color = Color.White.copy(alpha = 0.5f), blurRadius = 2f)
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            // 7. BOTÕES SIM/NÃO
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ChoiceButton(text = "Sim", onClick = onSimClick)
                ChoiceButton(text = "Não", onClick = onNaoClick)
            }

            // 8. CAIXA DE DIÁLOGO
            DialogBox(text = "Deseja roletar seu pokemon novamente? ...")
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
fun TypeBadge(text: String, gradientColors: List<Color>) {
    Box(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .background(Brush.verticalGradient(gradientColors), RoundedCornerShape(15.dp))
            .border(1.dp, Color.Black.copy(alpha = 0.6f), RoundedCornerShape(15.dp))
            .padding(horizontal = 20.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
fun ChoiceButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(135.dp)
            .height(55.dp)
            .background(Color.White, RoundedCornerShape(30.dp))
            .border(4.dp, Color(0xFF00B0FF), RoundedCornerShape(30.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.Red,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
fun DialogBox(text: String) {
    var displayedText by remember { mutableStateOf("") }

    LaunchedEffect(text) {
        delay(500)
        text.forEachIndexed { index, _ ->
            displayedText = text.substring(0, index + 1)
            delay(40)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .background(Color.White, RoundedCornerShape(20.dp))
            .border(6.dp, Color(0xFF00B0FF), RoundedCornerShape(20.dp))
            .padding(20.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = displayedText,
            color = Color.Black,
            fontSize = 20.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            lineHeight = 28.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BulbasaurScreenPreview() {
    PokeRandomTheme {
        BulbasaurScreen()
    }
}
