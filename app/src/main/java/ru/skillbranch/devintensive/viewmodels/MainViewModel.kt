package ru.skillbranch.devintensive.viewmodels

import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.extensions.shortFormat
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.models.data.ChatType
import ru.skillbranch.devintensive.repositories.ChatRepository
import ru.skillbranch.devintensive.utils.DataGenerator



class MainViewModel: ViewModel() {
    private val query = mutableLiveData("")
    private val chatRepository = ChatRepository
    private val chats = Transformations.map(chatRepository.loadChats()){chats ->
        var archive = chats.filter { it.isArchived }
        if(archive.isNotEmpty()){
            var chatsList = mutableListOf<ChatItem>()
            chatsList.add(0,addItemArchive(archive))
            chatsList.addAll(chats.filter { !it.isArchived }.map { it.toChatItem() })
            return@map chatsList
        }
        else {
        return@map chats
            .map{ it.toChatItem() }
            .sortedBy { it.id.toInt() }}
    }

    private fun addItemArchive(archive: List<Chat>): ChatItem {
        val unreadableMessage = archive.fold(0){total, chat -> total+chat.unreadableMessageCount()}

        val lastChat:Chat =
            if (archive.none { it.unreadableMessageCount() != 0 }) archive.last() else
            archive.filter { it.unreadableMessageCount() !=0 }.maxBy { it.lastMessageDate()!! }!!
        return ChatItem(
            "-1",
            null,
            "??",
            "Архив чатов",
            lastChat.lastMessageShort().first,
            unreadableMessage,
            lastChat.lastMessageDate()?.shortFormat(),
            false,
            ChatType.ARCHIVE,
            lastChat.lastMessageShort().second

        )

    }

    fun getChatData(): LiveData<List<ChatItem>> {

        
        val result = MediatorLiveData<List<ChatItem>>()

        val filterF = {
            val queryStr = query.value!!
            val chats = chats.value!!

            result.value = if(queryStr.isEmpty()) chats
            else chats.filter { it.title.contains(queryStr,true) }
        }
        result.addSource(chats){filterF.invoke()}
        result.addSource(query){filterF.invoke()}
        return result
    }


    fun addToArchive(ChatId: String) {
        val chat = chatRepository.find(ChatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = true))
    }

    fun restoreFromArchive(ChatId: String){
        val chat = chatRepository.find(ChatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = false))
    }

    fun handleSearchQuery(text: String) {
        query.value = text
    }


}