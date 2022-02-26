package com.mcssoft.racedaycompose.domain.use_case

import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import javax.inject.Inject

class GetRaces @Inject constructor(
    private val iRepo: IDbRepo
) {

    operator fun invoke (mId: Long) {
        // TBA.
    }

}
