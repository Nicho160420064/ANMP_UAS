package com.ubaya.uas_anmp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.uas_anmp.R
import com.ubaya.uas_anmp.databinding.TenantReviewItemBinding
import com.ubaya.uas_anmp.model.Review

class TenantReviewAdapter (val tenantReviewList: ArrayList<Review>) : RecyclerView.Adapter<TenantReviewAdapter.TenantReviewViewHolder>(){
    class TenantReviewViewHolder(var view: TenantReviewItemBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenantReviewViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<TenantReviewItemBinding>(inflater,
            R.layout.tenant_review_item, parent, false)
        return TenantReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: TenantReviewViewHolder, position: Int) {
        holder.view.review = tenantReviewList[position]
    }

    override fun getItemCount()= tenantReviewList.size

    fun updateTenantReviewList(newTenantReviewList: List<Review>){
        tenantReviewList.clear()
        tenantReviewList.addAll(newTenantReviewList)
        notifyDataSetChanged()
    }
}