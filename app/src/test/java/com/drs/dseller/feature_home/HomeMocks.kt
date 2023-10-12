package com.drs.dseller.feature_home

import com.drs.dseller.feature_home.domain.model.Category
import com.drs.dseller.feature_home.domain.model.HomeOffer
import com.drs.dseller.feature_home.domain.usecases.GetCategories
import com.drs.dseller.feature_home.domain.usecases.GetHomeOffers
import com.drs.dseller.feature_home.domain.usecases.HomeUseCases
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy

class HomeMocks {

    companion object{
        fun getUseCases():HomeUseCases{
            val getCategories:GetCategories = mock()
            val getOffers:GetHomeOffers = mock()
            return spy(HomeUseCases(getCategories,getOffers))
        }

        fun getCategories():List<Category>{
            return listOf(
                Category("Electronics"),
                Category("Home Appliances"),
                Category("Groceries"),
                Category("Clothing")
            )
        }

        fun getOffers():List<HomeOffer>{
            return listOf(
                HomeOffer("Electronics"),
                HomeOffer("Home Appliances"),
                HomeOffer("Groceries")
            )
        }
    }

}