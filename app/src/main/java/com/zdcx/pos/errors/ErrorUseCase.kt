package com.zdcx.pos.errors

import com.zdcx.pos.data.error.Error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}
