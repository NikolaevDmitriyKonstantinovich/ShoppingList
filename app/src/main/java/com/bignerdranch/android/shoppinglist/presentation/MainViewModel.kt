package com.bignerdranch.android.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.shoppinglist.data.ShopListRepositoryImpl
import com.bignerdranch.android.shoppinglist.domain.DeleteShopItemUseCase
import com.bignerdranch.android.shoppinglist.domain.EditShopItemUseCase
import com.bignerdranch.android.shoppinglist.domain.ShopItem
import com.bignerdranch.android.shoppinglist.domain.GetShopListUseCase

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)
    }
}