package com.asierg.cqrspoc.shared.domain.model

import com.asierg.cqrspoc.shared.domain.model.valueObject.UuidBasedIdentity
import java.util.*

abstract class AggregateId(id: UUID) : UuidBasedIdentity(id)
