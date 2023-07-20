package com.ubaya.uas_anmp.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.ubaya.uas_anmp.R
import com.ubaya.uas_anmp.databinding.ActivityMainBinding
import com.ubaya.uas_anmp.model.Account
import com.ubaya.uas_anmp.viewmodel.DetailViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityMainBinding


    companion object{
        val SHARED_ACCOUNT_ID="SHARED_ACCOUNT_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController=(supportFragmentManager.findFragmentById(R.id.fragmentHost) as NavHostFragment).navController
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

        binding.bottomNav.setupWithNavController(navController)

        viewModel= ViewModelProvider(this).get(DetailViewModel::class.java)
        var account1= Account("anna_","Anna", "081005102223", "ann_10@gmail.com", "10 Desember 1990", "https://images.unsplash.com/photo-1474978528675-4a50a4508dc3?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTd8fHByb2ZpbGV8ZW58MHx8MHx8&w=1000&q=80","")
        var account2 = Account("johnK28", "John","082661382109", "john28@gmail.com", "28 Maret 2001", "https://images.unsplash.com/photo-1529665253569-6d01c0eaf7b6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8cHJvZmlsZXxlbnwwfHwwfHw%3D&w=1000&q=80","")
        val listProfile = listOf(account1, account2)
        viewModel.addProfile(listProfile)

        var username=account1.idAccount

        // Save ID Account
        var sharedName = packageName
        var shared = getSharedPreferences(sharedName, Context.MODE_PRIVATE)
        var editor = shared.edit()
        editor.putString(SHARED_ACCOUNT_ID, username)
        editor.apply()

    }

    override fun onSupportNavigateUp():Boolean{
        return NavigationUI.navigateUp(navController, binding.drawerLayout) || super.onSupportNavigateUp()
    }

}