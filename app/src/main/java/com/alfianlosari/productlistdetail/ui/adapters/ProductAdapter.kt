package com.alfianlosari.productlistdetail.ui.adapters

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alfianlosari.productlistdetail.R
import com.alfianlosari.productlistdetail.model.Product
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter(): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    private var products: List<Product> = listOf()


    override fun getItemCount(): Int
        = products.size

    override fun onBindViewHolder(holder: ProductViewHolder?, position: Int) {
        holder?.bindProduct(products.get(position))
    }

    fun swapProducts(newProducts: List<Product>) {
        if (newProducts.isEmpty()) {
            this.products = newProducts
            notifyItemRangeInserted(0, 0)
        } else {
            val result = DiffUtil.calculateDiff(object: DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int)
                        = products.get(oldItemPosition).id == newProducts.get(newItemPosition).id

                override fun getOldListSize(): Int
                        = products.size

                override fun getNewListSize(): Int
                        = newProducts.size

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int)
                        = products.get(oldItemPosition).id == newProducts.get(newItemPosition).id

            })
            products = newProducts
            result.dispatchUpdatesTo(this)
        }

    }


    class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindProduct(product: Product) = with(product) {
            itemView.name.text = name
            itemView.price.text = "$price"
            itemView.desc.text = description

        }

    }


}