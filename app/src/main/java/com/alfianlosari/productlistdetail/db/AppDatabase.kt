package com.alfianlosari.productlistdetail.db

import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.alfianlosari.productlistdetail.AppExecutors
import com.alfianlosari.productlistdetail.db.converter.DateConverter
import com.alfianlosari.productlistdetail.db.dao.CommentDao
import com.alfianlosari.productlistdetail.db.dao.ProductDao
import com.alfianlosari.productlistdetail.db.entity.CommentEntity
import com.alfianlosari.productlistdetail.db.entity.ProductEntity

@Database(entities = [
        ProductEntity::class,
        CommentEntity::class
    ], version = 1,
        exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase: RoomDatabase()  {

    abstract val productDao: ProductDao
    abstract val commentDao: CommentDao
    val isDatabaseCreated = MutableLiveData<Boolean>()

    companion object {
        val DATABASE_NAME = "basic-sample-db"
        private var sInstance: AppDatabase? = null

        fun instance(context: Context, executors: AppExecutors): AppDatabase {
            if (sInstance == null) {
                synchronized(AppDatabase) {
                    sInstance = buildDatabase(context.applicationContext, executors)
                    sInstance!!.updateDatabaseCreated(context.applicationContext)
                }
            }
            return sInstance!!
        }

        private fun buildDatabase(appContext: Context, executors: AppExecutors):AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
                    .addCallback(object: RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            executors.diskIO.execute {
                                addDelay()
                                val database = AppDatabase.instance(appContext, executors)
                                val products = DataGenerator.generateProducts()
                                val comments = DataGenerator.generateCommentsForProducts(products)
                                insertData(database, products, comments)
                                database.isDatabaseCreated.postValue(true)
                            }
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
        }

        private fun insertData(database: AppDatabase, products: List<ProductEntity>, comments: List<CommentEntity>) {
            database.runInTransaction {
                database.productDao.insertAll(products)
                database.commentDao.insertAll(comments)
            }
        }

        private fun addDelay() {
            Thread.sleep(4000)
        }

    }

    fun updateDatabaseCreated(context: Context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            isDatabaseCreated.postValue(true)
        }
    }

}