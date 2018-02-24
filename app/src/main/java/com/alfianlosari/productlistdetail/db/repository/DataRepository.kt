package com.alfianlosari.productlistdetail.db.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer
import com.alfianlosari.productlistdetail.db.AppDatabase
import com.alfianlosari.productlistdetail.db.entity.CommentEntity
import com.alfianlosari.productlistdetail.db.entity.ProductEntity

class DataRepository(val database: AppDatabase) {

    var observableProducts: MediatorLiveData<List<ProductEntity>> = MediatorLiveData()
        private set

    val products: LiveData<List<ProductEntity>>
        get() = observableProducts

    init {
        observableProducts
                .addSource(database.productDao.loadAllProducts(),
                        object: Observer<List<ProductEntity>> {
                            override fun onChanged(productEntities: List<ProductEntity>?) {
                                if (database.isDatabaseCreated.value != null) {
                                    observableProducts.postValue(productEntities)
                                }
                            }
                        })
    }


    fun loadProduct(productId: Int): LiveData<ProductEntity>
        = database.productDao.loadProduct(productId)

    fun loadComments(productId: Int): LiveData<List<CommentEntity>>
        = database.commentDao.loadComments(productId)


    companion object {

        private var sInstance: DataRepository? = null

        fun instance(database: AppDatabase): DataRepository {
            if (sInstance == null) {
                synchronized(DataRepository) {
                    sInstance = DataRepository(database)
                }
            }
            return sInstance!!
        }



    }


}