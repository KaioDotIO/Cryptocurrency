package com.kaio.cryptocurrencyapp.domain.use_case.get_coin

import com.kaio.cryptocurrencyapp.common.NetworkResponse
import com.kaio.cryptocurrencyapp.data.remote.dto.toCoinDetail
import com.kaio.cryptocurrencyapp.domain.model.CoinDetail
import com.kaio.cryptocurrencyapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(coinId: String): Flow<NetworkResponse<CoinDetail>> = flow {
        try {
            emit(NetworkResponse.Loading())
            val coin = repository.getCoinById(coinId).toCoinDetail()
            emit(NetworkResponse.Success(coin))
        } catch (e: HttpException) {
            emit(NetworkResponse.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(
                NetworkResponse.Error(
                    e.localizedMessage ?: "Couldn't reach server. Check your internet connection"
                )
            )
        }
    }
}