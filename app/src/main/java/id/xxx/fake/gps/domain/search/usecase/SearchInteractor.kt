package id.xxx.fake.gps.domain.search.usecase

import android.content.Context
import androidx.paging.PagingData
import id.xxx.fake.gps.domain.search.model.SearchModel
import id.xxx.fake.gps.domain.search.repository.ISearchRepository
import id.xxx.data.source.map.box.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class SearchInteractor constructor(
    private val iSearchRepository: ISearchRepository<SearchModel>
) : ISearchUseCase {
    override fun getPagingData(
        value: String, scope: CoroutineScope
    ): Flow<Resource<PagingData<SearchModel>>> =
        iSearchRepository.getPagingData(value, scope)


    override fun getPlaces(value: String): Flow<Resource<List<SearchModel>>> =
        iSearchRepository.getPlaces(value)

    override fun getAddress(context: Context, value: String): Resource<SearchModel> =
        iSearchRepository.getAddress(context, value)
}