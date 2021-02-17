package id.xxx.fake.test.domain.search.usecase

import android.content.Context
import id.xxx.fake.test.domain.halper.Resource
import id.xxx.fake.test.domain.search.model.SearchModel
import id.xxx.fake.test.domain.search.repository.IRepository
import kotlinx.coroutines.flow.Flow

class InteractorImpl constructor(
    private val iSearchRepo: IRepository<SearchModel>
) : IInteractor {

    override fun getPlaceWithPagingData(value: String) =
        iSearchRepo.getPlaceWithPagingData(value)

    override fun getPlaces(value: String): Flow<Resource<List<SearchModel>>> =
        iSearchRepo.getPlaces(value)

    override fun getAddress(context: Context, value: String): Flow<Resource<SearchModel>> =
        iSearchRepo.getAddress(context, value)
}