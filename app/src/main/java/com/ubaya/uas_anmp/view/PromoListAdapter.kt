package com.ubaya.uas_anmp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.uas_anmp.R
import com.ubaya.uas_anmp.databinding.PromoListItemBinding
import com.ubaya.uas_anmp.model.Promo

class PromoListAdapter (val promoList: ArrayList<Promo>) : RecyclerView.Adapter<PromoListAdapter.PromoViewHolder>(), ButtonDetailPromoClickListener{
    class PromoViewHolder(var view: PromoListItemBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromoViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<PromoListItemBinding>(inflater,
            R.layout.promo_list_item, parent, false)
        return PromoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PromoViewHolder, position: Int) {
        holder.view.promo = promoList[position]
        holder.view.listener = this
    }

    override fun getItemCount()= promoList.size

    fun updatePromoList(newPromoList: List<Promo>){
        promoList.clear()
        promoList.addAll(newPromoList)
        notifyDataSetChanged()
    }

    override fun onButtonDetailPromoClick(v: View) {
        val action= PromoListFragmentDirections.actionPromoDetailFragment(v.tag.toString())
        Navigation.findNavController(v).navigate(action)
    }
}