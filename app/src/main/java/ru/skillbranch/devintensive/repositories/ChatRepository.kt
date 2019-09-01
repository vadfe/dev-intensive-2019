package ru.skillbranch.devintensive.repositories

import androidx.lifecycle.MutableLiveData
import ru.skillbranch.devintensive.data.managers.CacheManager
import ru.skillbranch.devintensive.extensions.shortFormat
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.models.data.ChatType
import ru.skillbranch.devintensive.models.data.User
import ru.skillbranch.devintensive.utils.DataGenerator

object ChatRepository {
    private val chats = CacheManager.loadChats()
    fun loadChats(): MutableLiveData<List<Chat>> {
        val copy = chats.value!!.toMutableList()
        return chats
    }

    fun update(chat: Chat) {
        val copy = chats.value!!.toMutableList()
        val ind = chats.value!!.indexOfFirst { it.id == chat.id }
        if(ind == -1) return
        copy[ind] = chat
        chats.value = copy
    }

    fun find(chatId: String): Chat? {
        val ind = chats.value!!.indexOfFirst { it.id == chatId }
        return chats.value!!.getOrNull(ind)
    }

    fun archive(): MutableList<Chat>? {

        val copy = chats.value!!.toMutableList()
        copy.clear()
        val arch = copy.filter { it.isArchived  }
        val last = arch.lastOrNull()
        if(last != null) {
            val last_autor = last.lastMessageShort().second
            val last_masg = last.lastMessageShort().first
            val last_msg_date  = last.lastMessageDate()
            val count_unrd_msg = arch.sumBy { it.unreadableMessageCount() }
            copy.add(Chat(
                "0",
                "title",
                last.members,
                last.messages,
                true
            ))
            /*copy.add(ChatItem(
                "0",
                null,
                "",
                 "title",
                last_masg,
                count_unrd_msg,
                last_msg_date?.shortFormat(),
                false,
                ChatType.ARCHIVE,
                last_autor
            ))*/
            return copy
        }
        return null
    }
}