package com.example.plannerkt.section_chat.chats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plannerkt.R
import com.example.plannerkt.adapters.ChatsAdapter
import com.example.plannerkt.halpers.OnItemClickListener
import com.example.plannerkt.models.Chat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_chat.view.*

class ChatFragment : Fragment() {

    val db = Firebase.firestore
    val chatList = ArrayList<Chat>()
    val list = ArrayList<String>()
    val fbUser = Firebase.auth.currentUser
    lateinit var adapter: ChatsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_chat, container, false)

        listenChats()
        initViews(root)
        initList(root)
        return root

    }

    private fun initViews(view:View) {

    }

    private fun initList(view: View) {
        adapter = ChatsAdapter(chatList,
        object :OnItemClickListener{
            override fun <T> onItemClick(listItem: T) {
//                startActivity(Intent(context, Messagesactivity::class.java))

            }

        })

        val layoutManager = LinearLayoutManager(context)
        view.recycler_chat.layoutManager = layoutManager
        view.recycler_chat.adapter = adapter


    }

    private fun listenChats() {
        val docRef = db.collection("chats")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
            }

            if (snapshot != null) {
                for (change in snapshot.documentChanges) {
                    when (change.type) {
                        DocumentChange.Type.ADDED -> {
                            list.clear()
                            val chat: Chat = change.document.toObject(Chat::class.java)
                            Log.e(TAG, "if didnt work")

                            if (chat.userName == fbUser?.uid){
                                chatList.add(0, chat)
                                Log.e(TAG, "chat.userName = fbUser.uid")
                            }

                        }
                        DocumentChange.Type.MODIFIED -> {
//                            val chat: Chat = change.document.toObject(Chat::class.java)
//                            chatList.add(0, chat)
                        }
                        DocumentChange.Type.REMOVED -> {

                        }
                    }
                }

            } else {
                Log.w(TAG, "snapshot == null", e)


            }
        }
    }
}