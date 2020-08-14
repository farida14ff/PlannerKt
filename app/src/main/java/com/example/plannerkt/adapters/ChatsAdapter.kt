package com.example.plannerkt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plannerkt.R
import com.example.plannerkt.listeners.OnItemClickListener
import com.example.plannerkt.models.Chat
import kotlinx.android.synthetic.main.item_chat.view.*

class ChatsAdapter(private val items: ArrayList<Chat>?,
                   private val onItemClickListener: OnItemClickListener
): RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        return ChatsViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat,parent,false))
    }

    override fun getItemCount(): Int {
        return items?.size!!
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        holder.bind(items!![position],onItemClickListener)
    }

    

    inner class ChatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val userName = itemView.user_name
        val lastMessageTime = itemView.last_message_time
        val lastMessageBody = itemView.last_message_body

        val imageViewMessageCounter = itemView.image_view_message_counter


        fun bind(chat: Chat, onItemClickListener: OnItemClickListener) {
            itemView.setOnClickListener{
                onItemClickListener.onItemClick(chat)
            }

            userName.text = chat.userName
            lastMessageTime.text = chat.lastMessageTime.toString()
            lastMessageBody.text = chat.lastMessage


        }

    }
}