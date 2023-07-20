package com.ubaya.uas_anmp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.uas_anmp.R
import com.ubaya.uas_anmp.databinding.FragmentTenantDetailBinding
import com.ubaya.uas_anmp.viewmodel.DetailViewModel

class TenantDetailFragment : Fragment(),ButtonWriteReviewClickListener {
    private lateinit var viewModel: DetailViewModel
    private lateinit var dataBinding: FragmentTenantDetailBinding
    private val tenantReviewAdapter= TenantReviewAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate<FragmentTenantDetailBinding>(inflater, R.layout.fragment_tenant_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
        dataBinding.listener = this

        viewModel= ViewModelProvider(this).get(DetailViewModel::class.java)
        var tenantId=""
        var tenantName=""
        if(arguments != null){
            tenantId= TenantDetailFragmentArgs.fromBundle(requireArguments()).tenantId
            tenantName= TenantDetailFragmentArgs.fromBundle(requireArguments()).tenantName
        }
        (activity as AppCompatActivity).supportActionBar?.title = tenantName

        viewModel.fetch(tenantId)
        viewModel.fetchReviews(tenantId)

        dataBinding.recViewReviewDetail.layoutManager= LinearLayoutManager(context)
        dataBinding.recViewReviewDetail.adapter= tenantReviewAdapter

        observeViewModel()


    }
    private fun observeViewModel() {
        viewModel.tenantsLD.observe(viewLifecycleOwner){
            var tenant= it
            dataBinding.tenant = tenant
        }

        viewModel.reviewsLD.observe(viewLifecycleOwner){
            tenantReviewAdapter.updateTenantReviewList(it)
        }
        viewModel.reviewsLoadError.observe(viewLifecycleOwner){

        }
        viewModel.reviewsloadingLD.observe(viewLifecycleOwner){
            if(it){
                dataBinding.recViewReviewDetail.visibility= View.GONE
            }else{
                dataBinding.recViewReviewDetail.visibility= View.VISIBLE
            }
        }
    }

    override fun onButtonWriteReviewClick(v: View) {
        var id = v.tag.toString()
        Log.d("testButton",id)
        val action= TenantDetailFragmentDirections.actionAddReviewFragment(id)
        Navigation.findNavController(v).navigate(action)
    }
}