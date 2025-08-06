package ru.iyshcherbakov.dntestapp.data.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//Инициализация Hilt
@HiltAndroidApp
class NodeApp: Application()