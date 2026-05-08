package com.wayuyu.mepatech.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import com.wayuyu.mepatech.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPassword(
    onBackToLogin: () -> Unit
) {

    var email by remember { mutableStateOf("") }

    Scaffold(

        containerColor = DarkBackground,

        topBar = {
            TopAppBar(

                title = {
                    Text(
                        text = "Forgot Password",
                        color = TextWhite
                    )
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBackground
                )
            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            DarkBackground,
                            DarkSurface
                        )
                    )
                )
                .padding(paddingValues)
                .padding(24.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // 🌌 GLASS CARD
            Card(

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(28.dp),

                colors = CardDefaults.cardColors(
                    containerColor = GlassWhite
                )

            ) {

                Column(
                    modifier = Modifier.padding(24.dp),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Reset Password",
                        style = MaterialTheme.typography.headlineMedium,
                        color = TextWhite,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Enter your email address to receive a password reset link.",
                        color = TextGray
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },

                        label = {
                            Text("Email Address")
                        },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(18.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(

                        onClick = {

                            FirebaseAuth.getInstance()
                                .sendPasswordResetEmail(email)
                                .addOnSuccessListener {

                                    println("RESET EMAIL SENT")
                                }
                                .addOnFailureListener { error ->

                                    println("RESET ERROR: ${error.message}")
                                }
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),

                        shape = RoundedCornerShape(18.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue
                        )

                    ) {

                        Text(
                            text = "Send Reset Link",
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    TextButton(
                        onClick = onBackToLogin
                    ) {

                        Text(
                            text = "Back to Login",
                            color = CyanAccent
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordPreview() {

    ForgotPassword(
        onBackToLogin = {}
    )
}