package com.apex.codeassesment.ui.main

import com.apex.codeassesment.data.model.User

sealed class UiState {
    object Loading : UiState()
    object Empty : UiState()
    data class SuccessUserList(val userList: MutableList<User>) : UiState()
    data class SuccessUser(val user: User) : UiState()
    data class Error(val message: String) : UiState()
}

