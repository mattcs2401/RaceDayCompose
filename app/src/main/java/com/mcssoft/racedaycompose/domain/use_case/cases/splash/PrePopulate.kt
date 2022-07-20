package com.mcssoft.racedaycompose.domain.use_case.cases.splash

import android.content.Context
import com.mcssoft.racedaycompose.R
import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.domain.model.Trainer
import com.mcssoft.racedaycompose.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONArray
import java.io.BufferedReader
import javax.inject.Inject

class PrePopulate @Inject constructor(
    private val iDbRepo: IDbRepo,
    private val context: Context
) {
    /**
     *
     */
    operator fun invoke(): Flow<DataResult<Boolean>> = flow {
        try {
            emit(DataResult.loading())

            val inputStream = context.resources.openRawResource((R.raw.trainers))
            val trainers = BufferedReader(inputStream.reader()).use { reader ->
                JSONArray(reader.readText())
            }
            for (ndx in 0 until trainers.length()) {
                val trainer = trainers.getJSONObject(ndx)
                val entity = Trainer(
                    shortName = trainer.getString("short-name"),
                    longName = trainer.getString("long-name"),
                    location = trainer.getString("location")
                )
                iDbRepo.insertTrainer(entity)
            }

            emit(DataResult.success(true))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}
