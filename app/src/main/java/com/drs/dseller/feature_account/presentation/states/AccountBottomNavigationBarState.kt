package com.drs.dseller.feature_account.presentation.states

import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import kotlinx.coroutines.flow.Flow

data class AccountBottomNavigationBarState(
    val cartFlow: Flow<List<CartProduct>>
)
