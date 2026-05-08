package com.wayuyu.mepatech.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wayuyu.mepatech.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLogin: (String, String) -> Unit,
    onForgotPassword: () -> Unit,
    onSendOtp: (String) -> Unit,
    onVerifyOtp: (String) -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var phone by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }

    var otpSent by remember { mutableStateOf(false) }

    Scaffold(

        containerColor = DarkBackground,

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "ReliefLink",
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
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            // 🌌 HERO SECTION
            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(28.dp),

                colors = CardDefaults.cardColors(
                    containerColor = GlassWhite
                )
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text(
                        text = "Welcome Back",
                        style = MaterialTheme.typography.headlineMedium,
                        color = TextWhite,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Securely connect to emergency relief services.",
                        color = TextGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // 🔷 EMAIL LOGIN SECTION
            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(24.dp),

                colors = CardDefaults.cardColors(
                    containerColor = GlassWhite
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Login with Email",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextWhite
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },

                        label = {
                            Text("Email Address")
                        },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(18.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },

                        label = {
                            Text("Password")
                        },

                        modifier = Modifier.fillMaxWidth(),

                        visualTransformation = PasswordVisualTransformation(),

                        shape = RoundedCornerShape(18.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            onLogin(email, password)
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
                            text = "Login",
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // 🔵 OTP LOGIN SECTION
            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(24.dp),

                colors = CardDefaults.cardColors(
                    containerColor = GlassWhite
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Phone Authentication",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextWhite
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },

                        label = {
                            Text("Phone Number")
                        },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(18.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            otpSent = true
                            onSendOtp(phone)
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),

                        shape = RoundedCornerShape(18.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = CyanAccent
                        )
                    ) {

                        Text(
                            text = "Send OTP",
                            color = Color.White
                        )
                    }

                    if (otpSent) {

                        Spacer(modifier = Modifier.height(18.dp))

                        OutlinedTextField(
                            value = otp,
                            onValueChange = { otp = it },

                            label = {
                                Text("Enter OTP")
                            },

                            modifier = Modifier.fillMaxWidth(),

                            shape = RoundedCornerShape(18.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                onVerifyOtp(otp)
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
                                text = "Verify OTP",
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🔽 FORGOT PASSWORD
            TextButton(
                onClick = onForgotPassword
            ) {

                Text(
                    text = "Forgot Password?",
                    color = CyanAccent
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {

    LoginScreen(
        onLogin = { _, _ -> },
        onForgotPassword = {},
        onSendOtp = {},
        onVerifyOtp = {}
    )
}