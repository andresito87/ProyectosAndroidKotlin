package com.google.codelabs.mdc.kotlin.shrine

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.NetworkImageView
import com.google.codelabs.mdc.kotlin.shrine.databinding.ShrProductCardBinding

class ProductCardViewHolder(binding: ShrProductCardBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var productImage: NetworkImageView = binding.productImage
    var productTitle: TextView = binding.productTitle
    var productPrice: TextView = binding.productPrice

}
