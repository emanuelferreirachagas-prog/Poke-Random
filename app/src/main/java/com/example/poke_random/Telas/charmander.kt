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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import kotlinx.coroutines.yield
import kotlinx.coroutines.delay

@Composable
fun CharmanderScreen(
    modifier: Modifier = Modifier,
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
            painter = painterResource(id = R.drawable.d6c8052634e93ac1f777a3e38b2ebf8a_removebg_preview), // Ou o recurso da silhueta da pokébola
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
                .padding(horizontal = 24.dp), // Ajustado padding horizontal
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            // 3. IMAGEM DO CHARMANDER (Com contorno branco estilo adesivo)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(220.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gemini_generated_image_3v14hs3v14hs3v14_removebg_preview), // Certifique-se que esta imagem tem o brilho/fogo ao fundo
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
                        colors = listOf(Color.White, Color(0xFFBDBDBD), Color(0xFF043556))
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
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CharmanderChoiceButton(text = "Sim", onClick = onSimClick)
                CharmanderChoiceButton(text = "Não", onClick = onNaoClick)
            }

            // 8. CAIXA DE DIÁLOGO INFERIOR
            CharmanderDialogBox(
                text = "Deseja roletar seu pokemon novamente? ..."
            )
            Spacer(modifier = Modifier.height(60.dp))
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
            .width(135.dp)
            .height(55.dp)
            .background(Color.White, RoundedCornerShape(30.dp))
            .border(4.dp, Color(0xFF00B0FF), RoundedCornerShape(30.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.Red, // Vermelho
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
fun CharmanderScreenPreview() {
    PokeRandomTheme {
        CharmanderScreen()
    }
}