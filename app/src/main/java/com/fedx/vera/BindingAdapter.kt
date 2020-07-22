package com.fedx.vera

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fedx.vera.network.GoodsProperty
import com.fedx.vera.ui.products.tableware.AdapterGoods

@BindingAdapter("imageUrl")
fun bindingImage(img: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUrl = imgUrl.toUri().buildUpon().scheme("http").build()
        Glide.with(img.context)
            .load(imgUrl)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(img)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<GoodsProperty>?){
    val adapter = recyclerView.adapter as AdapterGoods
    adapter.submitList(data)
}
