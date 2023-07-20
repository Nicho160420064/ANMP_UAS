package com.ubaya.uas_anmp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.uas_anmp.model.*
import com.ubaya.uas_anmp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    private val job= Job()

    var tenants : ArrayList<Tenant> = ArrayList()
    val tenantsLD= MutableLiveData<List<Tenant>>()
    val tenantsLoadError= MutableLiveData<Boolean>()
    val loadingLD= MutableLiveData<Boolean>()
    val TAG= "volleyTag"
    private var queue: RequestQueue?= null


    val promosLD= MutableLiveData<List<Promo>>()
    val promosLoadError= MutableLiveData<Boolean>()
    val promosloadingLD= MutableLiveData<Boolean>()
    val PROMOTAG= "volleyTag"
    private var promoQueue: RequestQueue?= null

    val reservationsLD= MutableLiveData<List<Reservation>>()
    val reservationsLoadError= MutableLiveData<Boolean>()
    val reservationsloadingLD= MutableLiveData<Boolean>()
    val RESERVATIONTAG= "volleyTag"
    private var reservationQueue: RequestQueue?= null

    val reviewsLD= MutableLiveData<ArrayList<Review>>()
    val reviewsLoadError= MutableLiveData<Boolean>()
    val reviewsloadingLD= MutableLiveData<Boolean>()
    val REVIEWTAG= "volleyTag"
    private var reviewsQueue: RequestQueue?= null

    fun addDataTenant(list: List<Tenant>){
        launch {
            val db = buildDb(getApplication())
            db.ubayaKulinerDao().insertTenants(*list.toTypedArray())
        }
    }

    fun refresh(){
        tenantsLoadError.value= false
        loadingLD.value= true

        launch {
            val db = buildDb(getApplication())
            tenantsLD.value= db.ubayaKulinerDao().selectAllTenants()
            Log.d("tenat1:", tenantsLD.toString())
        }
        loadingLD.value= false
    }

    suspend fun getTenant(): List<Tenant> {
        tenantsLoadError.value= false
        loadingLD.value= true

        var tenants:List<Tenant> = emptyList()
        val db = buildDb(getApplication())
        tenants = db.ubayaKulinerDao().selectAllTenants()

        Log.d("tenantdb2", tenantsLD.toString())
        return tenants
    }

    fun clearTask(tenant: Tenant){
        launch {
            val db= Room.databaseBuilder(
                getApplication(),
                UbayaKulinerDatabase::class.java, "ubayakulinerdb"
            ).build()
            db.ubayaKulinerDao().deleteTenants(tenant)
            tenantsLD.value= db.ubayaKulinerDao().selectAllTenants()
        }
    }
    fun addPromo(list: List<Promo>){
        launch {
            val db = buildDb(getApplication())
            db.ubayaKulinerDao().insertAllPromo(*list.toTypedArray())
        }
    }
    fun refreshPromo(){
        promosLoadError.value = false
        promosloadingLD.value = true
        launch {
            val db = buildDb(getApplication())
            promosLD.value =  db.ubayaKulinerDao().selectAllPromo()
        }
        promosloadingLD.value=false
    }

    fun refreshReservation(accountId:String){
        reservationsLoadError.value= false
        reservationsloadingLD.value= true

        launch {
            val db = buildDb(getApplication())
            reservationsLD.value =  db.ubayaKulinerDao().selectReservation(accountId)
        }
        reservationsloadingLD.value=false
    }

    fun refreshReview(tenantId:String){
        reviewsLoadError.value= false
        reservationsloadingLD.value= true

        reservationQueue= Volley.newRequestQueue(getApplication())
        val url= "http://192.168.0.14/anmp/ubayaKuliner160419035.php?data=reviews&id=$tenantId"
        val stringRequest= StringRequest(
            Request.Method.GET, url,
            {
                val sType= object : TypeToken<ArrayList<Review>>(){}.type

                val result= Gson().fromJson<ArrayList<Review>>(it, sType)
                reviewsLD.value= result
                reviewsloadingLD.value= false
                Log.d("showvolleyreview", it)
            },
            {
                reservationsloadingLD.value= false
                reservationsLoadError.value= true
                Log.d("errorvolleyreview", it.toString())
            }
        ).apply {
            tag= "REVIEWTAG"
        }
        reviewsQueue?.add(stringRequest)
    }



    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
        promoQueue?.cancelAll(PROMOTAG)
        reservationQueue?.cancelAll(RESERVATIONTAG)
        reviewsQueue?.cancelAll(REVIEWTAG)
    }

    override val coroutineContext: CoroutineContext
        get()= job  + Dispatchers.Main
}