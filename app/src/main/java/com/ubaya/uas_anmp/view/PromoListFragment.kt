package com.ubaya.uas_anmp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.uas_anmp.R
import com.ubaya.uas_anmp.databinding.FragmentPromoListBinding
import com.ubaya.uas_anmp.databinding.FragmentTenantListBinding
import com.ubaya.uas_anmp.model.Promo
import com.ubaya.uas_anmp.viewmodel.ListViewModel


class PromoListFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private val promoListAdapter= PromoListAdapter(arrayListOf())
    private lateinit var binding: FragmentPromoListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPromoListBinding.inflate(inflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(this).get(ListViewModel::class.java)

        val promo1 = Promo("P001","Discount", "30%", "23 April 2022", "Discount 30% dengan minimum transaksi Rp 50.000 Berlaku untuk pembelian menu apapun Hanya berlaku untuk makan di tempat (dine-in)",
            "https://www.smithcoauctions.com/wp-content/uploads/2022/01/McDonalds-1.png", "T001")
        val promo2 = Promo("P002","Discount", "Rp 50.000", "10 Agustus 2022", "Discount Rp 50.000 dengan minimum transaksi Rp 200.000 Berlaku untuk pembelian menu apapun Hanya berlaku untuk makan di tempat (dine-in)",
            "https://www.smithcoauctions.com/wp-content/uploads/2022/01/McDonalds-1.png", "T001")
        val promo3 = Promo("P003","Discount", "Rp 50.000", "20 Juli 2022", "Discount Rp 50.000 dengan minimum transaksi Rp 100.000 Berlaku untuk pembelian menu apapun Hanya berlaku untuk makan di tempat (dine-in)",
            "https://jobnow247.com/wp-content/uploads/2022/01/unnamed-1.png", "T002")
        val promo4 = Promo("P004","Free", "1 Tofu Fries", "3 Juli 2022", "Free 1 Tofu Fries dengan minimum transaksi Rp 50.000 Berlaku untuk pembelian menu apapun Hanya berlaku untuk makan di tempat (dine-in)",
            "https://www.plaza-ambarrukmo.co.id/images/tenant/logo/16122775_223758831417951_5937679969030242304_n.jpg", "T001")
        val promo5 = Promo("P005","Discount", "10%", "23 Juli 2022", "Discount 10% dengan minimum transaksi Rp 34.000 Berlaku untuk pembelian menu apapun Hanya berlaku untuk makan di tempat (dine-in)",
            "https://cakapinterview.com/wp-content/uploads/2021/12/CK-PT-Inspirasi-Bisnis-Nusantara-Haus-Indonesia-696x419.jpg", "T001")

        val listPromo  = listOf(promo1, promo2, promo3, promo4, promo5)
        viewModel.addPromo(listPromo)

        viewModel.refreshPromo()

        binding.recViewPromo.layoutManager= LinearLayoutManager(context)
        binding.recViewPromo.adapter=promoListAdapter

        observeViewModel()

        binding.refreshLayoutPromo.setOnRefreshListener {
            binding.recViewPromo.visibility= View.GONE
            binding.textErrorPromo.visibility= View.GONE
            binding.progressLoadPromo.visibility= View.VISIBLE
            viewModel.refreshPromo()
            binding.refreshLayoutPromo.isRefreshing= false
        }
    }
    private fun observeViewModel() {
        viewModel.promosLD.observe(viewLifecycleOwner){
            promoListAdapter.updatePromoList(it as ArrayList<Promo>)
        }
        viewModel.promosLoadError.observe(viewLifecycleOwner){
            binding.textErrorPromo.visibility= if(it) View.VISIBLE else View.GONE
        }
        viewModel.promosloadingLD.observe(viewLifecycleOwner){
            if(it){
                binding.recViewPromo.visibility= View.GONE
                binding.progressLoadPromo.visibility= View.VISIBLE
            }else{
                binding.recViewPromo.visibility= View.VISIBLE
                binding.progressLoadPromo.visibility= View.GONE
            }
        }
    }
}