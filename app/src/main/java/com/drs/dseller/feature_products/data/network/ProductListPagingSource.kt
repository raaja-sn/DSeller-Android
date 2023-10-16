package com.drs.dseller.feature_products.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.drs.dseller.feature_products.domain.model.Product

/**
 * Fetch products list from backend and paginate using Paging source
 *
 * @param productService The retrofit service class to fetch products
 * @param searchKey The search key used by paging source to paginate
 */
class ProductListPagingSource(
    private val productService:ProductsService,
    private val searchKey:ProductPagingKey
):PagingSource<ProductPagingKey,Product>() {

    override fun getRefreshKey(state: PagingState<ProductPagingKey, Product>): ProductPagingKey? {
        return null
    }

    override suspend fun load(params: LoadParams<ProductPagingKey>): LoadResult<ProductPagingKey, Product> {

        return try {
            val key = params.key ?: searchKey
            val resultResp = productService.listProducts(
                key.filter.category,
                key.filter.sortBy,
                key.filter.sortOrder,
                key.filter.pageNumber,
                20
            )
            val pList = resultResp.body() ?: listOf()
            if (pList.isEmpty()) {
                println("loading page ${key.filter.pageNumber}")
                LoadResult.Page(pList,null,null)
            } else {
                println("loading page ${key.filter.pageNumber}")
                LoadResult.Page(
                    pList,
                    getKey(key.filter.pageNumber - 1,  key),
                    getKey(key.filter.pageNumber + 1, key)
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun getKey(pageNumber:Int, key:ProductPagingKey):ProductPagingKey{
        return ProductPagingKey(key.filter.copy(pageNumber = pageNumber))
    }
}