import com.todaylab.cleanarchtemplate.core.DataResource

/**
 * Repository interface for singleton data on a generic type [T].
 */
interface BaseSingletonRepository<T> {
    suspend fun get(): DataResource<T>
    suspend fun save(item: T): Boolean
    suspend fun delete(): Boolean
}