package com.asierg.cqrspoc.shared.domain.model.valueObject

import java.time.Instant

abstract class InstantValueObject protected constructor(value: Instant) : SingleValueObject<Instant>(value)
