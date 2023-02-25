package com.bignerdranch.android.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.shoppinglist.R
import com.bignerdranch.android.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout


class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

//    private lateinit var viewModel: ShopItemViewModel
//
//    private lateinit var tilName: TextInputLayout
//    private lateinit var tilCount: TextInputLayout
//    private lateinit var etName: EditText
//    private lateinit var etCount: EditText
//    private lateinit var buttonSave: Button

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
//        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
//        initViews()
//        addTextChangeListeners()
        if(savedInstanceState == null) {
            launchRightMode()
        }
//        observeViewModel()
    }

//    private fun observeViewModel() {
//        viewModel.errorInputCount.observe(this) {
//            val message = if (it) {
//                getString(com.google.android.material.R.string.error_a11y_label) //!!!!!!!!!!!!!!
//            } else {
//                null
//            }
//
//            tilCount.error = message
//        }
//
//        viewModel.errorInputName.observe(this) {
//            val message = if (it) {
//                getString(com.google.android.material.R.string.error_icon_content_description) //!!!!!!!!
//            } else {
//                null
//            }
//
//            tilName.error = message
//        }
//        viewModel.shoulCloseScreen.observe(this) {
//            finish()
//        }
//    }
//
    private fun launchRightMode() {
    val fragment = when(screenMode) {
            MODE_EDIT -> ShopItemFragment.newInstanceEditItem(shopItemId)
            MODE_ADD -> ShopItemFragment.newInstanceAddItem()
        else -> throw RuntimeException("Unknown mode")
        }
    supportFragmentManager.beginTransaction()
        .replace(R.id.shop_item_container, fragment)
        .commit()
    }
//
//    private fun addTextChangeListeners() {
//        etName.addTextChangedListener(object : TextWatcher
//        {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetErrorInputName()
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//            }
//
//        })
//
//        etCount.addTextChangedListener(object : TextWatcher
//        {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetErrorInputCount()
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//            }
//
//        })
//    }
//
//
//    private fun launchEditMode() {
//        viewModel.getShopItem(shopItemId)
//        viewModel.shopItem.observe(this) {
//            etName.setText(it.name)
//            etCount.setText(it.count.toString())
//        }
//        buttonSave.setOnClickListener {
//            viewModel.editShopItem(etName.text?.toString(), etCount.toString())
//        }
//    }
//    private fun launchAddMode() {
//        buttonSave.setOnClickListener {
//            viewModel.addShopItem(etName.text?.toString(), etCount.toString())
//        }
//    }
//
    private fun parseIntent() {
        if(!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen_mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if(mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown mode")
        }
        screenMode = mode

        if(screenMode == MODE_EDIT) {
            if(!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

//    private fun initViews() {
//        tilName = findViewById(R.id.til_name)
//        tilCount = findViewById(R.id.til_count)
//        etName = findViewById(R.id.et_name)
//        etCount = findViewById(R.id.et_count)
//        buttonSave = findViewById(R.id.save_button)
//    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }

    override fun onEditingFinished() {
        finish()
    }
}