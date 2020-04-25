package ru.skillbranch.devintensive.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.UserItem

class UserAdapter(val listener: (UserItem) ->Unit): RecyclerView.Adapter<UserAdapter.UserViewHolder>(){
    private var items:List<UserItem> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val convertView = inflater.inflate(R.layout.item_user_list, parent, false)
        return UserViewHolder(convertView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) = holder.bind(items[position], listener)
    inner class UserViewHolder(convertView:View): RecyclerView.ViewHolder(convertView), LayoutContainer {
        override val containerView: View?
            get() = itemView

        fun bind(user: UserItem, listener: (UserItem) -> Unit){
            if (user.avatar != null){
                Glide.with(itemView)

            }
        }

    }
}