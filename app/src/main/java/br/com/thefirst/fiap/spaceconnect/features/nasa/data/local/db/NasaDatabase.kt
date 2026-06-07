package br.com.thefirst.fiap.spaceconnect.features.nasa.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.local.dao.AstronomyDao
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.AstronomyEntity
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.AstronomyFavoriteEntity

@Database(
    entities = [AstronomyEntity::class, AstronomyFavoriteEntity::class],
    version = 2,  // Incrementar versão
    exportSchema = false
)
abstract class NasaDatabase : RoomDatabase() {

    abstract fun astronomyDao(): AstronomyDao

    companion object {
        @Volatile
        private var INSTANCE: NasaDatabase? = null

        fun getDatabase(context: Context): NasaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NasaDatabase::class.java,
                    "nasa_database"
                )
                    .fallbackToDestructiveMigration(false)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}