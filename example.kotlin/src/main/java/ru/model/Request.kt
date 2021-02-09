package ru.model

import io.art.server.validation.Validatable
import io.art.server.validation.Validator
import io.art.server.validation.Validators.notNull

class Request(private val FModel: Model) : Validatable {
    override fun onValidating(validator: Validator) {
        validator.validate("FModel", FModel, notNull())
    }
}
