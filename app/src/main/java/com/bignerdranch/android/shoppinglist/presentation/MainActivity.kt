package com.bignerdranch.android.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.shoppinglist.R
import com.bignerdranch.android.shoppinglist.domain.ShopItem
import java.util.Objects

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecycleView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.shopList.observe(this) {

            adapter.shopList = it

        }
    }

    private fun setupRecycleView() {

        val rvshopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        adapter = ShopListAdapter()
        rvshopList.adapter = adapter
        rvshopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_ENABLED,
            ShopListAdapter.MAX_POOL_SIZE
        )
        rvshopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_DISABLED,
            ShopListAdapter.MAX_POOL_SIZE
        )
        setupLongCliclListener()

        setupClickListener()

        setupSwipeListener(rvshopList)
    }

    private fun setupSwipeListener(rvshopList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.shopList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }
        }
        val itemTouchHalper = ItemTouchHelper(callback)
        itemTouchHalper.attachToRecyclerView(rvshopList)
    }

    private fun setupClickListener() {
        adapter.onShopItemClickListener = {
            Log.d("Main", it.toString())
        }
    }

    private fun setupLongCliclListener() {
        adapter.onShopItemLongClickListener = {
            viewModel.changeEnabledState(it)
        }
    }
}