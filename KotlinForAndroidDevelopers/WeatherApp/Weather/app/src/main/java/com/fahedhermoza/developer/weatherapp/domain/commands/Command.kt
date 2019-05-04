package com.fahedhermoza.developer.weatherapp.domain.commands

public interface Command<out T> {
    fun execute(): T
}