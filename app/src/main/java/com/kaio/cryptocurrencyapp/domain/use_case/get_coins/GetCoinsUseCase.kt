package com.kaio.cryptocurrencyapp.domain.use_case.get_coins

import com.kaio.cryptocurrencyapp.common.NetworkResponse
import com.kaio.cryptocurrencyapp.data.remote.dto.toCoin
import com.kaio.cryptocurrencyapp.domain.model.Coin
import com.kaio.cryptocurrencyapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<NetworkResponse<List<Coin>>> = flow {
        try {
            emit(NetworkResponse.Loading())
            val coins = repository.getCoins().map { it.toCoin() }
            emit(NetworkResponse.Success(coins))
        } catch (e: HttpException) {
            emit(NetworkResponse.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(NetworkResponse.Error(e.localizedMessage ?: "Couldn't reach server. Check your internet connection"))
        }
    }
}