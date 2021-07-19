package com.kalfian.infokosan.models.notif

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "notif")
//Parcelable annotation to make parcelable object
@Parcelize
data class Notif(
    @PrimaryKey(autoGenerate = false)@ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "body") var body: String = "",
    @ColumnInfo(name = "user_id") var userId: Int = 0,
) : Parcelable