package com.zdcx.pos.data.dto.recipes


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@Entity
@JsonClass(generateAdapter = true)
@Parcelize
data class User(
    @PrimaryKey
    @Json(name = "email")
    val email: String = "",
    @Json(name = "latlng")
    val latlng: String = "",
    @Json(name = "name")
    val name: String = ""
) : Parcelable
