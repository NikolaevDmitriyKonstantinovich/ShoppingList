package com.bignerdranch.android.shoppinglist.domain

class RemoveShopItemUseCase( private val shopListRepository: ShopListRepository ) {

    fun removeShopItem(shopItem: ShopItem) {
        shopListRepository.removeShopItem(shopItem)
    }

}