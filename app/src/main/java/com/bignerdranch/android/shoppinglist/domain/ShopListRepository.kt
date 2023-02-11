package com.bignerdranch.android.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun addShopItem(item: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(id: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>

    fun removeShopItem(shopItem: ShopItem)

}