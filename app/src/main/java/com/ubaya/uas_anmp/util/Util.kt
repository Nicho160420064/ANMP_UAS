package com.ubaya.uas_anmp.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ubaya.uas_anmp.R
import com.ubaya.uas_anmp.model.UbayaKulinerDatabase
import java.lang.Exception

fun ImageView.loadImage(url:String, progressBar: ProgressBar){
    Picasso.get()
        .load(url)
        .resize(400, 400)
        .centerCrop()
        .error(R.drawable.ic_baseline_error_24)
        .into(this, object: Callback {
            override fun onSuccess() {
                progressBar.visibility= View.GONE
            }

            override fun onError(e: Exception?) {
            }

        })
}

val DB_NAME = "ubayakulinerdb"

//build db
fun buildDb(context: Context): UbayaKulinerDatabase{
    val db= Room.databaseBuilder(context, UbayaKulinerDatabase::class.java, DB_NAME)
        .addMigrations(MIGRATION_1_2)
        .build()
    return db
}


@BindingAdapter("imageUrl", "progressBar")
fun loadPhotoURL(view: ImageView, url: String?, pb: ProgressBar) {
    if (url != null) {
        view.loadImage(url, pb)
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE account ADD COLUMN member STRING")
        database.execSQL(
            "ALTER TABLE reservation ADD COLUMN accountId STRING")
    }
}