@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalMaterial3ExpressiveApi::class
)

package com.example.xmltools.AppActivitys.Compact.Portrait.HomeActivitys.Home_MoreInfo

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import cairo_bold
import com.example.xmltools.AppADS.ImageADS
import com.example.xmltools.Model.All_Withdrawal_data.ModelNavigationItems
import com.example.xmltools.Model.All_Withdrawal_data.messages_ofToast.ModelMoreButtons
import com.example.xmltools.ViewModels.DepositDataViewModel.DepositDataViewModel
import com.example.xmltools.ViewModels.Deposit_and_Withdrawal.DepositAndWithdrawalViewModel
import com.example.xmltools.ViewModels.Deposit_and_Withdrawal.TransactionUiModel
import com.example.xmltools.ViewModels.Withdrawal_data_VM.WithdrawalDataViewModel
import com.example.xmltools.ui.theme.AppStyle
import com.example.xmltools.ui.theme.black
import com.example.xmltools.ui.theme.darkRed
import com.example.xmltools.ui.theme.forestGreen
import com.example.xmltools.ui.theme.green
import com.example.xmltools.ui.theme.noColor
import com.money.trackpay.R
import kotlinx.coroutines.launch
import readexpro_bold
import readexpro_medium

@Composable
fun Compact_Home(navController: NavHostController) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val setting = stringResource(R.string.setting)
    val monthly = stringResource(R.string.monthly_inventory)
    val annual = stringResource(R.string.annual_inventory)
    val category = stringResource(R.string.category_inventory)

    //4 this is the list of the the Activitys of the ModelNavigations :
    val ModelNavigationItemsList = listOf(
        ModelNavigationItems(
            title = stringResource(R.string.setting), icon = R.drawable.settings
        ), ModelNavigationItems(
            title = stringResource(R.string.monthly_inventory),
            icon = R.drawable.baseline_assignment_24
        ), ModelNavigationItems(
            title = stringResource(R.string.annual_inventory),
            icon = R.drawable.baseline_bar_chart_24
        ), ModelNavigationItems(
            title = stringResource(R.string.category_inventory),
            icon = R.drawable.category_inventory
        )
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.padding(end = 60.dp), drawerContainerColor = noColor
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = AppStyle.ModalNavigationDrawerColor
                            )
                        )
                        .border(
                            width = 1.dp,
                            color = black,
                            shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
                        )
                ) {
                    //` the name and icon of the App :
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .systemBarsPadding()
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(bottomEnd = 12.dp, bottomStart = 12.dp),
                                color = black
                            )
                            .padding(top = 20.dp, start = 12.dp, end = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,

                        ) {
                        Image(
                            modifier = Modifier.size(80.dp),
                            painter = painterResource(R.drawable.app_icons),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,

                            )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            modifier = Modifier.padding(top = 12.dp),
                            text = stringResource(R.string.app_name),
                            fontSize = 20.sp,
                            fontFamily = readexpro_bold,
                            letterSpacing = 5.50.sp,
                            color = AppStyle.textColor2,
                            textAlign = TextAlign.Center
                        )

                    }
                    Spacer(modifier = Modifier.padding(vertical = 32.dp))
                    //` this lazyColumn is for show user anuther Activitys :
                    LazyColumn {
                        items(ModelNavigationItemsList) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp),
                                onClick = {
                                    ImageADS(context = context)//4 here we add image ads \\
                                    when (it.title) {
                                        setting -> navController.navigate("Compact_setting") {
                                            popUpTo("Compact_home") {

                                            }
                                        }

                                        monthly -> navController.navigate("Compact_monthly") {
                                            popUpTo("Compact_home") {

                                            }
                                        }

                                        annual -> navController.navigate("Compact_annual") {
                                            popUpTo("Compact_home") {

                                            }
                                        }

                                        category -> navController.navigate("Compact_category") {
                                            popUpTo("Compact_home") {

                                            }
                                        }
                                    }
                                },
                                colors = CardDefaults.cardColors(
                                    containerColor = AppStyle.cardOfDrawerSheet,
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(
                                            width = 1.dp, brush = Brush.sweepGradient(
                                                colors = AppStyle.listOfBrushColor
                                            ), shape = RoundedCornerShape(12.dp)
                                        )
                                        .padding(horizontal = 24.dp, vertical = 12.dp),
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier.weight(2.50f),
                                        text = it.title,
                                        fontFamily = readexpro_medium,
                                        fontSize = 16.sp
                                    )
                                    Icon(
                                        modifier = Modifier.weight(0.50f),
                                        imageVector = ImageVector.vectorResource(it.icon),
                                        contentDescription = null
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.padding(vertical = 12.dp))
                        }
                    }

                }
            }
        },
    ) {
        //~ and here we show the main Activity , the home Activity :
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .systemBarsPadding(),
            topBar = { Compact_homeTopBar() { scope.launch { drawerState.open() } } },

            floatingActionButton = {
                Compact_HomeFloatActionButton(
                    navController,
                    onAddExpenses = {},
                    onAddMoneySave = {})
            }) { innerpadding ->
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.back_ground1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Compact_HomeBody(
                modifier = Modifier.padding(innerpadding), navController = navController
            )
        }
    }
}

@Composable
private fun Compact_homeTopBar(function: () -> Unit) {
    //1 this is withdrawal viewModel :
    val withdrawalDataViewModel: WithdrawalDataViewModel = viewModel()
    val allDataWithdrawal by withdrawalDataViewModel.withdrawalDataFlow.collectAsState()
    //1 this is deposit viewModel :
    val depositDataViewModel: DepositDataViewModel = viewModel()
    val allDataDeposit by depositDataViewModel.depositDataFlow.collectAsState()
    //4 here we calculate the remaining balance of withdrawal and deposit :
    val amountOfDeposit = allDataDeposit.sumOf { it.depositAmount }
    val amountOfWithdrawal = allDataWithdrawal.sumOf { it.amount }
    val theResult = amountOfDeposit - amountOfWithdrawal
    //` this is the Percentage of the (withdrawal/Depsoit) :
    val percentage = if (amountOfDeposit != 0.0) {
        (amountOfWithdrawal / amountOfDeposit).toFloat()
    } else {
        0f
    }
    //` the animation of the progress :
    val animationOfProgress by animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(
            durationMillis = 8500, easing = FastOutSlowInEasing
        ),
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = noColor),

        ) {
        //~ here we show the user the total he have new :
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Text(
                modifier = Modifier.padding(horizontal = 6.dp),
                text = "${stringResource(R.string.total_amount)}: $theResult ",
                color = AppStyle.textColor2,
                fontFamily = cairo_bold
            )
            CircularProgressIndicator(
                modifier = Modifier.size(42.dp),
                progress = { animationOfProgress },
                color = darkRed,
                trackColor = forestGreen,
                strokeWidth = 6.dp,

                )
        }

        //~ this is the button of menu :
        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = { function() },
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = AppStyle.buttonColor,
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.menu),
                contentDescription = "menu",
                tint = black
            )
        }
    }
}

@Composable
private fun Compact_HomeBody(modifier: Modifier = Modifier, navController: NavHostController) {

    //1 this is withdrawal viewModel :
    val withdrawalDataViewModel: WithdrawalDataViewModel = viewModel()

    //1 this is deposit viewModel :
    val depositDataViewModel: DepositDataViewModel = viewModel()

    //1 this is all data viewModel :
    val allDataViewModel: DepositAndWithdrawalViewModel = viewModel()
    val allDataDepsitAndWithdrawal by allDataViewModel.ALL_DATA.collectAsState()

    //2 We using this to filter withdrawal by Category and Date:
    var searchText by rememberSaveable { mutableStateOf("") }
    val searchFromWithdrawalData by withdrawalDataViewModel.withdrawalDataFlow.collectAsState(
        initial = emptyList()
    )
    val filteredWithdrawalByCategory = if (searchText.isEmpty()) {
        emptyList()
    } else searchFromWithdrawalData.filter { item ->
        val category = item.type
        val date = item.date
        category.contains(searchText, ignoreCase = true) || date.contains(
            searchText, ignoreCase = true
        )
    }
    //2 filter deposit by category and date :
    val filterDeposit by depositDataViewModel.depositDataFlow.collectAsState(initial = emptyList())
    val theDepositFilter = if (searchText.isEmpty()) {
        emptyList()
    } else filterDeposit.filter { item ->
        val category = item.depositType
        val date = item.date
        category.contains(searchText, ignoreCase = true) || date.contains(
            searchText, ignoreCase = true
        )
    }

    var showTextFieldOfSearch by rememberSaveable { mutableStateOf(false) }
    var searchBarExpanded by rememberSaveable { mutableStateOf(false) }

    val animation_Button by animateFloatAsState(
        targetValue = if (showTextFieldOfSearch) 0f else 1f,
        animationSpec = MaterialTheme.motionScheme.slowSpatialSpec()
    )
    val animation_TextField by animateFloatAsState(
        targetValue = if (showTextFieldOfSearch) 1f else 0f,
        animationSpec = MaterialTheme.motionScheme.slowSpatialSpec()
    )
    val animation_ListHome by animateFloatAsState(
        targetValue = if (showTextFieldOfSearch) 0f else 1f,
        animationSpec = MaterialTheme.motionScheme.slowSpatialSpec()
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp)
            .border(
                width = .4.dp, shape = RoundedCornerShape(4.dp), color = black
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {

        //3 here we show button and outLineTextFiald of Searhcing :

        if (showTextFieldOfSearch) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        this.scaleX = animation_TextField
                        this.alpha = animation_TextField
                    }
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Right) {
                IconButton(
                    modifier = Modifier.weight(.3f), onClick = {
                        showTextFieldOfSearch = false
                    }, colors = IconButtonDefaults.iconButtonColors(
                        containerColor = AppStyle.cardColor
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.search_off),
                        contentDescription = "search",
                        tint = black
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Column(
                    modifier = Modifier.weight(2f),
                ) {
                    DockedSearchBar(
                        query = searchText,
                        onQueryChange = { searchText = it },
                        onSearch = { searchBarExpanded = false },
                        active = searchBarExpanded,
                        onActiveChange = {},
                        placeholder = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.search_by_category),
                                    color = AppStyle.textColor2
                                )
                            }
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.search),
                                contentDescription = "search",
                                tint = black
                            )
                        },
                        colors = SearchBarDefaults.colors(
                            inputFieldColors = TextFieldDefaults.colors(
                                unfocusedTextColor = AppStyle.textColor2,
                                focusedTextColor = AppStyle.textColor2,
                                focusedContainerColor = AppStyle.buttonWithTextColor,
                                unfocusedContainerColor = AppStyle.buttonWithTextColor,
                                cursorColor = black,
                            )
                        ),
                        shadowElevation = 12.dp,
                    ) {

                    }
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        this.alpha = animation_Button
                        this.scaleX = animation_Button
                    }
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Center) {
                Button(
                    modifier = Modifier.fillMaxWidth(), onClick = {
                        showTextFieldOfSearch = true
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = AppStyle.buttonWithTextColor
                    )
                ) {
                    Text(
                        text = stringResource(R.string.search),
                        color = AppStyle.outLinText,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    Icon(
                        painter = painterResource(R.drawable.search),
                        contentDescription = "search",
                        tint = black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //3 search in withdrawal items :
            if (showTextFieldOfSearch) {
                items(filteredWithdrawalByCategory) { withdrawal_Data ->
                    Card(
                        modifier = Modifier
                            .graphicsLayer {
                                this.scaleX = animation_TextField
                                this.scaleY = animation_TextField
                            }
                            .fillMaxWidth()
                            .padding(12.dp), colors = CardDefaults.cardColors(
                            containerColor = AppStyle.cardColor
                        )) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                Text(
                                    text = "${withdrawal_Data.date}",
                                    fontSize = 16.sp,
                                    fontFamily = readexpro_medium,
                                    color = AppStyle.outLinText,
                                )

                                Image(
                                    modifier = Modifier.size(28.dp),
                                    painter = painterResource(withdrawal_Data.withdrawalIcon),
                                    contentDescription = "withdrawal icons",
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(
                                modifier = Modifier
                                    .border(
                                        width = 1.dp, brush = Brush.linearGradient(
                                            colors = AppStyle.listOfBrushColor
                                        ), shape = RoundedCornerShape(1.dp)
                                    )
                                    .weight(1f)
                                    .shadow(elevation = 1.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    text = stringResource(R.string.withdrawal),
                                    color = AppStyle.outLinText
                                )
                                Spacer(modifier = Modifier.padding(vertical = 1.dp))
                                Text(
                                    text = "${withdrawal_Data.amount}",
                                    fontFamily = readexpro_medium,
                                    color = AppStyle.outLinText
                                )
                                Spacer(modifier = Modifier.padding(vertical = 1.dp))
                            }
                            Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                            Column(
                                modifier = Modifier
                                    .border(
                                        width = 1.dp, brush = Brush.linearGradient(
                                            colors = AppStyle.listOfBrushColor
                                        ), shape = RoundedCornerShape(1.dp)
                                    )
                                    .weight(1f)
                                    .shadow(elevation = 1.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    text = stringResource(R.string.category),
                                    color = AppStyle.outLinText
                                )
                                Spacer(modifier = Modifier.padding(vertical = 1.dp))
                                Text(
                                    "${withdrawal_Data.type}",
                                    fontFamily = readexpro_medium,
                                    color = AppStyle.outLinText,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.padding(vertical = 1.dp))
                            }
                        }
                        Spacer(modifier = Modifier.padding(vertical = 8.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Button(
                                onClick = {
                                    navController.navigate("Compact_showmoreinfo/${withdrawal_Data.id.toHexString()}")
                                }, colors = ButtonDefaults.buttonColors(
                                    containerColor = AppStyle.buttonColor
                                )
                            ) {
                                Row {
                                    Text(
                                        text = stringResource(R.string.more_information),
                                        color = AppStyle.textButtonColor,
                                        fontFamily = readexpro_medium,

                                        )
                                    Icon(
                                        painter = painterResource(R.drawable.arrow_drop_down),
                                        contentDescription = "more",
                                        tint = green
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(vertical = 4.dp))
                    }
                }
                //3 searsh in deposit items :
                items(theDepositFilter) { deposit_data ->
                    Card(
                        modifier = Modifier
                            .graphicsLayer {
                                this.scaleX = animation_TextField
                                this.scaleY = animation_TextField
                            }
                            .fillMaxWidth()
                            .padding(12.dp), colors = CardDefaults.cardColors(
                            containerColor = AppStyle.cardColor
                        )) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                Text(
                                    text = "${deposit_data.date}",
                                    fontSize = 16.sp,
                                    fontFamily = readexpro_medium,
                                    color = AppStyle.outLinText,
                                )
                                Image(
                                    modifier = Modifier.size(28.dp),
                                    painter = painterResource(deposit_data.depositIcon),
                                    contentDescription = "withdrawal icons",
                                )
                            }

                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(
                                modifier = Modifier
                                    .border(
                                        width = 1.dp, brush = Brush.linearGradient(
                                            colors = AppStyle.listOfBrushColor
                                        ), shape = RoundedCornerShape(1.dp)
                                    )
                                    .weight(1f)
                                    .shadow(elevation = 1.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    text = stringResource(R.string.deposit),
                                    color = AppStyle.outLinText
                                )
                                Spacer(modifier = Modifier.padding(vertical = 1.dp))
                                Text(
                                    text = "${deposit_data.depositAmount}",
                                    fontFamily = readexpro_medium,
                                    color = AppStyle.outLinText
                                )
                                Spacer(modifier = Modifier.padding(vertical = 1.dp))
                            }
                            Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                            Column(
                                modifier = Modifier
                                    .border(
                                        width = 1.dp, brush = Brush.linearGradient(
                                            colors = AppStyle.listOfBrushColor
                                        ), shape = RoundedCornerShape(1.dp)
                                    )
                                    .weight(1f)
                                    .shadow(elevation = 1.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    text = stringResource(R.string.category),
                                    color = AppStyle.outLinText
                                )
                                Spacer(modifier = Modifier.padding(vertical = 1.dp))
                                Text(
                                    "${deposit_data.depositType}",
                                    fontFamily = readexpro_medium,
                                    color = AppStyle.outLinText,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.padding(vertical = 1.dp))
                            }
                        }
                        Spacer(modifier = Modifier.padding(vertical = 8.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Button(
                                onClick = {
                                    navController.navigate("info_deposit/${deposit_data.id.toHexString()}")
                                }, colors = ButtonDefaults.buttonColors(
                                    containerColor = AppStyle.buttonColor
                                )
                            ) {
                                Row {
                                    Text(
                                        text = stringResource(R.string.more_information),
                                        color = AppStyle.textButtonColor,
                                        fontFamily = readexpro_medium,

                                        )
                                    Icon(
                                        painter = painterResource(R.drawable.arrow_drop_down),
                                        contentDescription = "more",
                                        tint = green
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(vertical = 4.dp))
                    }
                }
            }
            //3 here we show all items :
            else {
                items(allDataDepsitAndWithdrawal) { localeData ->
                    when (localeData) {
                        //3 here we show deposit itemms :
                        is TransactionUiModel.Deposit -> {
                            Card(
                                modifier = Modifier
                                    .graphicsLayer {
                                        this.scaleX = animation_ListHome
                                        this.scaleY = animation_ListHome
                                    }
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = AppStyle.cardColor
                                )) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                                    ) {
                                        Text(
                                            text = "${localeData.data.day}/${localeData.data.month}/${localeData.data.year}",
                                            fontSize = 16.sp,
                                            fontFamily = readexpro_medium,
                                            color = AppStyle.outLinText,
                                        )
                                        Image(
                                            modifier = Modifier.size(28.dp),
                                            painter = painterResource(localeData.data.depositIcon),
                                            contentDescription = "withdrawal icons",
                                        )
                                    }

                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .border(
                                                width = 1.dp, brush = Brush.linearGradient(
                                                    colors = AppStyle.listOfBrushColor
                                                ), shape = RoundedCornerShape(1.dp)
                                            )
                                            .weight(1f)
                                            .shadow(elevation = 1.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,

                                        ) {
                                        Text(
                                            text = stringResource(R.string.deposit),
                                            color = AppStyle.outLinText
                                        )
                                        Spacer(modifier = Modifier.padding(vertical = 1.dp))
                                        Text(
                                            text = "${localeData.data.depositAmount}",
                                            fontFamily = readexpro_medium,
                                            color = AppStyle.outLinText
                                        )
                                        Spacer(modifier = Modifier.padding(vertical = 1.dp))
                                    }
                                    Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                                    Column(
                                        modifier = Modifier
                                            .border(
                                                width = 1.dp, brush = Brush.linearGradient(
                                                    colors = AppStyle.listOfBrushColor
                                                ), shape = RoundedCornerShape(1.dp)
                                            )
                                            .weight(1f)
                                            .shadow(elevation = 1.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                    ) {
                                        Text(
                                            text = stringResource(R.string.category),
                                            color = AppStyle.outLinText
                                        )
                                        Spacer(modifier = Modifier.padding(vertical = 1.dp))
                                        Text(
                                            "${localeData.data.depositType}",
                                            fontFamily = readexpro_medium,
                                            color = AppStyle.outLinText,
                                            textAlign = TextAlign.Center,
                                            maxLines = 1
                                        )
                                        Spacer(modifier = Modifier.padding(vertical = 1.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Bottom
                                ) {
                                    Button(
                                        onClick = {
                                            navController.navigate("info_deposit/${localeData.data.id.toHexString()}")
                                        }, colors = ButtonDefaults.buttonColors(
                                            containerColor = AppStyle.buttonColor
                                        )
                                    ) {
                                        Row {
                                            Text(
                                                text = stringResource(R.string.more_information),
                                                color = AppStyle.textButtonColor,
                                                fontFamily = readexpro_medium,

                                                )
                                            Icon(
                                                painter = painterResource(R.drawable.arrow_drop_down),
                                                contentDescription = "more",
                                                tint = green
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                            }
                        }
                        //3 here we show withdrawal itemms :
                        is TransactionUiModel.Withdrawal -> {
                            Card(
                                modifier = Modifier
                                    .graphicsLayer {
                                        this.scaleX = animation_ListHome
                                        this.scaleY = animation_ListHome
                                    }
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = AppStyle.cardColor
                                )) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                                    ) {
                                        Text(
                                            text = "${localeData.data.date}",
                                            fontSize = 16.sp,
                                            fontFamily = readexpro_medium,
                                            color = AppStyle.outLinText,
                                        )
                                        Image(
                                            modifier = Modifier.size(28.dp),
                                            painter = painterResource(localeData.data.withdrawalIcon),
                                            contentDescription = "withdrawal icons",
                                        )
                                    }

                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .border(
                                                width = 1.dp, brush = Brush.linearGradient(
                                                    colors = AppStyle.listOfBrushColor
                                                ), shape = RoundedCornerShape(1.dp)
                                            )
                                            .weight(1f)
                                            .shadow(elevation = 1.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,

                                        ) {
                                        Text(
                                            text = stringResource(R.string.withdrawal),
                                            color = AppStyle.outLinText
                                        )
                                        Spacer(modifier = Modifier.padding(vertical = 1.dp))
                                        Text(
                                            text = "${localeData.data.amount}",
                                            fontFamily = readexpro_medium,
                                            color = AppStyle.outLinText
                                        )
                                        Spacer(modifier = Modifier.padding(vertical = 1.dp))
                                    }
                                    Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                                    Column(
                                        modifier = Modifier
                                            .border(
                                                width = 1.dp, brush = Brush.linearGradient(
                                                    colors = AppStyle.listOfBrushColor
                                                ), shape = RoundedCornerShape(1.dp)
                                            )
                                            .weight(1f)
                                            .shadow(elevation = 1.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                    ) {
                                        Text(
                                            text = stringResource(R.string.category),
                                            color = AppStyle.outLinText
                                        )
                                        Spacer(modifier = Modifier.padding(vertical = 1.dp))
                                        Text(
                                            "${localeData.data.type}",
                                            fontFamily = readexpro_medium,
                                            color = AppStyle.outLinText,
                                            textAlign = TextAlign.Center,
                                            maxLines = 1
                                        )
                                        Spacer(modifier = Modifier.padding(vertical = 1.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Bottom
                                ) {
                                    Button(
                                        onClick = {
                                            navController.navigate("Compact_showmoreinfo/${localeData.data.id.toHexString()}")
                                        }, colors = ButtonDefaults.buttonColors(
                                            containerColor = AppStyle.buttonColor
                                        )
                                    ) {
                                        Row {
                                            Text(
                                                text = stringResource(R.string.more_information),
                                                color = AppStyle.textButtonColor,
                                                fontFamily = readexpro_medium,

                                                )
                                            Icon(
                                                painter = painterResource(R.drawable.arrow_drop_down),
                                                contentDescription = "more",
                                                tint = green
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

//~ this button to add new deposit or withdrawal items :
@Composable
private fun Compact_HomeFloatActionButton(
    navController: NavHostController,
    onAddExpenses: () -> Unit,
    onAddMoneySave: () -> Unit,
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val fadItems = listOf<ModelMoreButtons>(
        ModelMoreButtons(
            icon = R.drawable.deposit_icons, text = stringResource(R.string.deposit)
        ),
        ModelMoreButtons(
            icon = R.drawable.withdrawal_icons, text = stringResource(R.string.withdrawal)
        ),
    )
    FloatingActionButtonMenu(
        expanded = expanded, button = {
            Button(
                modifier = Modifier.alpha(0.90f),
                onClick = {
                    expanded = !expanded
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppStyle.floatButtonColor
                ),
                shape = RoundedCornerShape(size = 12.dp),
            ) {
                Row(
                    modifier = Modifier.padding(2.50.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_add_24),
                        contentDescription = null,
                        tint = AppStyle.iconButtonColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.new_item),
                        fontSize = 16.sp,
                        color = AppStyle.textColor2,
                    )
                }
            }
        }) {

        fadItems.forEach { items ->
            FloatingActionButtonMenuItem(
                onClick = {
                    ImageADS(context = context) //4 here we add image ads \\
                    when (items.icon) {
                        R.drawable.deposit_icons -> {
                            navController.navigate("Compact_add_deposit") {
                                popUpTo("Compact_home")
                            }
                        }

                        R.drawable.withdrawal_icons -> {
                            navController.navigate("Compact_add") {
                                popUpTo("Compact_home")
                            }
                        }
                    }
                }, text = {
                    Text(text = items.text, color = AppStyle.textColor2)
                }, icon = {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(items.icon),
                        contentDescription = null,
                        tint = black
                    )
                }, containerColor = AppStyle.buttonColor
            )
        }
    }
}