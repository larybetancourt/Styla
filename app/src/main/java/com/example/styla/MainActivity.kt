package com.example.styla

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.styla.ui.screens.*
import com.example.styla.ui.theme.StylaTheme
import com.example.styla.ui.screens.AddItemScreen


object Routes {
    const val WELCOME = "welcome"
    const val SIGNUP = "signup"   // CoverScreen/Signup
    const val FORM = "form"
    const val LOGIN = "login"
    const val START = "start"     // post-registro / post-login
    const val ABOUT = "about"     // AboutYou (género, cumpleaños, estilo)
    const val CAMERA = "camera"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StylaTheme(darkTheme = false, dynamicColor = false) {
                val nav = rememberNavController()

                NavHost(
                    navController = nav,
                    startDestination = Routes.WELCOME
                ) {
                    // --------- Welcome ---------
                    composable(Routes.WELCOME) {
                        WelcomeScreen(
                            onCreateAccountClick = { nav.navigate(Routes.SIGNUP) },
                            onLoginClick = { nav.navigate(Routes.LOGIN) }
                        )
                    }

                    // --------- Signup (CoverScreen) ---------
                    composable(Routes.SIGNUP) {
                        Signup(
                            onNavigateToRegistro = { nav.navigate(Routes.FORM) },
                            onNavigateToLogin = { nav.navigate(Routes.LOGIN) },
                            onBack = { nav.navigateUp() }
                        )
                    }

                    // --------- Form (registro con email) ---------
                    composable(Routes.FORM) {
                        Form(
                            onBack = { nav.navigateUp() },
                            onSuccess = {
                                // Luego de crear cuenta, vamos a AboutYou para completar datos
                                nav.navigate(Routes.ABOUT) {
                                    popUpTo(Routes.WELCOME) { inclusive = false }
                                    launchSingleTop = true
                                }
                            },
                            onNavigateToLogin = { nav.navigate(Routes.LOGIN) }
                        )
                    }

                    // --------- Login ---------
                    composable(Routes.LOGIN) {
                        LoginScreen(
                            onNavigateBack = { nav.navigateUp() },
                            onSuccessLogin = {
                                // Al iniciar sesión, directo al Start
                                nav.navigate(Routes.START) {
                                    popUpTo(Routes.WELCOME) { inclusive = false }
                                    launchSingleTop = true
                                }
                            }
                        )
                    }

                    // --------- AboutYou (completa género/cumple/estilo) ---------
                    composable(Routes.ABOUT) {
                        AboutYouScreen(
                            onBack = { nav.navigateUp() },
                            onGoToStart = {
                                // Al guardar, ir al Start
                                nav.navigate(Routes.START) {
                                    popUpTo(Routes.WELCOME) { inclusive = false }
                                    launchSingleTop = true
                                }
                            }
                        )
                    }

                    // --------- Start ---------
                    composable(Routes.START) {
                        StartScreen(
                            onOpenCamera = { nav.navigate(Routes.CAMERA) }  // 👈 pasamos el callback
                        )
                    }

                    composable(Routes.CAMERA) {
                        AddItemScreen(onBack = { nav.navigateUp() })
                    }


                }
            }
        }
    }
}
