package ru.skillbranch.devintensive.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat_single.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.ChatItem

class ChatAdapter(val listener: (ChatItem) -> Unit) : RecyclerView.Adapter<ChatAdapter.SingleViewHolder>() {
    var items : List<ChatItem> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val convertView = inflater.inflate(R.layout.item_chat_single, parent, false)
        return SingleViewHolder(convertView)
    }

    override fun getItemCount(): Int = items.size

    fun updateData(data: List<ChatItem>){

        val diffCallback = object : DiffUtil.Callback(){
            override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean = items[oldPos].id == data[newPos].id

            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = data.size

            override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean  = items[oldPos].hashCode() == data[newPos].hashCode()

        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = data
        diffResult.dispatchUpdatesTo(this)
//        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SingleViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    inner class SingleViewHolder(convertView:View) : RecyclerView.ViewHolder(convertView), LayoutContainer, ItemTouchViewHolder{
        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemCleared() {
            itemView.setBackgroundColor(Color.WHITE)
        }

        override val containerView: View?
            get() = itemView

        fun bind(item:ChatItem, listener: (ChatItem) -> Unit){
            if(item.avatar == null){
                // iv_avatar_single.generateAvatar(item.initials)
            }
            else{
                //to do
            }
            sv_indicator.visibility = if(item.isOnline) View.VISIBLE else View.GONE
            with(tv_date_sinle){
                visibility = if(item.lastMessageDate != null) View.VISIBLE else View.GONE
                text = item.lastMessageDate
            }

            with(tv_counter_sinle){
                visibility = if(item.messageCount > 0) View.VISIBLE else View.GONE
                text = item.messageCount.toString()
            }

            tv_title_single.text = item.title
            tv_message_single.text = item.shortDescription

            itemView.setOnClickListener{
                listener.invoke(item)
            }
        }
    }
}