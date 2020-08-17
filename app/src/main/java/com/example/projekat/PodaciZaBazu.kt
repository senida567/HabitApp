package com.example.projekat

import android.content.Context
import android.util.Log
import androidx.work.WorkerParameters
import androidx.work.CoroutineWorker
import com.example.projekat.entity.*
import kotlinx.coroutines.coroutineScope

class PodaciZaBazu(db : AppDatabase){
    //context: Context,
   // workerParams: WorkerParameters
//) : CoroutineWorker(context, workerParams) {
   // override suspend fun doWork(): Result = coroutineScope {
    var db : AppDatabase

    fun onCreate() {
        try {
            //val db = AppDatabase.getInstance(applicationContext)

            db.tipoviAktivnostiDao().insert(TipoviAktivnosti(1, "Inkrementalne"))
            db.tipoviAktivnostiDao().insert(TipoviAktivnosti(2, "Kolicinske"))
            db.tipoviAktivnostiDao().insert(TipoviAktivnosti(3, "Vremenske"))

            db.kategorijeDao().insert(Kategorije(1, "Trčanje", 3, "Jutarnje/Večernje"))
            db.kategorijeDao().insert(Kategorije(2, "Doručak", 2, ""))
            db.kategorijeDao().insert(Kategorije(3, "Učenje", 1, ""))
            db.kategorijeDao().insert(Kategorije(4, "Tekućine", 1, ""))

            db.aktivnostiDao().insert(Aktivnosti(1, "Jutarnje trčanje", 1))
            db.glavneAktivnostiDao().insert(GlavneAktivnosti(1, "Jutarnje trčanje", 0, 0, 0,"min", 1))
            db.vremenskeDao().insert(Vremenske(1, 1, "", ""))
            db.mjerneJediniceDao().insert(MjerneJedinice(2, "min", 1))

            db.aktivnostiDao().insert(Aktivnosti(2, "Unos kalorija", 2))
            db.kolicinskeDao().insert(Kolicinske(1, 2, 200))
            db.mjerneJediniceDao().insert(MjerneJedinice(1, "kcal", 2))

            db.aktivnostiDao().insert(Aktivnosti(3, "Računarske mreže", 3))
            db.glavneAktivnostiDao().insert(GlavneAktivnosti(3, "Računarske mreže", 0, 1, 0,"sati",3))
            db.inkrementalneDao().insert(Inkrementalne(1, 3, 1, 0))
            db.mjerneJediniceDao().insert(MjerneJedinice(3, "sati", 3))

            db.aktivnostiDao().insert(Aktivnosti(4, "Voda", 4))
            db.inkrementalneDao().insert(Inkrementalne(2, 4, 1, 0))
            db.mjerneJediniceDao().insert(MjerneJedinice(4, "čaše", 4))
            Log.d("USPJESNO", "USPJESNO")
            //Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Greška!", ex)
            //Result.failure()
        }
    }

    init {
        this.db = db
    }

    companion object {
        private const val TAG = "PodaciZaBazu"
    }
}