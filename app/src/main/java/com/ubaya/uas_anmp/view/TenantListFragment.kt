package com.ubaya.uas_anmp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.uas_anmp.R
import com.ubaya.uas_anmp.databinding.FragmentTenantDetailBinding
import com.ubaya.uas_anmp.databinding.FragmentTenantListBinding
import com.ubaya.uas_anmp.model.Tenant
import com.ubaya.uas_anmp.viewmodel.ListViewModel

class TenantListFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private val tenantListAdapter= TenantListAdapter(arrayListOf())
    private lateinit var dataBinding: FragmentTenantListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentTenantListBinding.inflate(inflater, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Ubaya Kuliner"

        viewModel= ViewModelProvider(this).get(ListViewModel::class.java)

        // ADD Tenant (default)
        var tenant= Tenant("T001","Mcd", "Fast Food, Sweets, Snack", "McDonalds merupakan restoran fast food terbesar di dunia yang diawali pada tahun 1955 di California, Amerika Serikat. Dengan produk unggulan berupa Burger bernama Bigmac, McDonald’s hingga saat ini telah memiliki ribuan restoran yang tersebar di lebih dari 100 negara, salah satunya Indonesia.",
            "Jl. Tenggilis Mejoyo Blok AN No.21", "Senin-Minggu (06.00-23.00)", "Tunai, visa, master, debet", "https://mmc.tirto.id/image/otf/880x495/2021/06/08/bts-mela-twt_ratio-16x9.jpg")
        var tenant2=Tenant("T002", "Domino's Pizza", "Pizza and pasta", "Order online Domino's pizza, enjoy best pizza, pasta, chicken, and other promos for carryout or delivery. Free delivery and 30 minutes delivery guarantee.",
            "Jl. Raya Rungkut Mejoyo Blok AJ No.8", "Senin-Minggu (08.00-23.00)", "Tunai, visa, master, debet", "https://dom-repo-olo-dev.oss-ap-southeast-5.aliyuncs.com/promotion/PAPITRIOMOBILENoDanaTHUMBSR1.jpg")
        var tenant3= Tenant("T003", "PokPok Chicken","Snacks", "Sajian dari daging ayam fillet yang dipotong kecil-kecil tak beraturan, lalu digoreng dengan baluran tepung krispi.",
            "Jl. Rungkut Mejoyo Selatan AM No.7", "Senin-Minggu (09.00-22.00)", "Tunai, visa, master, debet", "https://pbs.twimg.com/media/ERS2tlxU4AA9NdV.jpg")
        var tenant4= Tenant("T004", "Haus", "Beverage, Snacks, Coffee", "Haus! Indonesia adalah gerai minuman yang menyediakan segala jenis minuman kekinian yang sedang hits dengan harga yang affordable. Saat ini Haus!",
            "Jl. Tenggilis Mejoyo Blok AN No.25", "Senin-Minggu (09.00-23.00)", "Tunai, OVO, Gopay", "https://katalogpromosi.com/wp-content/uploads/2021/06/haus_hebat_27062021p01.jpg")
        var tenant5= Tenant("T005", "Bakso Boedjangan", "Beverage, Snacks, Coffee", "Dengan racikan yang cukup kreatif, Bakso Boedjangan mampu menyajikan menu bakso tradisional yang dipadukan dengan resep-resep pilihan yang beberapa diantaranya bahkan dipadu dengan resep mancanegara seperti masakan Asia dan bahkan Eropa-Amerika.",
            "Jl. Raya Rungkut Mejoyo Blok AJ No.14", "Senin-Minggu (09.00-21.00)", "Tunai, visa, OVO, Gopay", "https://pbs.twimg.com/media/C-3wivCW0AAN0gp.jpg")

        var listTenant= listOf(tenant, tenant2, tenant3, tenant4, tenant5)
        viewModel.addDataTenant(listTenant)

        // SELECT Tenant
        viewModel.refresh()

        dataBinding.recView.layoutManager= LinearLayoutManager(context)
        dataBinding.recView.adapter=tenantListAdapter

        observeViewModel()

        dataBinding.refreshLayoutPromo.setOnRefreshListener {
            dataBinding.recView.visibility= View.GONE
            dataBinding.textError.visibility= View.GONE
            dataBinding.progressLoad.visibility= View.VISIBLE
            viewModel.refresh()
            dataBinding.refreshLayoutPromo.isRefreshing= false
        }
    }

    private fun observeViewModel() {
        viewModel.tenantsLD.observe(viewLifecycleOwner){
            tenantListAdapter.updateTenantList(it)
        }
        viewModel.tenantsLoadError.observe(viewLifecycleOwner){
            dataBinding.textError.visibility= if(it) View.VISIBLE else View.GONE
        }
        viewModel.loadingLD.observe(viewLifecycleOwner){
            if(it){
                dataBinding.recView.visibility= View.GONE
                dataBinding.progressLoad.visibility= View.VISIBLE
            }else{
                dataBinding.recView.visibility= View.VISIBLE
                dataBinding.progressLoad.visibility= View.GONE
            }
        }
    }
}