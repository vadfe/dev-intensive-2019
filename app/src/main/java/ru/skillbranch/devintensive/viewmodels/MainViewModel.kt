package ru.skillbranch.devintensive.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.extensions.shortFormat
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.models.data.ChatType
import ru.skillbranch.devintensive.models.data.UserItem
import ru.skillbranch.devintensive.repositories.ChatRepository
import ru.skillbranch.devintensive.utils.DataGenerator

class MainViewModel: ViewModel() {
    private val query = mutableLiveData("")
    private val chatRepository = ChatRepository
    private val chats = Transformations.map(chatRepository.loadChats()){chats ->
        val caa = chats.filter { !it.isArchived }
            .filter { it.title.contains(query.value!!, true)}
            .map { it.toChatItem() }
            .sortedBy { it.id.toInt() }

        return@map caa
    }
    private val chats_archive = chatRepository.archive()
    fun getChatData(): LiveData<List<ChatItem>> {
        //>>> add filter
        val  result = MediatorLiveData<List<ChatItem>>()
        val filterF = {
            val queryStr = query.value!!
            val chans_t = chats.value!!
            result.value = if(queryStr.isEmpty()) chans_t
            else chans_t.filter{ it.title.contains(queryStr, true) }
        }

        result.addSource(chats){filterF.invoke()}
        result.addSource(query){filterF.invoke()}
       // result.addSource(chats_archive)

        ///<<<
        return result
    }
    fun addToArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = true))

    }

    fun restoreFromArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = false))
    }

    fun handleSearchQuery(text: String?) {
        query.value = text
    }
}