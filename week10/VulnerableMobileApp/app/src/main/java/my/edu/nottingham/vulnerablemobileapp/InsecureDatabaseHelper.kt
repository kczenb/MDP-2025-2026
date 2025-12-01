package my.edu.nottingham.vulnerablemobileapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class InsecureDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "vulnerable.db"
        const val DATABASE_VERSION = 1
        const val TABLE_USERS = "users"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Vulnerable: No parameterized query in table creation (though less critical)
        db.execSQL("CREATE TABLE $TABLE_USERS (id INTEGER PRIMARY KEY, username TEXT, password TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    // VULNERABLE: SQL Injection in raw query
    fun getUserByNameUnsafe(username: String) = readableDatabase.let { db ->
        // UNSAFE: Direct string concatenation
        val query = "SELECT * FROM $TABLE_USERS WHERE username = '$username'"
        db.rawQuery(query, null)  // SQL Injection vulnerability
    }

    // SECURE: Parameterized query
    fun getUserByNameSafe(username: String) = readableDatabase.let { db ->
        db.query(TABLE_USERS, null, "username = ?", arrayOf(username), null, null, null)
    }
}