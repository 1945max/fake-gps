package id.xxx.fake.gps.utils

interface IMapper<Input, Output> {
    fun map(input: Input): Output
}