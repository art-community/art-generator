package ru.model

data class GenericModel<F, S : GenericTypeParameter<F>>(
        var first: F? = null,
        var second: S? = null,
        var fistArray: Array<F>? = null,
        var secondArray: Array<S>? = null,
        var firstList: List<F>? = null,
        var secondList: List<S>? = null,
        var firstModel: GenericModel<F, S>? = null,
        var firstModelArray: Array<GenericModel<F, S>>? = null,
        var firstModelList: Array<List<GenericModel<F, S>>>? = null,
        var firstModelSet: Array<Set<GenericModel<F, S>>>? = null,
        var firstModelCollection: Array<Collection<GenericModel<F, S>>>? = null,
        var firstModelMap: Array<Map<String, GenericModel<F, S>>>? = null,
)
