package dduwcom.mobile.finalreport

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dduwcom.mobile.finalreport.databinding.ListViewBinding

class BookAdapter (val books : ArrayList<BookDto>)
    : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    val TAG = "BookAdapter"

    /*재정의 필수 - 데이터의 개수 확인이 필요할 때 호출*/
    override fun getItemCount(): Int = books.size

    /*재정의 필수 - 각 item view 의 view holder 생성 시 호출*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemBinding = ListViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(itemBinding, listener, lcListener)
    }

    /*재정의 필수 - 각 item view 의 항목에 데이터 결합 시 호출*/
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.itemBinding.tvTitle.text = books[position].title
        holder.itemBinding.tvAuthor.text =books[position].author
        if (books[position].photo == 1)
            holder.itemBinding.ivPhoto.setImageResource(R.drawable.book1)
        if(books[position].photo == 2)
            holder.itemBinding.ivPhoto.setImageResource(R.drawable.book2)
        if (books[position].photo == 3)
            holder.itemBinding.ivPhoto.setImageResource(R.drawable.book3)
        if(books[position].photo == 4)
            holder.itemBinding.ivPhoto.setImageResource(R.drawable.book4)
        if(books[position].photo == 5)
            holder.itemBinding.ivPhoto.setImageResource(R.drawable.book5)
    }

    class BookViewHolder(val itemBinding: ListViewBinding,
                         listener: OnItemClickListener?,
                         lcListener: OnItemLongClickListener?)
        : RecyclerView.ViewHolder(itemBinding.root) {
        init {
            /*RecyclerView 항목 클릭 시 외부 click 이벤트 리스너 호출*/
            itemBinding.root.setOnClickListener{
                listener?.onItemClick(it, adapterPosition)  // RecyclerView 항목 클릭 시 외부에서 지정한 리스너 호출
            }
            itemBinding.root.setOnLongClickListener{
                lcListener?.onItemLongClick(it, adapterPosition)
                true
            }
        }
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int)
    }

    var lcListener : OnItemLongClickListener? = null

    fun setOnItemLongClickListener(listener: OnItemLongClickListener?) {
        this.lcListener = listener
    }


    interface OnItemClickListener {
        fun onItemClick(view : View, position : Int)
    }

    var listener : OnItemClickListener? = null  // listener 를 사용하지 않을 때도 있으므로 null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }
}



