package my.edu.nottingham.vulnerablemobileapp

import android.content.ContentProvider
import android.content.ContentValues
import android.database.MatrixCursor
import android.net.Uri
import android.os.Environment
import java.io.File

class VulnerableContentProvider : ContentProvider() {

    private companion object {
        const val AUTHORITY = "com.example.vulnerableapp.provider"
        val COLUMNS = arrayOf("data")
    }

    override fun onCreate(): Boolean = true

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ) = MatrixCursor(COLUMNS).apply {
        // Vulnerable: Path traversal - direct use of user input
        selection?.let { userInput ->
            val file = File(Environment.getExternalStorageDirectory(), userInput)
            if (file.exists()) {
                addRow(arrayOf(file.absolutePath))
            }
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun getType(uri: Uri): String? = null
}