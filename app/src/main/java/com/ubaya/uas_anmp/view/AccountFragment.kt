package com.ubaya.uas_anmp.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.ubaya.uas_anmp.R
import com.ubaya.uas_anmp.databinding.FragmentAccountBinding
import com.ubaya.uas_anmp.model.Account
import com.ubaya.uas_anmp.viewmodel.DetailViewModel
import kotlinx.coroutines.launch

class AccountFragment : Fragment() , EditAccountClickListener{
    private lateinit var viewModel: DetailViewModel
    private lateinit var dataBinding: FragmentAccountBinding

    companion object{
        val SHARED_ACCOUNT_ID="SHARED_ACCOUNT_ID"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dataBinding.listener = this
//        var totalReservasi=0
        viewModel= ViewModelProvider(this).get(DetailViewModel::class.java)

        //Retrieve the saved account id
        var sharedId = context?.packageName
        var shared = context?.getSharedPreferences(sharedId, Context.MODE_PRIVATE)
        var accountId = shared?.getString(SHARED_ACCOUNT_ID, null)

        var totalReservasi=0

        viewLifecycleOwner.lifecycleScope.launch {
            totalReservasi= viewModel.countReservation(accountId.toString())
            Log.d("totalReservasi", totalReservasi.toString())
            var member=""
            if(Integer.parseInt(totalReservasi.toString()) < 5)
                member= "Silver"
            else if((Integer.parseInt(totalReservasi.toString()) >= 5) && (Integer.parseInt(totalReservasi.toString()) < 10))
                member= "Gold"
            else
                member= "Platinum"

            Log.d("Member", member)

            viewModel.updateMember(member, accountId.toString())

            dataBinding.txtMember.text = "Member "+member
        }

        viewModel.fetchAccount(accountId.toString())

        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.accountsLD.observe(viewLifecycleOwner) { account ->
            // Set the account object to the data binding variable
            dataBinding.account = account
        }
    }

    override fun onButoonEditAccountClickListener(v: View, obj: Account) {
        viewModel.editPProfile(obj)
        Toast.makeText(v.context, "Account updated", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(v).popBackStack()
    }
}