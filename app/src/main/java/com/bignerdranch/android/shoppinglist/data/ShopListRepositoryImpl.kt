package com.bignerdranch.android.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.shoppinglist.domain.ShopItem
import com.bignerdranch.android.shoppinglist.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private val shopList = mutableListOf<ShopItem>()

    private var autoIncr = 0

    init {
        for(i in 0 until 1000) {
            val item = ShopItem("Name$i", i, Random.nextBoolean())
            shopList.add(item)
        }
    }

    override fun addShopItem(item: ShopItem) {
        if(item.id == ShopItem.UNDEFINED_ID) {

            item.id = autoIncr
            autoIncr++

        }
        
        shopList.add(item)
        update()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElem = getShopItem(shopItem.id)
        shopList.remove(oldElem)
        addShopItem(shopItem)
    }

    override fun getShopItem(id: Int): ShopItem {
        return shopList.find { it.id == id } ?: throw RuntimeException("Element not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    override fun removeShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        update()
    }

    private fun update() {
        shopListLD.value = shopList.toList()
    }

}