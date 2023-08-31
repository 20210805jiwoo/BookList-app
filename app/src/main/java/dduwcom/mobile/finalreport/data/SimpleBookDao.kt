package dduwcom.mobile.finalreport

class SimpleBookDao {
    val books = ArrayList<BookDto>()
    init {
        books.add (BookDto(1, R.drawable.book1,"구의 증명", "최진영", "은행나무", "10,800"))
        books.add (BookDto(2, R.drawable.book2,"불안한 사람들", "프레드릭 베크만", "다산책방", "14,220"))
        books.add (BookDto(3, R.drawable.book3,"어린 왕자", "앙투안 드 생텍쥐페리", "열린책들", "10,620"))
        books.add (BookDto(4, R.drawable.book4,"문명", "베르나르 베르베르", "열린책들", "13,320"))
        books.add (BookDto(5, R.drawable.book5,"바깥은 여름", "김애란", "문학동네", "11,000"))
    }
    fun getAllBooks() : ArrayList<BookDto> {
        return books
    }

    fun addNewBook(newBookDto: BookDto) {
        books.add(newBookDto)
    }

    fun modifyBook(pos: Int, modifyBookDto: BookDto) {
        books.set(pos, modifyBookDto)
    }

    fun removeBook(removeBookDto: BookDto) {
        val index = books.indexOf(removeBookDto)
        books.removeAt(index)
    }

    fun getBookByPos(pos: Int) = books.get(pos)
}