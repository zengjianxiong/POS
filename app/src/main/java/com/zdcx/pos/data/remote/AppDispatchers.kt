package com.zeng.stationa.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher

class AppDispatchers(val main: MainCoroutineDispatcher, val io: CoroutineDispatcher)
