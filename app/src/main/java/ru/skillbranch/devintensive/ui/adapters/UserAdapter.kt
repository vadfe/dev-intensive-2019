package ru.skillbranch.devintensive.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat_single.*
import kotlinx.android.synthetic.main.item_user_list.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.UserItem

class UserAdapter( val listener:(UserItem) -> Unit) :RecyclerView.Adapter<UserAdapter.UserViewHiolder>() {
    private var items: List<UserItem> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHiolder {
        val inflater = LayoutInflater.from(parent.context)
        val convertView = inflater.inflate(R.layout.item_user_list,parent,false)
        return UserViewHiolder(convertView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: UserViewHiolder, position: Int) = holder.bind(items[position], listener)

    fun updateData(data: List<UserItem>){
        val diffCallback = object : DiffUtil.Callback(){
            override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean = items[oldPos].id == data[newPos].id

            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = data.size

            override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean  = items[oldPos].hashCode() == data[newPos].hashCode()

        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = data
        diffResult.dispatchUpdatesTo(this)
    }

    inner class UserViewHiolder(convertView: View):RecyclerView.ViewHolder(convertView), LayoutContainer {
        override val containerView: View?
            get() = itemView

        fun bind(user:UserItem, listener: (UserItem) -> Unit){
            if(user.avatar != null){
                Glide.with(itemView)
                        .load(user.avatar)
                        .into(iv_avatar_user)
            }else{
                Glide.with(itemView)
                        .clear(iv_avatar_user)
                //iv_avatar_user.setInitials(user.initials?:"??")
            }
            sv_us_indicator.visibility = if(user.isOnline) View.VISIBLE else View.GONE
            tv_user_name.text = user.fullName
            tv_last_activity.text = user.lastActivity
            iv_selected.visibility = if(user.isSelected) View.VISIBLE else View.GONE
            itemView.setOnClickListener{listener.invoke(user)}
        }

    }
}