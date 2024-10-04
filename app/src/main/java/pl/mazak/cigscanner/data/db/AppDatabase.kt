package pl.mazak.cigscanner.data.db;

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.mazak.cigscanner.data.db.currency.Currency
import pl.mazak.cigscanner.data.db.currency.CurrencyDao
import pl.mazak.cigscanner.data.db.product.Product
import pl.mazak.cigscanner.data.db.product.ProductDao

@Database(entities = [Product::class, Currency::class], version = 3, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun currencyDao(): CurrencyDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }

}
