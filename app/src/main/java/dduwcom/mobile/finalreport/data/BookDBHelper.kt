package dduwcom.mobile.finalreport

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

class BookDBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    val TAG = "FoodDBHelper"

    companion object {
        const val DB_NAME = "book_db"
        const val TABLE_NAME = "book_table"
        const val COL_TITLE = "title"
        const val COL_AUTHOR = "author"
        const val COL_PHOTO = "photo"
        const val COL_PUBLISHER = "publisher"
        const val COL_PRICE = "price"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ( ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_TITLE TEXT, $COL_AUTHOR TEXT, $COL_PUBLISHER TEXT, $COL_PRICE TEXT, $COL_PHOTO INTEGER)"
        Log.d(TAG, CREATE_TABLE)    // SQL 문장이 이상 없는지 Log에서 확인 필요
        db?.execSQL(CREATE_TABLE)

        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '구의 증명', '최진영', '은행나무', '10,800', 1)")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '불안한 사람들', '프레드릭 베크만', '다산책방', '14,220', 2)")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '어린 왕자', '앙투안 드 생텍쥐페리', '열린책들', '10,620', 3)")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '문명', '베르나르 베르베르', '열린책들', '13,320', 4)")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '바깥은 여름', '김애란', '문학동네', '11,000', 5)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        val DROP_TABLE ="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }
}