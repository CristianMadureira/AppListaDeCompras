package com.example.listadecompras.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun inserir(item: ItemEntity)

    @Update
    suspend fun atualizar(item: ItemEntity)

    @Query("DELETE from item WHERE id = :id" )
    suspend fun deletar(id: Long)

    @Query("SELECT * from item WHERE id = :id")
    fun getItem(id: Int): Flow<ItemEntity>

    @Query("SELECT * from item ORDER BY nome ASC")
    fun getItens() : Flow<List<ItemEntity>>
}