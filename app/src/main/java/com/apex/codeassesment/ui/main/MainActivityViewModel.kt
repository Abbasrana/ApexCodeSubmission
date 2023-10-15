package com.apex.codeassesment.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apex.codeassesment.data.DataState
import com.apex.codeassesment.data.IUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val userRepository: IUserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<UiState>(UiState.Loading)
    val user: StateFlow<UiState> = _user

    fun getSavedUser() {
        viewModelScope.launch {
            userRepository.getSavedUser().collect() { dataState ->
                when (dataState) {
                    is DataState.Error -> _user.value = UiState.Error(dataState.message)
                    is DataState.Success -> _user.value = UiState.SuccessUser(dataState.data)
                }
            }
        }
    }

    fun getUser(forceUpdate: Boolean) {
        _user.value = UiState.Loading
        viewModelScope.launch {
            userRepository.getUser(forceUpdate).collect() { dataState ->
                when (dataState) {
                    is DataState.Error -> _user.value = UiState.Error(dataState.message)
                    is DataState.Success -> _user.value =UiState.SuccessUser(dataState.data)
                }
            }
        }
    }

    fun getUsers() {
        _user.value = UiState.Loading
        viewModelScope.launch {
            userRepository.getUsers().collect() { dataState ->
                when (dataState) {
                    is DataState.Error -> _user.value = UiState.Error(dataState.message)
                    is DataState.Success -> _user.value = UiState.SuccessUserList(dataState.data.toMutableList())
                }
            }
        }
    }
}