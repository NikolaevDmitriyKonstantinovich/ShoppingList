package com.bignerdranch.android.shoppinglist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.shoppinglist.data.ShopListRepositoryImpl
import com.bignerdranch.android.shoppinglist.domain.EditShopItemUseCase
import com.bignerdranch.android.shoppinglist.domain.RemoveShopItemUseCase
import com.bignerdranch.android.shoppinglist.domain.ShopItem
import com.bignerdranch.android.shoppinglist.domain.getShopListUseCase

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = getShopListUseCase(repository)
    private val deleteShopListUseCase = RemoveShopItemUseCase(repository)
    private val editShopListUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()


    fun deleteShopItem(item: ShopItem) {
        deleteShopListUseCase.removeShopItem(item)

    }

    fun changeEnabledState(item: ShopItem) {
        val newItem = item.copy(enabled = !item.enabled)
        editShopListUseCase.editShopItem(newItem)
    }

}