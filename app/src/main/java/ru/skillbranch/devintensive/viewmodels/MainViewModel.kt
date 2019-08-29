package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.repositories.ChatRepository
import ru.skillbranch.devintensive.utils.DataGenerator

class MainViewModel: ViewModel() {
    private val chatRepository = ChatRepository
    private val chats = mutableLiveData(loadChat())
    fun getChatData(): LiveData<List<ChatItem>> {
        return chats
    }

    private fun loadChat():List<ChatItem>{
        val chats = chatRepository.loadChats()
        return chats.map {it.toChatItem()}
                .sortedBy { it.id.toInt() }
    }

    fun addItems() {
        val newItem = DataGenerator.generateChatsWithOffset(chats.value!!.size, 5).map { it.toChatItem() }
        val copy = chats.value!!.toMutableList()
        copy.addAll(newItem)
        chats.value = copy.sortedBy { it.id.toInt() }
    }

}