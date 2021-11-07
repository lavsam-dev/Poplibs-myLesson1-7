package lavsam.gb.libs.mylesson1

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import lavsam.gb.libs.mylesson1.database.AppDatabase
const val ROOM_NAME = "poplibs.db"
class App : Application() {

    companion object {
        lateinit var instance: App
    }

    //Временно до даггера положим это тут
    private val cicerone: Cicerone<Router> by lazy {
        Cicerone.create()
    }

    val navigatorHolder get() = cicerone.getNavigatorHolder()
    val router get() = cicerone.router
    val repository = GithubUsersRepo()
    private lateinit var db: AppDatabase

    fun getDB(): AppDatabase {
        return db
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        val value = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE User ADD COLUMN age INTEGER DEFAULT 0 NOT NULL")
            }
        }
        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            ROOM_NAME
        )
            .addMigrations(value)
            .build()
    }
}