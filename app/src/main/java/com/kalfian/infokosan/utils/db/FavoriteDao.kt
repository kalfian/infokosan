package com.kalfian.infokosan.utils.db

import androidx.room.*
import com.kalfian.infokosan.models.favorite.Favorite

@Dao
interface FavoriteDao {

    @Insert
    fun insert(favorite: Favorite)

    @Update
    fun update(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("SELECT * FROM favorite WHERE user_id = :userId")
    fun getAll(userId: Int) : List<Favorite>

    @Query("SELECT * FROM favorite WHERE id_property = :id AND user_id = :userId")
    fun getById(id: Int, userId: Int) : List<Favorite>
}