package com.ubaya.uas_anmp.model

import androidx.room.*


@Dao
interface UbayaKulinerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTenants(vararg tenant:Tenant)

    @Query("SELECT * FROM tenants")
    suspend fun selectAllTenants(): List<Tenant>

    @Query("SELECT * FROM tenants where id= :id")
    suspend fun selectTenant(id:String): Tenant

    @Delete
    suspend fun deleteTenants(tenant: Tenant)

    // Review
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(vararg review: Review)

    @Query("SELECT * FROM reviews where tenantId= :tenantId")
    suspend fun selectReviewsTenant(tenantId:String): List<Review>

    //Reservation
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllReserve(vararg reservation: Reservation)

    @Query("SELECT * FROM reservation where accountId= :accountId")
    suspend fun selectReservation(accountId:String):List<Reservation>

    @Query("SELECT COUNT('idReservation') FROM reservation WHERE accountId =:accountId")
    suspend fun countReservation(accountId:String):Int

    // Promo
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPromo(vararg promo: Promo)

    @Query("SELECT * FROM promo")
    suspend fun selectAllPromo():List<Promo>

    @Query("SELECT * FROM promo WHERE idPromo=:id")
    suspend fun selectPromo(id:String):Promo

    // Account
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAccount(vararg accounts: Account)

    @Query("SELECT * FROM account WHERE idAccount=:id")
    suspend fun selectAccount(id:String): Account

    @Update
    suspend fun updateAccount(account: Account)

    @Query("UPDATE account SET member=:memberAccount WHERE idAccount=:id")
    suspend fun updateJumlahAcc(memberAccount:String, id: String)
}