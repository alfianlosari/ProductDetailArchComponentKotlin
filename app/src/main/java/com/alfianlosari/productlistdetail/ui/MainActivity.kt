package com.alfianlosari.productlistdetail.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.alfianlosari.productlistdetail.R
import com.alfianlosari.productlistdetail.db.entity.ProductEntity
import com.alfianlosari.productlistdetail.ui.adapters.ProductAdapter
import com.alfianlosari.productlistdetail.viewmodel.ProductListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = ProductAdapter()
        products_list.layoutManager = LinearLayoutManager(this)
        products_list.adapter = adapter


        val viewModel = ViewModelProviders.of(this).get(ProductListViewModel::class.java)
        viewModel.products.observe(this, object: Observer<List<ProductEntity>> {
            override fun onChanged(productEntities: List<ProductEntity>?) {
                if (productEntities != null) {
                    adapter.swapProducts(productEntities)
                }
            }
        })


    }
}
