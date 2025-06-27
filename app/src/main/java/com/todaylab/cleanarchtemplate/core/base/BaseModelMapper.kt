package com.todaylab.cleanarchtemplate.core.base

/**
 * Base model mapper interface
 * @param High high level model
 * @param Low low level model
 */
interface BaseModelMapper<High, Low> {
    fun mapToHigh(low: Low): High
    fun mapToLow(high: High): Low

}