package br.com.thefirst.fiap.spaceconnect.features.nasa.domain.usecase

import br.com.thefirst.fiap.spaceconnect.common.Resource
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.repository.AstronomyRepository
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.math.abs

class GetAstronomyListByDateUseCase(
    private val repository: AstronomyRepository
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String,
        forceRefresh: Boolean
    ): Resource<List<Astronomy>> {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US).apply { isLenient = false }

        if (startDate.isEmpty() || endDate.isEmpty()) {
            return Resource.Error("Data de início e fim não podem estar vazios.")
        }

        try {
            sdf.parse(startDate)
            sdf.parse(endDate)
        } catch (e: Exception) {
            return Resource.Error("As datas devem estar no formato YYYY-MM-DD e serem válidas.")
        }

        if (startDate > endDate) {
            return Resource.Error("Data de início deve ser anterior à data de fim.")
        }

        return repository.getAstronomyListByStartDateAndEndDate(startDate, endDate, forceRefresh)

    }
}