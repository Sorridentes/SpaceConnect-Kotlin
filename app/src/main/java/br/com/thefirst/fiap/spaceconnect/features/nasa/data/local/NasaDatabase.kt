package br.com.thefirst.fiap.spaceconnect.features.nasa.data.local

import androidx.room.Database
import androidx.room.Room
import android.content.Context
import androidx.room.RoomDatabase
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.dao.AstronomyDao
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.AstronomyEntity

@Database(
    entities = [AstronomyEntity::class],
    version = 1,
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