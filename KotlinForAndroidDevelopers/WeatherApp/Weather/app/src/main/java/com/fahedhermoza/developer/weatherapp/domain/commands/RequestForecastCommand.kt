package com.fahedhermoza.developer.weatherapp.domain.commands

import com.fahedhermoza.developer.weatherapp.data.ForecastRequest
import com.fahedhermoza.developer.weatherapp.domain.mappers.ForecastDataMapper
import com.fahedhermoza.developer.weatherapp.domain.model.ForecastList

class RequestForecastCommand(private val zipCode: String) : Command<ForecastList> {
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
    }
}
