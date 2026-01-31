package com.google.codelabs.mdc.kotlin.shrine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.codelabs.mdc.kotlin.shrine.databinding.ShrProductCardBinding
import com.google.codelabs.mdc.kotlin.shrine.network.ImageRequester

import com.google.codelabs.mdc.kotlin.shrine.network.ProductEntry

/**
 * Adapter used to show a simple grid of products.
 */
class ProductCardRecyclerViewAdapter(private val productList: List<ProductEntry>) : RecyclerView.Adapter<ProductCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCardViewHolder {
        val binding = ShrProductCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductCardViewHolder, position: Int) {
        val product = productList[position]
        holder.productTitle.text = product.title
        holder.productPrice.text = product.price
        ImageRequester.setImageFromUrl(holder.productImage, product.url)
    }

    override fun getItemCount(): Int = productList.size
}
