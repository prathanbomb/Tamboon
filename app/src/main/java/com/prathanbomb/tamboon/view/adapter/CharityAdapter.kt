package com.prathanbomb.tamboon.view.adapter

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.prathanbomb.tamboon.R
import com.prathanbomb.tamboon.databinding.CharityItemBinding
import com.prathanbomb.tamboon.service.model.Charity
import com.prathanbomb.tamboon.view.callback.CharityClickCallback

/**
 * Created by prathanbomb on 9/14/2017 AD.
 */

class CharityAdapter(private val charityClickCallback: CharityClickCallback) : RecyclerView.Adapter<CharityAdapter.CharityViewHolder>() {

    private var charities: List<Charity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharityViewHolder {
        val binding = DataBindingUtil.inflate<CharityItemBinding>(LayoutInflater.from(parent.context), R.layout.charity_item, parent, false)
        binding.callback = charityClickCallback
        return CharityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharityViewHolder, position: Int) {
        holder.binding.charity = charities[position]
        holder.binding.executePendingBindings()
        Log.d("onBindViewHolder", charities.toString())
    }

    override fun getItemCount(): Int {
        return charities.size
    }

    fun setCharityList(charities: List<Charity>) {
        if (this.charities.isEmpty()) {
            this.charities = charities
            notifyItemRangeInserted(0, charities.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return this@CharityAdapter.charities.size
                }

                override fun getNewListSize(): Int {
                    return charities.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@CharityAdapter.charities[oldItemPosition].id === charities[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val charityNew = charities[newItemPosition]
                    val charityOld = charities[oldItemPosition]
                    return charityNew.id === charityOld.id && charityNew.id == charityOld.id
                }
            })
            this.charities = charities
            result.dispatchUpdatesTo(this)
        }
    }

    inner class CharityViewHolder(val binding: CharityItemBinding) : RecyclerView.ViewHolder(binding.root)
}
