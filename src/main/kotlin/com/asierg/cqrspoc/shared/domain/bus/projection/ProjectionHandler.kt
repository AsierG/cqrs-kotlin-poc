package com.asierg.cqrspoc.shared.domain.bus.projection

interface ProjectionHandler<T : Projection> {

    fun handle(projection: T)
}
