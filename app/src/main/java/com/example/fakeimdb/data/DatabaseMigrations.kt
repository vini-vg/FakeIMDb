import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import android.util.Log

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        Log.d("Migration", "Criando tabela favorite_movie")
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS favorite_movie (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                title TEXT NOT NULL,
                description TEXT NOT NULL,
                posterUrl TEXT NOT NULL
            )
            """
        )
        Log.d("Migration", "Tabela criada com sucesso")
    }
}
