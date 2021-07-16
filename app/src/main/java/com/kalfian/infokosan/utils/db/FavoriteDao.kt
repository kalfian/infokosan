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

    @Query("SELECT * FROM favorite")
    fun getAll() : List<Favorite>

    @Query("SELECT * FROM favorite WHERE id = :id")
    fun getById(id: Int) : List<Favorite>
}