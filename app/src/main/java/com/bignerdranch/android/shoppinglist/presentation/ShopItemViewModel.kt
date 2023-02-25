package com.bignerdranch.android.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.shoppinglist.data.ShopListRepositoryImpl
import com.bignerdranch.android.shoppinglist.domain.AddShopItemUseCase
import com.bignerdranch.android.shoppinglist.domain.EditShopItemUseCase
import com.bignerdranch.android.shoppinglist.domain.GetShopItemUseCase
import com.bignerdranch.android.shoppinglist.domain.ShopItem

class ShopItemViewModel : ViewModel() {
    val repository = ShopListRepositoryImpl

    private val getShopItem = GetShopItemUseCase(repository)
    private val addShopItem = AddShopItemUseCase(repository)
    private val editShopItem = EditShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
    get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shoulCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getShopItem(shopItemId : Int) {
        val item = getShopItem.getShopItem(shopItemId)
        _shopItem.value = item
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val validInput = validateInput(name, count)
        if(validInput) {
            val item = ShopItem(name, count, true)
            addShopItem.addShopItem(item)
            finishWork()
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val validInput = validateInput(name, count)
        if(validInput) {
            _shopItem.value?.let {
                val item = it.copy(name = name, count = count)
                editShopItem.editShopItem(item)
                finishWork()
            }
        }
    }

    private fun parseName(inputName: String?) : String {
        return inputName?.trim() ?: ""
    }
    private fun parseCount(inputCount: String?) : Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int) : Boolean {
        var result = true
        if(name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if(count <=0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }
    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}