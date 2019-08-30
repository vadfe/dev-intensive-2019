package ru.skillbranch.devintensive.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
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

    override fun onBindViewHolder(holder: UserViewHiolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class UserViewHiolder(convertView: View):RecyclerView.ViewHolder(convertView), LayoutContainer {
        override val containerView: View?
            get() = itemView

        fun bind(user:UserItem, listener: (UserItem) -> Unit){

        }

    }
}