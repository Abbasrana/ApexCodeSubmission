package com.apex.codeassesment.data

import com.apex.codeassesment.data.model.User
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.atomic.AtomicReference

interface IUserRepository {
    val savedUser: AtomicReference<User>

    fun getSavedUser(): Flow<DataState<User>>

    fun getUser(forceUpdate: Boolean): Flow<DataState<User>>

    fun getUsers(): Flow<DataState<List<User>>>
}