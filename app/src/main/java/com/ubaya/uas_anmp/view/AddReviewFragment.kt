package com.ubaya.uas_anmp.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ubaya.uas_anmp.R
import com.ubaya.uas_anmp.databinding.FragmentAddReviewBinding
import com.ubaya.uas_anmp.model.Review
import com.ubaya.uas_anmp.viewmodel.DetailViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddReviewFragment : Fragment(), ButtonAddReviewClickListener {
    private lateinit var viewModel:DetailViewModel
    private lateinit var dataBinding: FragmentAddReviewBinding

    companion object{
        val SHARED_ACCOUNT_ID="SHARED_ACCOUNT_ID"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_review, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dataBinding.listener = this

        dataBinding.review = Review("", 0F,"","","")

        viewModel= ViewModelProvider(this).get(DetailViewModel::class.java)
        var tenantId=""
        if(arguments != null){
            tenantId= AddReviewFragmentArgs.fromBundle(requireArguments()).tenantId
        }
        viewModel.fetch(tenantId)

        observeViewModel()
    }
    private fun observeViewModel() {
        viewModel.tenantsLD.observe(viewLifecycleOwner){

            var tenant= it
            dataBinding.tenant = tenant
//            imageViewImageReview.loadImage(tenant.photoUrl.toString(), progressLoadImageAddReview)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onButtonAddReviewClick(v: View) {
        //Retrieve the saved account id
        var sharedId = context?.packageName
        var shared = context?.getSharedPreferences(sharedId, Context.MODE_PRIVATE)
        var id = shared?.getString(AccountFragment.SHARED_ACCOUNT_ID, null)

        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatted = current.format(formatter)

        dataBinding.review?.let {
            Log.d("check",v.tag.toString())
            val tag = v.tag.toString().split(",")
            val tenantId = tag[0]
            val accountid = id

            it.accountId = accountid
            it.tenantId = tenantId
            it.date = formatted
            var list = listOf(it)

//            Log.d("test",review.toString())
            viewModel.addDataReview(list)
            Toast.makeText(v.context, "Your review has been successfully added!", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(v).popBackStack()
        }
    }

}