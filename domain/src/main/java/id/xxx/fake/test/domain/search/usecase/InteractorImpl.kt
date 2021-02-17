package id.xxx.fake.test.domain.search.usecase

import android.content.Context
import id.xxx.fake.test.domain.halper.Resource
import id.xxx.fake.test.domain.search.model.SearchModel
import id.xxx.fake.test.domain.search.repository.IRepository
import kotlinx.coroutines.flow.Flow

class InteractorImpl constructor(
    private val iSearchRepo: IRepository<SearchModel>
) : IInteractor {

    override fun getPlaceWithPagingData(query: String) =
        iSearchRepo.getPlaceWithPagingData(query)

    override fun getPlaces(query: String): Flow<Resource<List<SearchModel>>> =
        iSearchRepo.getPlaces(query)

    override fun getAddress(context: Context, value: String): Flow<Resource<SearchModel>> =
        iSearchRepo.getAddress(context, value)
}