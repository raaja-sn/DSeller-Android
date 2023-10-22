package com.drs.dseller.feature_home.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.drs.dseller.R
import com.drs.dseller.core.ui_elements.appbar.AppBottomNavigationBar
import com.drs.dseller.feature_home.presentation.HomeEvent
import com.drs.dseller.feature_home.presentation.HomeViewModel
import com.drs.dseller.feature_home.presentation.screens.components.OfferImage
import com.drs.dseller.feature_home.presentation.states.HomeState
import com.drs.dseller.feature_products.presentation.toProducts

@Composable
fun HomeScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    state:HomeState,
    vm:HomeViewModel,
    navHostController: NavHostController
){

    DisposableEffect(key1 = lifecycleOwner){
        val observer = object:DefaultLifecycleObserver{
            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                vm.onEvent(HomeEvent.GetCategories)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val searchChanged = remember{
        { text:String ->
            vm.onEvent(HomeEvent.SearchChanged(text))
        }
    }

    val categoryClicked = remember{
        { idx:Int ->
            navHostController.toProducts(state.categories[idx].name)
        }
    }

    val cartItems = state.cartFlow.collectAsStateWithLifecycle().value

    Scaffold(
        bottomBar = {
            AppBottomNavigationBar(
                cartQuantity =  cartItems.size,
                navHostController = navHostController
            )
        }
    ) { it ->
        Column(
            modifier = Modifier
                .padding(it)
        ){
            HomeBody(
                state = state ,
                searchChanged = searchChanged ,
                categoryClicked = categoryClicked
            )
        }
    }


}
