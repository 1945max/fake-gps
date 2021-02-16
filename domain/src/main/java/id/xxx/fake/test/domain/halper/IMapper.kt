package id.xxx.fake.test.domain.halper

interface IMapper<Input, Output> {
    fun map(input: Input): Output
}