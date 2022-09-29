package com.binar.latihan_networking.ui.adapter;

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.latihan_networking.databinding.ItemContentBinding
import com.binar.latihan_networking.model.GetAllCarResponseItem


class MainAdapter(private val onItemClick: OnClickListener) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<GetAllCarResponseItem>() {
        override fun areItemsTheSame(
            oldItem: GetAllCarResponseItem,
            newItem: GetAllCarResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GetAllCarResponseItem,
            newItem: GetAllCarResponseItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(value: List<GetAllCarResponseItem>?) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size


    inner class ViewHolder(
        private val binding: ItemContentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: GetAllCarResponseItem) {
            with(binding) {
                with(item) {
                    tvTitle.text = name
                    tvPrice.text = price.toString()
                    root.setOnClickListener { onItemClick.onClickItem(this) }
                }
            }
        }
    }

    interface OnClickListener {
        fun onClickItem(data: GetAllCarResponseItem)
    }

}