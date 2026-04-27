@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.xmltools.AppActivitys.Compact.Portrait.HomeActivitys.Category_Annual

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import cairo_bold
import com.example.xmltools.AppADS.BannerADS
import com.example.xmltools.ViewModels.DepositDataViewModel.DepositDataViewModel
import com.example.xmltools.ViewModels.Withdrawal_data_VM.WithdrawalDataViewModel
import com.example.xmltools.ui.theme.AppStyle
import com.example.xmltools.ui.theme.black
import com.money.trackpay.R

@Composable
fun CategoryInventory(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .navigationBarsPadding(),
        bottomBar = {
            BannerADS(modifier = Modifier)
        }
    ) { paddingValues ->
        //4 here we just add the background of this activity :
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.munthly_background),
            contentDescription = "background",
            contentScale = ContentScale.Crop,
        )
        CategoryBody(
            navController, modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

private @Composable
fun CategoryBody(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .navigationBarsPadding(),
    ) { paddingValues ->
        //4 here we just add the background of this activity :
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.munthly_background),
            contentDescription = "background",
            contentScale = ContentScale.Crop,
        )
        CategoryBodysItems(
            navController, modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

//3 this function is to add the items in the body , like ( text , outlinTextField.... ) 😱 :
@Composable
fun CategoryBodysItems(
    navController: NavHostController,
    modifier: Modifier,
) {
    //` here we get data from withdrawal data :
    val withdrawalDataViewModel = viewModel<WithdrawalDataViewModel>()
    val dataOfWithdrawal by withdrawalDataViewModel.withdrawalDataFlow.collectAsState(initial = emptyList())
    //` here we get data from deposit data :
    val depositDataViewModel = viewModel<DepositDataViewModel>()
    val dataOfDeposit by depositDataViewModel.depositDataFlow.collectAsState(initial = emptyList())

    //` value of searching in deposit and withdrawal by category :
    var searchText by rememberSaveable { mutableStateOf("") }
    //~ filtering withdrawal data by catigorys :
    val filterWithdrawal = if (searchText.isEmpty()) {
        emptyList()
    } else {
        dataOfWithdrawal.filter { itemData ->
            val category = itemData.type
            category.contains(searchText, ignoreCase = true)
        }
    }
    //~ filtering deoist data by catigorys :
    val filterDeposit = if (searchText.isEmpty()) {
        emptyList()
    } else {
        dataOfDeposit.filter { itemData ->
            val category = itemData.depositType
            category.contains(searchText, ignoreCase = true)
        }
    }
    //~ this variable is to get the total of amounts of deposit catigory :
    val depositAmount by remember(filterDeposit) {
        //4 we should know that we used " derivedStateOf " to make it restart the result aoutomatic : 😘
        derivedStateOf {
            filterDeposit.sumOf { it.depositAmount }
        }
    }
    //~ this variable is to get the total of amounts of withdrawal catigory :
    val withdrawalAmount by remember(filterWithdrawal) {
        //4 we should know that we used " derivedStateOf " to make it restart the result aoutomatic : 😘
        derivedStateOf {
            filterWithdrawal.sumOf { it.amount }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp, end = 4.dp, start = 4.dp)
            .border(
                width = 1.dp,
                color = black,
                shape = RoundedCornerShape(0.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        //3 here user is searching of his gategory in deposit or in withdrawal 🥲 :
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 7.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        )
        {
            DockedSearchBar(
                modifier = Modifier.fillMaxWidth(),
                query = searchText,
                onQueryChange = { searchText = it },
                onSearch = {},
                active = false,
                onActiveChange = {},
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.search),
                        contentDescription = null,
                        tint = black,
                    )

                },
                placeholder = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.search),
                        textAlign = TextAlign.Center,
                        color = AppStyle.textColor2,
                    )
                },
                colors = SearchBarDefaults.colors(
                    containerColor = AppStyle.cardColor,
                    inputFieldColors = TextFieldDefaults.colors(
                        cursorColor = black,
                        unfocusedContainerColor = AppStyle.cardColor,
                        focusedContainerColor = AppStyle.cardColor,
                        unfocusedTextColor = AppStyle.textColor2,
                        focusedTextColor = AppStyle.textColor2,

                        )
                )
            ) {

            }
        }
        Spacer(modifier = Modifier.height(18.dp))
        //3 here we show the user the total of the amount of this catigorys 😡 :
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
                .border(
                    width = 1.dp,
                    color = black,
                    shape = RoundedCornerShape(4.dp),
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //2 total deposit amount in this catigory  😃😍 :
            if (depositAmount > 0.0) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.weight(3f),
                        text = "$depositAmount",
                        color = black,
                        fontFamily = cairo_bold,
                        textAlign = TextAlign.Center,
                    )
                    Image(
                        modifier = Modifier
                            .size(36.dp)
                            .weight(0.5f),
                        painter = painterResource(R.drawable.deposit),
                        contentDescription = null,
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.weight(3f),
                        text = "0.0",
                        color = black,
                        fontFamily = cairo_bold,
                        textAlign = TextAlign.Center,
                    )
                    Image(
                        modifier = Modifier
                            .size(36.dp)
                            .weight(0.5f),
                        painter = painterResource(R.drawable.deposit),
                        contentDescription = null,
                    )
                }
            }
            VerticalDivider(modifier = Modifier.height(28.dp))
            //2 total withdrawal amount in this catigory  🫠🥲 :
            if (withdrawalAmount > 0.0) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.weight(3f),
                        text = "$withdrawalAmount",
                        color = black,
                        fontFamily = cairo_bold,
                        textAlign = TextAlign.Center,
                    )
                    Image(
                        modifier = Modifier
                            .size(36.dp)
                            .weight(0.5f),
                        painter = painterResource(R.drawable.withdrawal),
                        contentDescription = null,
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.weight(3f),
                        text = "0.0",
                        color = black,
                        fontFamily = cairo_bold,
                        textAlign = TextAlign.Center,
                    )
                    Image(
                        modifier = Modifier
                            .size(36.dp)
                            .weight(0.5f),
                        painter = painterResource(R.drawable.withdrawal),
                        contentDescription = null,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(18.dp))
        //3 here we show user the items of this categort 🫠:
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
                .border(
                    width = 1.dp,
                    color = black,
                    shape = RoundedCornerShape(8.dp),
                ),
            columns = GridCells.Fixed(2)
        ) {
            //3 here we show the result of serching in depost categorys : 🙆🏻‍♂️
            items(filterDeposit) { deposit_data ->
                Card(
                    modifier = Modifier.padding(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AppStyle.cardColor
                    )
                ) {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(
                                12.dp,
                                Alignment.CenterHorizontally
                            )
                        ) {
                            Text(text = deposit_data.date, color = AppStyle.textColor2)
                            Image(
                                modifier = Modifier
                                    .size(20.dp),
                                painter = painterResource(R.drawable.deposit),
                                contentDescription = null,
                            )
                        }
                        Spacer(modifier = Modifier.height(18.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                12.dp,
                                Alignment.CenterVertically
                            )
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "${deposit_data.depositAmount} $",
                                textAlign = TextAlign.Center,
                                color = AppStyle.textColor2
                            )
                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 6.dp),
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = deposit_data.depositType,
                                textAlign = TextAlign.Center,
                                color = AppStyle.textColor2
                            )
                        }
                    }
                }
            }
            //3 here we show the result of serching in depost categorys : ...... 🏃🏻‍♀️‍➡️ 🏃🏻‍♂️‍➡️  🏃🏻‍➡️   🏃🏻‍♀️‍➡️    🏃🏻‍♂️‍➡️     🏃🏻‍➡️  |---|
            items(filterWithdrawal) { withdrawal_Data ->
                Card(
                    modifier = Modifier.padding(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AppStyle.cardColor
                    )
                ) {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(
                                12.dp,
                                Alignment.CenterHorizontally
                            )
                        ) {
                            Text(text = withdrawal_Data.date, color = AppStyle.textColor2)
                            Image(
                                modifier = Modifier
                                    .size(20.dp),
                                painter = painterResource(R.drawable.withdrawal),
                                contentDescription = null,
                            )
                        }
                        Spacer(modifier = Modifier.height(18.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                12.dp,
                                Alignment.CenterVertically
                            )
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "${withdrawal_Data.amount} $",
                                textAlign = TextAlign.Center,
                                color = AppStyle.textColor2
                            )
                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 6.dp),
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = withdrawal_Data.type,
                                textAlign = TextAlign.Center,
                                color = AppStyle.textColor2
                            )
                        }
                    }
                }
            }
        }
    }
}