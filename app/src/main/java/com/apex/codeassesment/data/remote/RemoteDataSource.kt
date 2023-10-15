package com.apex.codeassesment.data.remote

import com.apex.codeassesment.data.DataState
import com.apex.codeassesment.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

// TODO (2 points): Add tests
class RemoteDataSource @Inject constructor(private val apiAgent: ApiAgent) {

    // TODO (5 points): Load data from endpoint https://randomuser.me/api
    /*  fun LoadUser() = User.createRandom()*/

    fun LoadUser(): Flow<DataState<User>> {
        return flow<DataState<User>> {
            apiAgent.loadUser().let {
                if (it.isSuccessful && it.body() != null) {
                    it.body()?.let { response ->
                        emit(DataState.success(response.results[0]))
                    }
                } else {
                    emit(DataState.error(it.message()))
                }
            }
        }.catch {
            emit(DataState.error(it.message.toString()))
        }
    }

    // TODO (3 points): Load data from endpoint https://randomuser.me/api?results=10
    // TODO (Optional Bonus: 3 points): Handle succes and failure from endpoints
    /*fun loadUsers() = (0..10).map { User.createRandom() }*/

    fun loadUsers(): Flow<DataState<List<User>>> {
        return flow<DataState<List<User>>> {
            apiAgent.loadUsers().let {
                if (it.isSuccessful && it.body() != null) {
                    it.body()?.let { response ->
                        emit(DataState.success(response.results))
                    }
                } else {
                    emit(DataState.error(it.message()))
                }
            }
        }.catch {
            emit(DataState.error(it.message.toString()))
        }
    }
}
