package com.wayuyu.mepatech.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.wayuyu.mepatech.ui.screens.auth.*
import com.wayuyu.mepatech.ui.screens.dashboard.*
import com.wayuyu.mepatech.ui.screens.onboarding.OnboardingScreen
import com.wayuyu.mepatech.ui.screens.profiles.*
import com.wayuyu.mepatech.ui.screens.splash.SplashScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH,
) {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseDatabase.getInstance().reference
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(ROUTE_SPLASH) {
            SplashScreen(
                onTimeout = {
                    navController.navigate(ROUTE_ONBOARDING1)
                }
            )
        }

        composable(ROUTE_FORGOTPASSWORD) {
            ForgotPassword(
                onBackToLogin = { navController.popBackStack() }
            )
        }

        composable(ROUTE_LOGIN) {

            LoginScreen(

                onLogin = { email, password ->

                    auth.signInWithEmailAndPassword(
                        email.trim(),
                        password.trim()
                    )

                        .addOnSuccessListener { result ->

                            println("LOGIN SUCCESS")

                            val userId =
                                result.user?.uid
                                    ?: return@addOnSuccessListener

                            db.child("users")
                                .child(userId)
                                .child("role")
                                .get()

                                .addOnSuccessListener { snapshot ->

                                    val role =
                                        snapshot.getValue(String::class.java)

                                    if (role == "admin") {

                                        navController.navigate(ROUTE_ADMIN) {

                                            popUpTo(ROUTE_LOGIN) {
                                                inclusive = true
                                            }
                                        }

                                    } else {

                                        navController.navigate(ROUTE_USER) {

                                            popUpTo(ROUTE_LOGIN) {
                                                inclusive = true
                                            }
                                        }
                                    }
                                }

                                .addOnFailureListener { error ->

                                    Toast.makeText(
                                        context,
                                        error.message,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }

                        .addOnFailureListener { error ->

                            println("LOGIN FAILED: ${error.message}")

                            Toast.makeText(
                                context,
                                error.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                },

                onForgotPassword = {

                    navController.navigate(ROUTE_FORGOTPASSWORD)
                },

                onSendOtp = {

                    println("Send OTP")
                },

                onVerifyOtp = {

                    navController.navigate(ROUTE_USER) {

                        popUpTo(ROUTE_LOGIN) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(ROUTE_REGISTER) {

            RegisterScreen(
                onRegister = { email, password ->

                    Toast.makeText(context, "Register clicked", Toast.LENGTH_SHORT).show()

                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener { result ->

                            val userId = result.user?.uid ?: return@addOnSuccessListener

                            val userMap = mapOf(
                                "email" to email,
                                "role" to "user"
                            )

                            db.child("users")
                                .child(userId)
                                .setValue(userMap)
                                .addOnSuccessListener {

                                    Toast.makeText(context, "Account created", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                }
                                .addOnFailureListener { error ->
                                    Toast.makeText(context, "DB error: ${error.message}", Toast.LENGTH_LONG).show()
                                }
                        }
                        .addOnFailureListener { error ->
                            Toast.makeText(context, "Auth error: ${error.message}", Toast.LENGTH_LONG).show()
                        }
                },
                onBackToLogin = {
                    navController.navigate(ROUTE_LOGIN) {

                        popUpTo(ROUTE_REGISTER) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(ROUTE_ADMIN) {
            Admin(
                onViewRequests = {
                    navController.navigate(ROUTE_ADMINREQUESTS)
                },
                onLogout = {
                    navController.navigate(ROUTE_LOGIN) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(ROUTE_USER) {
            User(
                onRequestRelief = { navController.navigate(ROUTE_REQUESTRELIEF) },
                onViewRequests = { navController.navigate(ROUTE_MYREQUESTS) },
                onProfile = { navController.navigate(ROUTE_PROFILE) }
            )
        }

        composable(ROUTE_REQUESTRELIEF) {
            RequestReliefScreen(
                onSubmit = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }

        composable(ROUTE_MYREQUESTS) {
            MyRequests(onBack = { navController.popBackStack() })
        }

        composable(ROUTE_PROFILE) {
            Profile(
                userName = "John Doe",
                phone = "+254712345678",
                role = "User",
                onLogout = {
                    navController.navigate(ROUTE_LOGIN) {
                        popUpTo(0)
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(ROUTE_ADMINREQUESTS) {
            AdminRequestsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(ROUTE_ONBOARDING1) {
            OnboardingScreen(
                onSkip = { navController.navigate(ROUTE_REGISTER) },
                onNext = { navController.navigate(ROUTE_REGISTER) }
            )
        }
    }
}