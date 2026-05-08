package com.wayuyu.mepatech.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentLate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wayuyu.mepatech.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(
    onTimeout: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkBackground,
                        DarkSurface
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        // 🌌 Glass Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),

            shape = RoundedCornerShape(32.dp),

            colors = CardDefaults.cardColors(
                containerColor = GlassWhite
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 50.dp, horizontal = 24.dp),

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // 🔷 APP ICON
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    PrimaryBlue,
                                    CyanAccent
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.AssignmentLate,
                        contentDescription = "App Logo",
                        tint = Color.White,
                        modifier = Modifier.size(55.dp)
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // 🔥 APP NAME
                Text(
                    text = "ReliefLink",
                    style = MaterialTheme.typography.headlineLarge,
                    color = TextWhite,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 🌍 TAGLINE
                Text(
                    text = "Connecting communities to emergency support.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextGray,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(30.dp))

                // 🔵 LOADING INDICATOR
                CircularProgressIndicator(
                    color = CyanAccent,
                    strokeWidth = 3.dp
                )
            }
        }
    }

    // ⏳ SPLASH TIMEOUT
    LaunchedEffect(Unit) {

        kotlinx.coroutines.delay(2500)

        onTimeout()
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {

    SplashScreen(
        onTimeout = {}
    )
}