package com.ubaya.uas_anmp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ubaya.uas_anmp.R
import com.ubaya.uas_anmp.databinding.FragmentPromoDetailBinding
import com.ubaya.uas_anmp.viewmodel.DetailViewModel

class PromoDetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var dataBinding: FragmentPromoDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate<FragmentPromoDetailBinding>(inflater, R.layout.fragment_promo_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(this).get(DetailViewModel::class.java)
        var promoId=""
        if(arguments != null){
            promoId= PromoDetailFragmentArgs.fromBundle(requireArguments()).promoId
        }
        viewModel.fetchPromo(promoId)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.promosLD.observe(viewLifecycleOwner){
            Log.d("itpromo", it.toString())
            dataBinding.promo = it

        }
    }

}