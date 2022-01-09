package com.nagwa.instabugchallenge.base.domain.interactor

abstract class UseCase<in Params> {
    abstract fun build(params: Params)
}

abstract class UseCaseWithReturn<in Params, Type> where Type : Any {

    abstract fun build(params: Params): Type
}