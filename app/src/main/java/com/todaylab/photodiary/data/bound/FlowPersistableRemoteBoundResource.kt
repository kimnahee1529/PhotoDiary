package ted.gun0912.movie.data.bound

import com.todaylab.photodiary.core.DataResource
import com.todaylab.photodiary.data.toDomainModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector

class FlowPersistableRemoteBoundResource<DomainType, DataType>(
    dataAction: suspend () -> DataType,
    private val localDataAction: suspend () -> DataType?,
    private val saveCacheAction: suspend (DataType) -> Unit,
) : FlowBaseBoundResource<DomainType, DataType>(dataAction) {

    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<DataResource<DomainType>>) {
        try {
            val localData: DomainType? =
                try {
                    localDataAction()?.toDomainModel()
                } catch (exception: Exception) {
                    null
                }
            collector.emit(DataResource.loading(localData))
            fetchFromSource(collector, saveCacheAction)
        } catch (exception: Exception) {
            collector.emit(DataResource.error(exception))
        }
    }

}
