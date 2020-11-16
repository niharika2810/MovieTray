package com.tmdb.movietray.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmdb.movietray.movies.common.data.source.local.store.UserPreferenceManager
import com.movietray.base.utils.Constants
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * @author Niharika
 **/
class HomeViewModel @ViewModelInject constructor(
    private val sessionManager: UserPreferenceManager
) : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName

    fun configureUserName() {
        viewModelScope.launch {
            sessionManager.getUserUserName()
                .collectLatest {
                    if(!it.isNullOrEmpty()) {
                        _userName.postValue(it)
                    }else{
                        _userName.postValue(Constants.DEFAULT_NAME)
                    }
                }
        }
    }

    fun setUserName(username: String) {
        viewModelScope.launch {
            sessionManager.setUserName(username)
        }
    }

}
