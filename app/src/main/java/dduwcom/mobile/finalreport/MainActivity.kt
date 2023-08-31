//과제명: 도서 관리 앱
//분반: 01분반
//학번: 20210805
//제출일: 2023년 6월 23일
package dduwcom.mobile.finalreport

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import dduwcom.mobile.finalreport.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    val REQ_ADD = 100
    val REQ_UPDATE = 200

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: BookAdapter
    lateinit var books: ArrayList<BookDto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvBooks.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        books = getAllBooks()
        adapter = BookAdapter(books)
        binding.rvBooks.adapter = adapter

        val onClickListener = object : BookAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                intent.putExtra("dto", books.get(position) )
                intent.putExtra("position", position)
                startActivityForResult(intent, REQ_UPDATE)
            }
        }

        adapter.setOnItemClickListener(onClickListener)

        val onLongClickListener = object: BookAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int) {
                showDeleteConfirmationDialog(position)
            }
        }
        adapter.setOnItemLongClickListener(onLongClickListener)
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_UPDATE -> {
                refreshList(resultCode)
            }
            REQ_ADD -> {
                refreshList(resultCode)
            }
        }
    }

    private fun refreshList(resultCode: Int) {
        if (resultCode == RESULT_OK) {
            books.clear()
            books.addAll(getAllBooks())
            adapter.notifyDataSetChanged()
//        } else {
//            Toast.makeText(this@MainActivity, "취소됨", Toast.LENGTH_SHORT).show()
//        }
        }
    }

    @SuppressLint("Range")
    fun getAllBooks(): ArrayList<BookDto> {
        val helper = BookDBHelper(this)
        val db = helper.readableDatabase
        val cursor = db.query(BookDBHelper.TABLE_NAME, null, null, null, null, null, null)

        val books = arrayListOf<BookDto>()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndex(BaseColumns._ID))
                val photo = getInt(getColumnIndex(BookDBHelper.COL_PHOTO))
                val title = getString(getColumnIndex(BookDBHelper.COL_TITLE))
                val author = getString(getColumnIndex(BookDBHelper.COL_AUTHOR))
                val publisher =getString(getColumnIndex(BookDBHelper.COL_PUBLISHER))
                val price = getString(getColumnIndex(BookDBHelper.COL_PRICE))
                val dto = BookDto(id, photo, title, author, publisher, price)
                books.add(dto)
            }
        }
        cursor.close()
        helper.close()
        return books
    }

    fun deleteBook(id: Long) : Int {
        val helper = BookDBHelper(this)
        val db = helper.writableDatabase

        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(id.toString())

        val result = db.delete(BookDBHelper.TABLE_NAME, whereClause, whereArgs)

        helper.close()
        return result
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item03 -> {
                showExitConfirmDialog()
                return true
            }
            R.id.item02 -> {
                val intent = Intent(this, IntroductionActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.item01 -> {
                val intent = Intent(this, AddActivity::class.java)
                startActivityForResult(intent, REQ_ADD)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun showDeleteConfirmationDialog(position: Int) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.apply {
            setTitle("도서 삭제")
            setMessage("${books[position]}을(를) 삭제하시겠습니까?")
            setPositiveButton("삭제") { _, _ ->
                if (deleteBook(books[position].id) > 0) {
                    refreshList(RESULT_OK)
//                    Toast.makeText(this@MainActivity, "삭제 완료", Toast.LENGTH_SHORT).show()
                }
            }
            setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
        }
        alertDialogBuilder.create().show()
    }

    private fun showExitConfirmDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.apply {
            setTitle("앱 종료")
            setMessage("앱을 종료하시겠습니까?")
            setPositiveButton("종료") { _, _ ->
                finish()
            }
            setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
        }
        alertDialogBuilder.create().show()
    }
}