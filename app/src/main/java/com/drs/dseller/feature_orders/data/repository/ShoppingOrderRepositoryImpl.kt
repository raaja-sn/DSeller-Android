package com.drs.dseller.feature_orders.data.repository

import com.drs.dseller.core.domain.model.AppUser
import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.core.domain.repository.SessionRepository
import com.drs.dseller.core.network.ServiceGenerator
import com.drs.dseller.core.response.SessionResponse
import com.drs.dseller.core.utils.NetworkUtils
import com.drs.dseller.feature_orders.data.network.ShoppingOrder
import com.drs.dseller.feature_orders.data.network.ShoppingOrderService
import com.drs.dseller.feature_orders.domain.model.toOrderProducts
import com.drs.dseller.feature_orders.domain.repository.ShoppingOrderRepository
import com.drs.dseller.feature_orders.response.ShoppingOrderResponse

class ShoppingOrderRepositoryImpl(
    private val userSessionRepo:SessionRepository<SessionResponse<AppUser>>,
    private val serviceGenerator: ServiceGenerator
) : ShoppingOrderRepository<ShoppingOrderResponse<Unit>> {


    override suspend fun placeOrder(cartProducts: List<CartProduct>): ShoppingOrderResponse<Unit> {
        return try{
            val user = getCurrentUser()
            val newOrderHttpRequest = serviceGenerator.generateService(ShoppingOrderService::class.java)
            val response = newOrderHttpRequest.placeOrder(ShoppingOrder(
                userId = user.userId,
                products = cartProducts.toOrderProducts()
            ))
            if(response.code() == 201){
                ShoppingOrderResponse.Success(Unit)
            }else{
                throw(Exception(
                    NetworkUtils.parseErrorResponse(response.errorBody()).message
                ))
            }
        }catch(e:Exception){
            ShoppingOrderResponse.Error(e.message?:"")
        }
    }

    private suspend fun getCurrentUser():AppUser{
        return when(val r = userSessionRepo.getUser(false)){
            is SessionResponse.Error -> {
                throw(Exception(r.message))
            }

            SessionResponse.SignInError -> {
                throw(Exception("Session Expired Sign In again"))
            }

            is SessionResponse.Success -> {
                r.data
            }
        }
    }
}