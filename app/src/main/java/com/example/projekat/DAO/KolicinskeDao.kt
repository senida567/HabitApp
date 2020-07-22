package com.example.projekat.DAO

import androidx.room.*
import com.example.projekat.entity.Kolicinske
import com.example.projekat.entity.TipoviAktivnosti

@Dao
interface KolicinskeDao {

    //suspend - da ne ometa glavu radnju prilikom izvšavanja
    //IGNORE - prije inserta pretrazi se tabela i ako ima kolona s aistim podacima ignorise unos
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(kolicinske: Kolicinske)

    //REPLACE - prije inserta pretrazi se tabela i ako ima kolona s aistim podacima (npr. id isti) zamijeni se
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(kolicinske: Kolicinske)

    @Query("SELECT * FROM kolicinske")
    suspend fun getAll() : List<Kolicinske>

    @Query("DELETE FROM kolicinske")
    suspend fun deleteAll()

    @Query("DELETE FROM kolicinske WHERE id = :id_K")
    suspend fun deleteId(id_K : Int)

    @Update
    suspend fun update(kolicinske: Kolicinske?)

    @Query("SELECT id FROM kolicinske ORDER BY id DESC LIMIT 1")
    suspend fun getLastId() : Int

    @Query("SELECT * FROM kolicinske WHERE id = :id_K")
    suspend fun getById(id_K : Int) : List<Kolicinske>
}