package com.todaylab.cleanarchtemplate.core

/**
 * Data resource class for handling data state
 * - Success: success
 * - Empty: success, but empty data
 * - Loading: loading with optional data
 * - Error: error with throwable
 */
sealed class DataResource<out T> {
    class Success<T>(val data: T) : DataResource<T>()
    object Empty : DataResource<Nothing>()
    class Loading<T>(val data: T? = null) : DataResource<T>()
    class Error(val throwable: Throwable) : DataResource<Nothing>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun empty() = Empty
        fun error(throwable: Throwable) = Error(throwable)
        fun <T> loading(data: T? = null) = Loading(data)
    }

    fun getDataOrNull(): T? {
        return when (this) {
            is Success -> data
            is Loading -> data
            is Empty -> null
            is Error -> null
        }
    }

    fun <K> mapData(transform: (T) -> K): DataResource<K> {
        return when (this) {
            is Success -> Success(transform(data))
            is Empty -> Empty
            is Error -> Error(throwable)
            is Loading -> Loading(data?.let { transform(it) })
        }
    }

    // override toString() for debug purpose
    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Empty -> "Empty"
            is Loading -> "Loading[data=$data]"
            is Error -> "Error[throwable=$throwable]"
        }
    }
}