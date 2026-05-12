package com.example.poke_random.telas

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.poke_random.R
import com.example.poke_random.ui.theme.PokeRandomTheme

@Composable
fun CharmanderScreen(
    modifier: Modifier = Modifier,
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

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // 1. FUNDO COM DEGRADÊ ALARANJADO (Baseado na imagem)
        Image(
            painter = painterResource(id = R.drawable.tela_inicial_5),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 2. POKÉBOLA NO TOPO (Grande e rotacionando)
        Image(
            painter = painterResource(id = R.drawable.oficial_1), // Ou o recurso da silhueta da pokébola
            contentDescription = null,
            modifier = Modifier
                .size(450.dp)
                .offset(y = (-150).dp, x = (-50).dp)
                .rotate(angle)
                .align(Alignment.TopCenter),
            alpha = 0.3f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            // 3. IMAGEM DO CHARMANDER (Com contorno branco estilo adesivo)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(220.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ellipse_6), // Certifique-se que esta imagem tem o brilho/fogo ao fundo
                    contentDescription = "Charmander",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 4. NOME COM GRADIENTE E SOMBRA (Estilo "Charmander")
            Text(
                text = stringResource(id = R.string.pokemon_name_charmander),
                style = TextStyle(
                fontSize = 40.sp, // Aumentado para destacar o gradiente
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF000000),       // Topo das letras branco
                        Color(0xFF310606),       // Meio das letras cinza
                        Color(0xFF000000),       // Base das letras branco
                        Color(0xFF000000),       // Laranja mais claro no topo
                    )
                ),
                shadow = Shadow(
                    color = Color.Black,
                    blurRadius = 10f
                )
            )
            )

            // 5. BADGE DO TIPO (Com Gradiente estilo a imagem)
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFE67E22), // Laranja mais claro no topo
                                Color(0xFFD35400), // Laranja médio
                                Color(0xFF873600)  // Marrom/Laranja escuro na base
                            )
                        ),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .border(1.dp, Color.Black.copy(alpha = 0.6f), RoundedCornerShape(15.dp))
                    .padding(horizontal = 25.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.pokemon_type_fire).uppercase(),
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            // 6. DESCRIÇÃO (Texto com sombra para legibilidade no fundo vibrante)
            Text(
                text = stringResource(id = R.string.pokemon_desc_charmander),
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

            // 7. BOTÕES "SIM" E "NÃO" (Bordas azuis arredondadas)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CharmanderChoiceButton(text = "Sim", onClick = onSimClick)
                CharmanderChoiceButton(text = "Não", onClick = onNaoClick)
            }

            // 8. CAIXA DE DIÁLOGO INFERIOR
            CharmanderDialogBox(
                text = "Deseja roletar seu pokemon novamente? ..."
            )
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun CharmanderChoiceButton(
    text: String,
    onClick: () -> Unit
) {
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
            color = Color(0xFFD32F2F), // Vermelho
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
fun CharmanderDialogBox(
    text: String
) {
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
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CharmanderScreenPreview() {
    PokeRandomTheme {
        CharmanderScreen()
    }
}