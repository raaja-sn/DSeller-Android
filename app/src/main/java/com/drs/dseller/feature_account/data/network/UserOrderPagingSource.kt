package com.drs.dseller.feature_account.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.drs.dseller.core.utils.NetworkUtils
import com.drs.dseller.feature_account.domain.model.UserOrder
import com.drs.dseller.feature_account.domain.model.UserOrderFilter

class UserOrderPagingSource(
    private val filter:UserOrderFilter,
    private val userOrderService:UserOrderService
) :PagingSource<UserOrderFilter,UserOrder>() {


    override fun getRefreshKey(state: PagingState<UserOrderFilter, UserOrder>): UserOrderFilter? {
        return null
    }

    override suspend fun load(params: LoadParams<UserOrderFilter>): LoadResult<UserOrderFilter, UserOrder> {
        val key = params.key?:filter
        return try{
            val resp = userOrderService.listOrders(
                key.userId,
                key.sortBy,
                key.sortOrder,
                key.pageNumber,
                key.pageSize
            )
            if(resp.code() == 200){
                val uList = resp.body()?: listOf()
                if(uList.isEmpty()){
                    LoadResult.Page(
                        uList,
                        null,
                        null
                    )
                }else{
                    LoadResult.Page(
                        uList,
                        getUpdatedKey(key.pageNumber-1,key),
                        getUpdatedKey(key.pageNumber+1,key)
                    )
                }
            }else{
                throw(Exception(NetworkUtils.parseErrorResponse(resp.errorBody()).message))
            }
        }catch(e:Exception){
            LoadResult.Error(e)
        }
    }

    private fun getUpdatedKey(pageNumber:Int, currFilter:UserOrderFilter):UserOrderFilter{
        return currFilter.copy(pageNumber = pageNumber)
    }
}