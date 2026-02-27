package com.example.xmltools.AppActivity.Compact.Portrait

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.xmltools.AppActivity.Compact.Portrait.HomeActivitys.Add_Edit.Compact_AddNewItems
import com.example.xmltools.AppActivity.Compact.Portrait.HomeActivitys.Compact_Annual
import com.example.xmltools.AppActivity.Compact.Portrait.HomeActivitys.Add_Edit.Compact_Edit
import com.example.xmltools.AppActivity.Compact.Portrait.HomeActivitys.Home_MoreInfo.Compact_Home
import com.example.xmltools.AppActivity.Compact.Portrait.HomeActivitys.Monthly_Setting.Compact_Monthly
import com.example.xmltools.AppActivity.Compact.Portrait.HomeActivitys.Monthly_Setting.Compact_Setting
import com.example.xmltools.AppActivity.Compact.Portrait.HomeActivitys.Home_MoreInfo.Compact_ShowMoreInfo
import com.example.xmltools.AppActivity.Compact.Portrait.LogInActivitys.Login_Signin.Compact_Login
import com.example.xmltools.AppActivity.Compact.Portrait.LogInActivitys.First_Passowrd.Compact_Password
import com.example.xmltools.AppActivity.Compact.Portrait.LogInActivitys.Login_Signin.Compact_SignIn
import com.example.xmltools.AppActivity.Compact.Portrait.LogInActivitys.First_Passowrd.FirstActivity
import com.example.xmltools.ViewModels.Guest_VM.GuestViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Compact_Nav() {

    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val viewModel = remember { GuestViewModel() }
    var enterDestination by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(Unit) {
        val isGuest = viewModel.getGuestState()
        enterDestination = when {
            currentUser != null -> "Compact_home"
            isGuest -> "Compact_home"
            else -> "Compact_first"
        }
    }

    val startDestination = if (currentUser != null) {
        "Compact_home"
    } else {
        "Compact_first"
    }

    if (enterDestination != null) {
        AnimatedNavHost(
            navController = navController, startDestination = enterDestination!!,
        ) {
            composable("Compact_login", enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }) {
                Compact_Login(navController)
            }
            composable("Compact_first", enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }) {
                FirstActivity(navController)
            }
            composable("Compact_home", enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }) {
                Compact_Home(navController)
            }
            composable("Compact_Signin", enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }) {
                Compact_SignIn(navController)
            }
            composable("Compact_password", enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }) {
                Compact_Password(navController)
            }
            composable("Compact_setting", enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            }, popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            }) {
                Compact_Setting(navController)
            }
            composable("Compact_monthly", enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }) {
                Compact_Monthly(navController)
            }
            composable("Compact_annual", enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }) {
                Compact_Annual(navController)
            }
            composable("Compact_add", enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }) {
                Compact_AddNewItems(navController)
            }
            composable(
                "Compact_showmoreinfo/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType }),
                enterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                },
                exitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                },
                popEnterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                },
                popExitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                }) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                Compact_ShowMoreInfo(navController, id)
            }

            composable(
                "Compact_edit/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType }),
                enterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                },
                exitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                },
                popEnterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                },
                popExitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                }) { backstack ->
                val id = backstack.arguments?.getString("id") ?: ""
                Compact_Edit(navController, id)
            }
        }
    }
}