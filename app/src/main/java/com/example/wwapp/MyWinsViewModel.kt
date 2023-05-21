package com.example.wwapp


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.wwapp.model.MyWinsDao
import com.example.wwapp.model.MyWinsItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyWinsViewModel @Inject constructor(private val myWinsDao: MyWinsDao) : ViewModel() {



    val wins = myWinsDao.getAllItems()
    val winChannel = Channel<WinEvents>()
    val winEvents = winChannel.receiveAsFlow()

    fun searchNote(query:String): Flow<List<MyWinsItems>>{
        //return myWinsDao.getSearchItems(query)
        val searchTerm = "%${query}%"
        return myWinsDao.getSearchItems(searchTerm)
    }


    fun insertWin(item:MyWinsItems) = viewModelScope.launch {
        myWinsDao.insertItem(item)
        winChannel.send(WinEvents.NavigateToMainScreen)
    }

    fun updateWin(item: MyWinsItems) = viewModelScope.launch {
        myWinsDao.updateItem(item)
        winChannel.send(WinEvents.NavigateToMainScreen)
    }
    fun deleteWin(item:MyWinsItems) = viewModelScope.launch {
        myWinsDao.deleteItem(item)
        winChannel.send(WinEvents.ShowUndoSnackBar("Note Deleted Successfully", item))
    }

    sealed class WinEvents{
        data class ShowUndoSnackBar(val msg:String, val item:MyWinsItems):WinEvents()
        object NavigateToMainScreen:WinEvents()
    }





}


