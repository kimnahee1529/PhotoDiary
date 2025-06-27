package com.todaylab.cleanarchtemplate.core

// todo: wrap data classes in sealed class
sealed class DataResource<out T> {
    class Success<T>(val data: T) : DataResource<T>()
    class Error(val throwable: Throwable) : DataResource<Nothing>()
    class Loading<T>(val data: T? = null) : DataResource<T>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun error(throwable: Throwable) = Error(throwable)
        fun <T> loading(data: T? = null) = Loading(data)
    }

    // override toString() for debug purpose
    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[throwable=$throwable]"
            is Loading -> "Loading[data=$data]"
        }
    }

    fun <K> mapData(transform: (T) -> K): DataResource<K> {
        return when (this) {
            is Success -> Success(transform(data))
            is Error -> Error(throwable)
            is Loading -> Loading(data?.let { transform(it) })
        }
    }
}