package com.ubaya.uas_anmp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.RequestQueue
import com.ubaya.uas_anmp.model.*
import com.ubaya.uas_anmp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    private val job= Job()

    val tenantsLD= MutableLiveData<Tenant>()
    val tenantsLoadError= MutableLiveData<Boolean>()
    val loadingLD= MutableLiveData<Boolean>()
    val TAG= "volleyTag"
    private var queue: RequestQueue?= null

    val tenants2LD= MutableLiveData<Tenant>()
    val tenants2LoadError= MutableLiveData<Boolean>()
    val loading2LD= MutableLiveData<Boolean>()

    val promosLD= MutableLiveData<Promo>()
    val promosLoadError= MutableLiveData<Boolean>()
    val promosloadingLD= MutableLiveData<Boolean>()
    val PROMOTAG= "volleyTag"
    private var promoQueue: RequestQueue?= null

    val accountsLD= MutableLiveData<Account>()
    val accountsLoadError= MutableLiveData<Boolean>()
    val accountsloadingLD= MutableLiveData<Boolean>()
    val ACCOUNTTAG= "volleyTag"
    private var accountQueue: RequestQueue?= null

    val reviewsLD= MutableLiveData<List<Review>>()
    val reviewsLoadError= MutableLiveData<Boolean>()
    val reviewsloadingLD= MutableLiveData<Boolean>()
    val REVIEWTAG= "volleyTag"
    private var reviewsQueue: RequestQueue?= null

    fun addProfile(list:List<Account>){
        launch {
            Log.d("Input Account",list.toString())
            val db = buildDb(getApplication())
            db.ubayaKulinerDao().insertAllAccount(*list.toTypedArray())
        }
    }
    fun editPProfile(accounts: Account){
        launch {
            Log.d("Edit Account",accounts.toString())
            val db = buildDb(getApplication())
            db.ubayaKulinerDao().updateAccount(accounts)
        }
    }
    fun addReservation(list: List<Reservation>){
        launch {
            val db = buildDb(getApplication())
            db.ubayaKulinerDao().insertAllReserve(*list.toTypedArray())
        }
    }
    fun updateMember(member:String, id:String){
        launch {
            val db = buildDb(getApplication())
            db.ubayaKulinerDao().updateJumlahAcc(member,id)
        }
    }
    fun fetch(tenantId:String){
        tenantsLoadError.value= false
        loadingLD.value= true

        launch {
            val db = buildDb(getApplication())
            tenantsLD.value= db.ubayaKulinerDao().selectTenant(tenantId)
            Log.d("tenantld", tenantsLD.value.toString())
        }
        loadingLD.value= false
    }
    suspend fun countReservation(accountId: String):Int{
        var count:Int
        val db = buildDb(getApplication())
        count= db.ubayaKulinerDao().countReservation(accountId.toString())
        Log.d("countReservation:" , count .toString())
        return count
    }
    fun fetchPromo(promoId:String){
        promosLoadError.value = false
        promosloadingLD.value=true
        launch {
            val db = buildDb(getApplication())
            promosLD.value =  db.ubayaKulinerDao().selectPromo(promoId)
        }
        promosloadingLD.value = false
    }

    fun fetchAccount(accountId:String){
        accountsLoadError.value= false

        launch {
            val db = buildDb(getApplication())
            accountsLD.value =  db.ubayaKulinerDao().selectAccount(accountId)
            Log.d("AccountDetailViewModel:" , accountsLD.value.toString())
        }
    }

    fun addDataReview(list: List<Review>){
        launch {
            val db = buildDb(getApplication())
            db.ubayaKulinerDao().insertReviews(*list.toTypedArray())
        }
    }

    fun fetchReviews(tenantId:String){

        launch {
            val db = buildDb(getApplication())
            reviewsLD.value= db.ubayaKulinerDao().selectReviewsTenant(tenantId)
            Log.d("fetchreview", reviewsLD.value.toString())
        }
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
        promoQueue?.cancelAll(PROMOTAG)
        accountQueue?.cancelAll(ACCOUNTTAG)
        reviewsQueue?.cancelAll(REVIEWTAG)
    }

    override val coroutineContext: CoroutineContext
        get()= job  + Dispatchers.Main
}