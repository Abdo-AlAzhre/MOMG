package com.example.xmltools.AppActivitys.Nav_Size

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.xmltools.AppActivitys.Compact.Portrait.HomeActivitys.Category_Annual.CategoryInventory
import com.example.xmltools.AppActivitys.Compact.Portrait.HomeActivitys.Category_Annual.Compact_Annual
import com.example.xmltools.AppActivitys.Compact.Portrait.HomeActivitys.Deposit_Add_Edit.Add_Deposit
import com.example.xmltools.AppActivitys.Compact.Portrait.HomeActivitys.Deposit_Add_Edit.Edit_Deposit
import com.example.xmltools.AppActivitys.Compact.Portrait.HomeActivitys.Home_MoreInfo.Compact_Home
import com.example.xmltools.AppActivitys.Compact.Portrait.HomeActivitys.Home_MoreInfo.Deposit_Information
import com.example.xmltools.AppActivitys.Compact.Portrait.HomeActivitys.Home_MoreInfo.Withdrawal_Information
import com.example.xmltools.AppActivitys.Compact.Portrait.HomeActivitys.Monthly_Setting.Compact_Monthly
import com.example.xmltools.AppActivitys.Compact.Portrait.HomeActivitys.Monthly_Setting.Compact_Setting
import com.example.xmltools.AppActivitys.Compact.Portrait.HomeActivitys.Withdrawal_Add_Edit.Compact_AddNewItems
import com.example.xmltools.AppActivitys.Compact.Portrait.HomeActivitys.Withdrawal_Add_Edit.Compact_Edit
import com.example.xmltools.AppActivitys.Compact.Portrait.HomeActivitys.FirstActivity.FirstActivity
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




    if (enterDestination != null) {
        AnimatedNavHost(
            navController = navController, startDestination =enterDestination!!,
        ) {

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
            composable("Compact_category", enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }) {
                CategoryInventory(navController)
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
                Withdrawal_Information(navController, id)
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

            composable("Compact_add_deposit", enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }, popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }) {
                Add_Deposit(navController =  navController)
            }
            composable(
                "edit_deposit/{id}",
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
                Edit_Deposit(modifier = Modifier ,navController, id)
            }
            composable(
                "info_deposit/{id}",
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
                Deposit_Information(modifier = Modifier, navController, id)
            }


        }
    }
}

