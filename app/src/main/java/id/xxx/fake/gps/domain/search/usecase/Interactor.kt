package id.xxx.fake.gps.domain.search.usecase

import android.content.Context
import id.xxx.data.source.map.box.Resource
import id.xxx.fake.gps.domain.search.model.SearchModel
import id.xxx.fake.gps.domain.search.repository.IRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class Interactor constructor(
    private val iSearchRepo: IRepository<SearchModel>
) : IInteractor {

    override fun getPlaceWithPagingData(value: String, scope: CoroutineScope) =
        iSearchRepo.getPlaceWithPagingData(value, scope)

    override fun getPlaces(value: String): Flow<Resource<List<SearchModel>>> =
        iSearchRepo.getPlaces(value)

    override fun getAddress(context: Context, value: String): Flow<Resource<SearchModel>> =
        iSearchRepo.getAddress(context, value)
}