package com.example.poke_random.Telas


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.poke_random.R
import com.example.poke_random.ui.theme.PokeRandomTheme

import android.graphics.Paint
import android.graphics.Path
import android.media.browse.MediaBrowser
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.delay


@Composable
fun TelaInicial(modifier: Modifier = Modifier, navController: NavController? = null) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(
                RawResourceDataSource.buildRawResourceUri(R.raw.pokemon_music)
            )
            setMediaItem(mediaItem)
            prepare()
            repeatMode = Player.REPEAT_MODE_ALL

            // Configuração de áudio para gerenciar o foco automaticamente
            val audioAttributes = androidx.media3.common.AudioAttributes.Builder()
                .setUsage(androidx.media3.common.C.USAGE_GAME)
                .setContentType(androidx.media3.common.C.AUDIO_CONTENT_TYPE_MUSIC)
                .build()
            setAudioAttributes(audioAttributes, true)
        }
    }

    LaunchedEffect(Unit) {
        var v = 0f
        exoPlayer.volume = v
        while (v < 0.1f) { // Sobe até 30%
            delay(100)    // Espera 100ms
            v += 0.01f    // Incrementa o volume
            exoPlayer.volume = v
        }
    }

    // 2. Gerenciamento de Efeitos Sonoros (SoundPool)
    val soundPool = remember {
        SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            ).build()
    }
    val soundId = remember { soundPool.load(context, R.raw.click_sound, 1) }

    // Gerenciamento do ciclo de vida da música e efeitos sonoros
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> exoPlayer.pause()
                Lifecycle.Event.ON_RESUME -> exoPlayer.play()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        
        // Inicia a música se estiver em estado RESUME
        if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            exoPlayer.play()
        }

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            exoPlayer.release()
            soundPool.release()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Background
        Image(
            painter = painterResource(id = R.drawable.iphone_17___8__1_),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título Curvado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentAlignment = Alignment.Center
            ) {
                CurvedOutlinedText(
                    text = stringResource(id = R.string.title_escolha),
                    offsetY = 0.dp
                )
                CurvedOutlinedText(
                    text = stringResource(id = R.string.title_aleatoria),
                    offsetY = 70.dp
                )
            }

            Spacer(modifier = Modifier.height(230.dp))

            // Botão Iniciar
            PokemonButton(
                text = stringResource(id = R.string.button_start),
                onClick = {
                    soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                    // navController?.navigate("proxima_tela")
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botão Opções
            PokemonButton(
                text = stringResource(id = R.string.button_options),
                onClick = {
                    soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                }
            )
        }
    }
}

@Composable
fun CurvedOutlinedText(
    text: String,
    modifier: Modifier = Modifier,
    offsetY: Dp = 0.dp
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .offset(y = offsetY)
    ) {
        val canvasWidth = size.width
        val arcWidth = canvasWidth * 0.8f
        val left = (canvasWidth - arcWidth) / 2
        val right = left + arcWidth

        val path = android.graphics.Path().apply {
            addArc(left, 100f, right, 500f, 200f, 140f)
        }

        drawIntoCanvas { canvas ->
            val outlinePaint = Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 120f
                style = Paint.Style.STROKE
                strokeWidth = 18f
                strokeJoin = Paint.Join.ROUND
                textAlign = Paint.Align.CENTER
                isFakeBoldText = true
            }

            val fillPaint = Paint().apply {
                color = android.graphics.Color.WHITE
                textSize = 120f
                style = Paint.Style.FILL
                textAlign = Paint.Align.CENTER
                isFakeBoldText = true
            }

            canvas.nativeCanvas.drawTextOnPath(text, path, 0f, 0f, outlinePaint)
            canvas.nativeCanvas.drawTextOnPath(text, path, 0f, 0f, fillPaint)
        }
    }
}

@Composable
fun PokemonButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(300.dp)
            .height(70.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.bot_es),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = text,
            color = Color.Black,
            fontSize = 32.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Normal
        )
    }
}
@Preview(showBackground = true)
@Composable
fun TelaInicialPreview() {
    PokeRandomTheme {
        TelaInicial()
    }
}
