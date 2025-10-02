package ted.gun0912.movie.data.bound

import com.todaylab.photodiary.core.DataResource
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector

class FlowBoundResource<DomainType, DataType>(dataAction: suspend () -> DataType) :
    FlowBaseBoundResource<DomainType, DataType>(dataAction) {

    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<DataResource<DomainType>>) {
        collector.emit(DataResource.loading<DomainType>() as DataResource<DomainType>)
        fetchFromSource(collector)
    }

}
