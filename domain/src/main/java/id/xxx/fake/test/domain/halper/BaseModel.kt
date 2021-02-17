package id.xxx.fake.test.domain.halper

interface BaseModel<ID : Any> {
    val id: ID?

    val isValid: Boolean
        get() = when (id) {
            is Number -> (id as Number).toInt() > 0
            is String -> (id as String).isNotBlank()
            else -> id != null
        }
}