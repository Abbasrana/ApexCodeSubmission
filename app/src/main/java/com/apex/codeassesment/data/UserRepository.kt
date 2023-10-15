package com.apex.codeassesment.data

import com.apex.codeassesment.data.local.LocalDataSource
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

// TODO (2 points) : Add tests
// TODO (3 points) : Hide this class through an interface, inject the interface in the clients instead and remove warnings

class UserRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : IUserRepository {
    override val savedUser: AtomicReference<User>
        get() = AtomicReference(User())

    override fun getSavedUser() = flow<DataState<User>> {
        emit(DataState.success(localDataSource.loadUser()))
    }.catch {
        emit(DataState.error(it.message.toString()))
    }

    override fun getUser(forceUpdate: Boolean) = flow<DataState<User>> {
        if (forceUpdate) {
            remoteDataSource.LoadUser().collect() {
                when (it) {
                    is DataState.Error -> emit(DataState.error(it.message))
                    is DataState.Success -> {
                        localDataSource.saveUser(it.data)
                        savedUser.set(it.data)
                        emit(DataState.success(it.data))
                    }
                }
            }
        }
    }.catch {
        emit(DataState.error(it.message.toString()))
    }

    override fun getUsers() = flow<DataState<List<User>>> {
        remoteDataSource.loadUsers().collect() {
            when (it) {
                is DataState.Error -> emit(DataState.error(it.message))
                is DataState.Success -> emit(DataState.success(it.data))
            }
        }

    }.catch {
        emit(DataState.error(it.message.toString()))
    }

}
