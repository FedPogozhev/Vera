package com.fedx.vera.ui.products.tableware


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fedx.vera.databinding.ItemGoodsBinding
import com.fedx.vera.network.GoodsProperty

class AdapterGoods: ListAdapter<GoodsProperty,
        AdapterGoods.GoodsPropertyViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterGoods.GoodsPropertyViewHolder {
        return GoodsPropertyViewHolder(ItemGoodsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AdapterGoods.GoodsPropertyViewHolder, position: Int) {
        val goodsProperty = getItem(position)
        holder.bind(goodsProperty)
    }
    companion object DiffCallback: DiffUtil.ItemCallback<GoodsProperty>(){
        override fun areItemsTheSame(oldItem: GoodsProperty, newItem: GoodsProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: GoodsProperty, newItem: GoodsProperty): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class GoodsPropertyViewHolder(private var binding: ItemGoodsBinding):
            RecyclerView.ViewHolder(binding.root){
        fun bind(goodsProperty: GoodsProperty){
            binding.property = goodsProperty
            binding.executePendingBindings()
        }
    }
}