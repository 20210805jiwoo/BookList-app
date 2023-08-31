package dduwcom.mobile.finalreport

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import dduwcom.mobile.finalreport.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    val TAG = "AddActivity"

    lateinit var addBinding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)
        addBinding.bookimage.setImageResource(R.drawable.book1)

        /*EditText에서 값을 읽어와 DB에 저장*/
        addBinding.btnAdd.setOnClickListener{
            val title = addBinding.etNewTitle.text.toString()
            val author = addBinding.etNewAuthor.text.toString()
            val publisher = addBinding.etNewPublisher.text.toString()
            val price = addBinding.etNewPrice.text.toString()
            val newDto = BookDto(0L, 1, title, author, publisher, price)

            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author)) {
                Toast.makeText(this, "책 제목과 저자를 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                if (addBook(newDto) > 0) {
                    setResult(RESULT_OK)
                } else {
                    setResult(RESULT_CANCELED)
                }
                finish()
            }
        }

        addBinding.btnCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    fun addBook(newDto: BookDto): Long {
        val helper = BookDBHelper(this)
        val db = helper.writableDatabase

        val newValues = ContentValues()
        newValues.put(BookDBHelper.COL_PHOTO, newDto.photo)
        newValues.put(BookDBHelper.COL_TITLE, newDto.title)
        newValues.put(BookDBHelper.COL_AUTHOR, newDto.author)
        newValues.put(BookDBHelper.COL_PUBLISHER, newDto.publisher)
        newValues.put(BookDBHelper.COL_PRICE, newDto.price)


        val result = db.insert(BookDBHelper.TABLE_NAME, null, newValues)

        helper.close()

        return result
    }
}