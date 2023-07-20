package com.ubaya.uas_anmp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tenants")
data class Tenant(
    @PrimaryKey(autoGenerate = false)
    var id:String,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "kategori")
    var kategori: String?,
    @ColumnInfo(name = "deskripsi")
    var deskripsi: String?,
    @ColumnInfo(name = "lokasi")
    var lokasi: String?,
    @ColumnInfo(name = "jamBuka")
    var jamBuka: String?,
    @ColumnInfo(name = "pembayaran")
    var pembayaran: String?,
    @ColumnInfo(name = "photoUrl")
    var photoUrl: String
)

@Entity(tableName = "reviews")
data class Review(
    @ColumnInfo(name = "date")
    var date: String?,
    @ColumnInfo(name = "star")
    var star: Float,
    @ColumnInfo(name = "comment")
    var comment: String?,
    @ColumnInfo(name = "accountId")
    var accountId: String?,
    @ColumnInfo(name = "tenantId")
    var tenantId: String
){
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
}

@Entity(tableName = "promo")
data class Promo(
    @PrimaryKey(autoGenerate = false)
    var idPromo:String,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "value")
    var value: String?,
    @ColumnInfo(name = "valiUntil")
    var validUntil: String?,
    @ColumnInfo(name = "tnc")
    var tnc: String?,
    @ColumnInfo(name = "photoUrl")
    var photoUrl: String?,
    @ColumnInfo(name = "tenantid")
    var tenantid:String?
)


@Entity(tableName = "account")
data class Account(
    @PrimaryKey(autoGenerate = false)
    var idAccount:String,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "noTelp")
    var noTelp: String?,
    @ColumnInfo(name = "email")
    var email: String?,
    @ColumnInfo(name = "tglLahir")
    var tglLahir: String?,
    @ColumnInfo(name = "photoUrl")
    var photoUrl: String?,
    @ColumnInfo(name = "member")
    var member: String?,
)
@Entity(tableName = "reservation")
data class Reservation(
    @ColumnInfo(name = "tenantName")
    var tenantName: String?,
    @ColumnInfo(name = "date")
    var date: String?,
    @ColumnInfo(name = "time")
    var time: String?,
    @ColumnInfo(name = "people")
    var people: String,
    @ColumnInfo(name = "reservationName")
    var reservationName: String?,
    @ColumnInfo(name = "contact")
    var contact: String?,
    @ColumnInfo(name = "status")
    var status: String?,
    @ColumnInfo(name = "accountId")
    var accountId: String?,
){
    @PrimaryKey(autoGenerate = true)
    var idReservation:Int = 0
}