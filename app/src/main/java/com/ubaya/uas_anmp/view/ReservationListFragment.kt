package com.ubaya.uas_anmp.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.uas_anmp.R
import com.ubaya.uas_anmp.databinding.FragmentPromoListBinding
import com.ubaya.uas_anmp.databinding.FragmentReservationListBinding
import com.ubaya.uas_anmp.databinding.FragmentTenantListBinding
import com.ubaya.uas_anmp.model.Reservation
import com.ubaya.uas_anmp.viewmodel.ListViewModel

class ReservationListFragment : Fragment(),ButtonToAddReservationClickListener  {
    private lateinit var viewModel: ListViewModel
    private val reservationListAdapter = ReservationListAdapter(arrayListOf())
    private lateinit var binding: FragmentReservationListBinding

    companion object {
        val SHARED_ACCOUNT_ID = "SHARED_ACCOUNT_ID"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReservationListBinding.inflate(inflater, container, false)
        binding.listener = this // Set the click listener to the binding
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var sharedId = context?.packageName
        var shared = context?.getSharedPreferences(sharedId, Context.MODE_PRIVATE)
        var id = shared?.getString(AccountFragment.SHARED_ACCOUNT_ID, null)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refreshReservation(id.toString())


        binding.recViewReservation.layoutManager = LinearLayoutManager(context)
        binding.recViewReservation.adapter = reservationListAdapter

        observeViewModel()

        binding.refreshLayoutReservation.setOnRefreshListener {
            binding.recViewReservation.visibility = View.GONE
            binding.textErrorReservation.visibility = View.GONE
            binding.progressLoadReservationList.visibility = View.VISIBLE
            viewModel.refreshReservation(id.toString())
            binding.refreshLayoutReservation.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.reservationsLD.observe(viewLifecycleOwner) {
            reservationListAdapter.updateReservationList(it as ArrayList<Reservation>)
        }
        viewModel.reservationsLoadError.observe(viewLifecycleOwner) {
            binding.textErrorReservation.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.reservationsloadingLD.observe(viewLifecycleOwner) {
            if (it) {
                binding.recViewReservation.visibility = View.GONE
                binding.progressLoadReservationList.visibility = View.VISIBLE
            } else {
                binding.recViewReservation.visibility = View.VISIBLE
                binding.progressLoadReservationList.visibility = View.GONE
            }
        }
    }

    override fun onButtonToAddReservationClick(v: View) {
        val action = ReservationListFragmentDirections.actionAddreservationFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }
}