package id.xxx.fake.gps.utils

interface IMapper<I, O> {
    fun map(input: I): O
}