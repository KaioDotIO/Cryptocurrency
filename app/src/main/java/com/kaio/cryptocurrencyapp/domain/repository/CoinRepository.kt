package com.kaio.cryptocurrencyapp.domain.repository

import com.kaio.cryptocurrencyapp.data.remote.dto.CoinDetailDto
import com.kaio.cryptocurrencyapp.data.remote.dto.CoinDto

interface CoinRepository {
    suspend fun getCoins(): List<CoinDto>

    suspend fun getCoinById(coinId: String): CoinDetailDto
}