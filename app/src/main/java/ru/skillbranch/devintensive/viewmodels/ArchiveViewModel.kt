package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.repositories.ChatRepository

class ArchiveViewModel: ViewModel(){
    private val query = mutableLiveData("")
    private val chatRepository = ChatRepository
    private val archive = Transformations.map(chatRepository.loadChats()){ chats ->
        return@map chats.filter {it.isArchived}
            .map{ it.toChatItem() }
            .sortedBy { it.id.toInt() }
    }
    fun getArchiveData(): LiveData<List<ChatItem>> {


        val result = MediatorLiveData<List<ChatItem>>()

        val filterF = {
            val queryStr = query.value!!
            val archives = archive.value!!

            result.value = if(queryStr.isEmpty()) archives
            else archives.filter { it.title.contains(queryStr,true) }
        }
        result.addSource(archive){filterF.invoke()}
        result.addSource(query){filterF.invoke()}
        return result
    }



}