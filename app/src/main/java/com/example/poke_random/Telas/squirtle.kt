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
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.poke_random.R

@Composable
fun SquirtleScreen(
    onSimClick: () -> Unit = {},
    onNaoClick: () -> Unit = {}
) {
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

        // 1. FUNDO AZUL (Água/Piscina)
        Image(
            painter = painterResource(id = R.drawable.tela_inicial_7__1_), // Substitua pelo seu fundo de água
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 2. POKÉBOLA NO TOPO (Silhueta escura)
        Image(
            painter = painterResource(id = R.drawable.oficial_2),
            contentDescription = null,
            modifier = Modifier
                .size(450.dp)
                .offset(y = (-150).dp, x = (-50).dp)
                .rotate(angle)
                .align(Alignment.TopCenter),
            alpha = 0.3f,
            colorFilter = ColorFilter.tint(Color(0xFF105B96), blendMode = BlendMode.Modulate)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            // 3. IMAGEM DO SQUIRTLE (Com efeito de água ao fundo)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(220.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.c4998971_b2a8_4000_ac87_c1457076a1d3_removebg_preview), // Imagem do Squirtle
                    contentDescription = "Squirtle",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 4. TÍTULO COM GRADIENTE METÁLICO
            Text(
                text = "Squirtle",
                style = TextStyle(
                    fontSize = 35.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Monospace,
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, Color(0xFFBDBDBD), Color(0xFF424242))
                    ),
                    shadow = Shadow(color = Color.Black, blurRadius = 10f)
                )
            )

            // 5. BADGE DO TIPO (WATER - Gradiente Azul)
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF3498DB), // Azul claro
                                Color(0xFF2980B9), // Azul médio
                                Color(0xFF1B4F72)  // Azul marinho profundo
                            )
                        ),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .border(1.dp, Color.Black.copy(alpha = 0.6f), RoundedCornerShape(15.dp))
                    .padding(horizontal = 30.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "WATER",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            // 6. DESCRIÇÃO (Texto branco com sombra preta para ler sobre a água)
            Text(
                text = "conhecido por disparar jatos de água com grande precisão e força. Sua carapaça não serve apenas para proteção, mas também reduz a resistência na água.",
                style = TextStyle(
                    fontSize = 19.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Bold,
                    shadow = Shadow(color = Color.White.copy(alpha = 0.7f), blurRadius = 3f)
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            // 7. BOTÕES SIM/NÃO (Padronizados)
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SquirtleChoiceButton(text = "Sim", onClick = onSimClick)
                SquirtleChoiceButton(text = "Não", onClick = onNaoClick)
            }

            // 8. CAIXA DE DIÁLOGO
            SquirtleDialogBox(text = "Deseja roletar seu pokemon novamente? ...")
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun SquirtleChoiceButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(130.dp)
            .height(50.dp)
            .background(Color.White, RoundedCornerShape(25.dp))
            .border(4.dp, Color(0xFF00B0FF), RoundedCornerShape(25.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color(0xFFD32F2F),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
fun SquirtleDialogBox(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color.White, RoundedCornerShape(20.dp))
            .border(5.dp, Color(0xFF00B0FF), RoundedCornerShape(20.dp))
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = 18.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Medium
        )
    }
}
@Preview
@Composable
fun SquirtleScreenPreview() {
    SquirtleScreen()
}

