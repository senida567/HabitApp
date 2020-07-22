package com.example.projekat.DAO

import androidx.room.*
import com.example.projekat.entity.Aktivnosti
import com.example.projekat.entity.TipoviAktivnosti

@Dao
interface AktivnostiDao {

    //suspend - da ne ometa glavu radnju prilikom izvšavanja
    //IGNORE - prije inserta pretrazi se tabela i ako ima kolona s aistim podacima ignorise unos
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(aktivnosti: Aktivnosti)

    //REPLACE - prije inserta pretrazi se tabela i ako ima kolona s aistim podacima (npr. id isti) zamijeni se
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(aktivnosti: Aktivnosti)

    @Query("SELECT * FROM aktivnosti")
    suspend fun getAll() : List<Aktivnosti>

    @Query("DELETE FROM aktivnosti")
    suspend fun deleteAll()

    @Query("DELETE FROM aktivnosti WHERE id = :id_A")
    suspend fun deleteId(id_A : Int)

    @Update
    suspend fun update(aktivnosti: Aktivnosti?)

    @Query("SELECT id FROM aktivnosti ORDER BY id DESC LIMIT 1")
    suspend fun getLastId() : Int

    @Query("SELECT * FROM aktivnosti WHERE id = :id_A")
    suspend fun getById(id_A : Int) : List<Aktivnosti>

    @Query("SELECT * FROM aktivnosti WHERE id_kategorije = :id_K")
    suspend fun getById_K(id_K : Int) : List<Aktivnosti>

}