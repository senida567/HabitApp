package com.example.projekat.DAO

import androidx.room.*
import com.example.projekat.entity.Inkrementalne
import com.example.projekat.entity.TipoviAktivnosti

@Dao
interface InkrementalneDao {


    //suspend - da ne ometa glavu radnju prilikom izvšavanja
    //IGNORE - prije inserta pretrazi se tabela i ako ima kolona s aistim podacima ignorise unos
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(inkrementalne: Inkrementalne)

    //REPLACE - prije inserta pretrazi se tabela i ako ima kolona s aistim podacima (npr. id isti) zamijeni se
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(inkrementalne: Inkrementalne)

    @Query("SELECT * FROM inkrementalne")
    fun getAll() : List<Inkrementalne>

    @Query("DELETE FROM inkrementalne")
    suspend fun deleteAll()

    @Query("DELETE FROM inkrementalne WHERE id = :id_I")
    suspend fun deleteId(id_I : Int)

    @Update
    suspend fun update(inkrementalne: Inkrementalne?)

    @Query("SELECT id FROM inkrementalne ORDER BY id DESC LIMIT 1")
    fun getLastId() : Int

    @Query("SELECT * FROM inkrementalne WHERE id = :id_I")
    suspend fun getById(id_I : Int) : List<Inkrementalne>

    @Query("SELECT * FROM inkrementalne WHERE id_aktivnosti = :id")
    fun getByIdAktivnosti(id : Int) : Inkrementalne
}