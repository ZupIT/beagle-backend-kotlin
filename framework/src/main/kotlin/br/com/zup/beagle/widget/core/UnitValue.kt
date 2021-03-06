/*
 * Copyright 2020, 2022 ZUP IT SERVICOS EM TECNOLOGIA E INOVACAO SA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.zup.beagle.widget.core

import br.com.zup.beagle.widget.context.Bind
import br.com.zup.beagle.widget.context.constant

/**
 * Represents measurement values that contain both the numeric magnitude and the unit of measurement.
 * @property value the numeric measurement value.
 * @property type the unit of measurement.
 */
data class UnitValue(
    val value: Bind<Double>,
    val type: UnitType = UnitType.REAL
) {
    companion object {
        /**
         * convert the int to value based in platform
         * @return the unit value for real
         */
        fun real(real: Int) = UnitValue(constant(real.toDouble()), UnitType.REAL)

        /**
         * convert the int to value based in platform
         * @return the unit value for real
         */
        fun real(real: Double) = UnitValue(constant(real), UnitType.REAL)

        /**
         * convert the value based in percentage.
         * @return the unit value for percent
         */
        fun percent(percent: Int) = UnitValue(constant(percent.toDouble()), UnitType.PERCENT)

        /**
         * convert the value based in percentage.
         * @return the unit value for percent
         */
        fun percent(percent: Double) = UnitValue(constant(percent), UnitType.PERCENT)
    }
}

/**
 * This defines of a unit type;
 *
 * @property REAL
 * @property PERCENT
 * @property AUTO
 */
enum class UnitType {
    /**
     * Apply the value based in platform, like android this represent dp.
     */
    REAL,

    /**
     * Apply the value based in percentage.
     */
    PERCENT,

    /**
     * TODO.
     */
    AUTO
}
