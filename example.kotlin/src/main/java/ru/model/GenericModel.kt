package ru.model

data class GenericModel<F, S : GenericTypeParameter<F>>(
        val first: F,
        val second: S,
        val fistArray: Array<F>,
        val secondArray: Array<S>,
        val firstList: List<F>,
        val secondList: List<S>,
        val firstModel: GenericModel<F, S>,
        val firstModelArray: Array<GenericModel<F, S>>,
        val firstModelList: Array<List<GenericModel<F, S>>>,
        val firstModelSet: Array<Set<GenericModel<F, S>>>,
        val firstModelCollection: Array<Collection<GenericModel<F, S>>>,
        val firstModelMap: Array<Map<String, GenericModel<F, S>>>,
)
