package com.kalfian.infokosan.models.favorite

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favorite")
//Parcelable annotation to make parcelable object
@Parcelize
data class Favorite(
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "id_property") var idProperty: Int = 0,
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "image") var image: String = "",
    @ColumnInfo(name = "alamat") var alamat: String = "",
    @ColumnInfo(name = "harga") var harga: Int = 0,
    @ColumnInfo(name = "user_id") var userId: Int = 0
) : Parcelable