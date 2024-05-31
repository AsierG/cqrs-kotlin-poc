package com.asierg.cqrspoc.shared.domain.bus.projection

interface ProjectionBus {

    fun project(projection: Projection)
}
