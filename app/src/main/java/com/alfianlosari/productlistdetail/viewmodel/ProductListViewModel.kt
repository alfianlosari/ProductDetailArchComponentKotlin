package com.alfianlosari.productlistdetail.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.alfianlosari.productlistdetail.App
import com.alfianlosari.productlistdetail.db.entity.ProductEntity

class ProductListViewModel: ViewModel() {

    private val observableProducts: MediatorLiveData<List<ProductEntity>> = MediatorLiveData()
    val products: LiveData<List<ProductEntity>>
        get() = observableProducts


    init {
        observableProducts.value = null
        val products = App.instance.repository.products
        observableProducts.addSource(products, observableProducts::setValue)
    }

}