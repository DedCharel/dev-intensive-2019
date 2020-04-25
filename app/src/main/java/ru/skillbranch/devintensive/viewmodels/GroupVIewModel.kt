package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.data.UserItem
import ru.skillbranch.devintensive.repositories.GroupRepository

class GroupVIewModel: ViewModel() {
    private val groupRepository = GroupRepository
    private val userItems = mutableLiveData(loadUsers())
    private val selectedItem = Transformations.map(userItems){users -> users.filter { it.isSelected }}

    fun getUserData(): LiveData<List<UserItem>>{
        return userItems
    }

    fun getSelectedData(): LiveData<List<UserItem>> = selectedItem

    fun handleSelectedItem(userId: String){
        userItems.value = userItems.value!!.map {
            if (it.id == userId) it.copy(isSelected = !it.isSelected)
            else it
        }

    }

    fun handleRemoveChip(userId: String) {
        userItems.value = userItems.value!!.map {
            if (it.id == userId) it.copy(isSelected = false)
            else it
        }
    }

    private fun loadUsers(): List<UserItem> = groupRepository.loadUsers().map { it.toUserItem() }

}