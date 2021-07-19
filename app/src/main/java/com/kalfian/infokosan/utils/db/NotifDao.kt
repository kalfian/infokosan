package com.kalfian.infokosan.utils.db

import androidx.room.*
import com.kalfian.infokosan.models.notif.Notif

@Dao
interface NotifDao {

    @Insert
    fun insert(notif: Notif)

    @Update
    fun update(notif: Notif)

    @Delete
    fun delete(notif: Notif)

    @Query("SELECT * FROM notif WHERE user_id = :userId")
    fun getAll(userId: Int) : List<Notif>

    @Query("SELECT * FROM notif WHERE id = :id AND user_id = :userId")
    fun getById(id: Int, userId: Int) : List<Notif>
}