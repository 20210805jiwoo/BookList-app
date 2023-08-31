package dduwcom.mobile.finalreport

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.text.TextUtils
import android.widget.Toast
import dduwcom.mobile.finalreport.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    lateinit var updateBinding: ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateBinding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(updateBinding.root)

        val dto = intent.getSerializableExtra("dto") as BookDto
        val position = intent.getIntExtra("position", -1)
        val imageResId = getImageResourceId(dto.id)

        updateBinding.etNewId.setText(dto.id.toString())
        updateBinding.etNewTitle.setText(dto.title)
        updateBinding.etNewAuthor.setText(dto.author)
        updateBinding.etNewPublisher.setText(dto.publisher)
        updateBinding.etNewPrice.setText(dto.price)
        updateBinding.bookimage.setImageResource(imageResId)

        updateBinding.btnAdd.setOnClickListener {
            val newTitle = updateBinding.etNewTitle.text.toString().trim()
            val newAuthor = updateBinding.etNewAuthor.text.toString().trim()

            if (TextUtils.isEmpty(newTitle) || TextUtils.isEmpty(newAuthor)) {
                Toast.makeText(this, "책 제목과 저자를 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                dto.title = newTitle
                dto.author = newAuthor
                dto.publisher = updateBinding.etNewPublisher.text.toString()
                dto.price = updateBinding.etNewPrice.text.toString()

                if (updateBook(dto) > 0) {
                    setResult(RESULT_OK)
                    finish()
                } else {
                    setResult(RESULT_CANCELED)
                }
            }
        }

        updateBinding.btnCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    private fun getImageResourceId(id: Long): Int {
        return when (id) {
            1L -> R.drawable.book1
            2L -> R.drawable.book2
            3L -> R.drawable.book3
            4L -> R.drawable.book4
            5L -> R.drawable.book5
            else -> 0
        }
    }

    fun updateBook(dto: BookDto): Int {
        val helper = BookDBHelper(this)
        val db = helper.writableDatabase

        val updateValue = ContentValues().apply {
            put(BookDBHelper.COL_TITLE, dto.title)
            put(BookDBHelper.COL_AUTHOR, dto.author)
            put(BookDBHelper.COL_PUBLISHER, dto.publisher)
            put(BookDBHelper.COL_PRICE, dto.price)
        }

        val whereClause = "_id=?"
        val whereArgs = arrayOf(dto.id.toLong().toString())

        val resultCount =  db.update(BookDBHelper.TABLE_NAME,
            updateValue, whereClause, whereArgs)

        helper.close()
        return resultCount
    }
}