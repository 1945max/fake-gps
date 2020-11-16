package id.xxx.fake.gps.utils

interface IMapper<I, O> {
    fun map(input: I): O
}

interface IMapperNullable<I, O> {
    fun map(input: I?): O?
}

interface IMapperList<I, O> : IMapper<List<I>, List<O>>

interface IMapperListNullable<I, O> : IMapper<List<I>?, List<O>>