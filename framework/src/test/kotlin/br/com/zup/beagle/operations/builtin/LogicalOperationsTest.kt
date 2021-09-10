/*
 * Copyright 2020 ZUP IT SERVICOS EM TECNOLOGIA E INOVACAO SA
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

package br.com.zup.beagle.operations.builtin

import br.com.zup.beagle.widget.context.Bind
import br.com.zup.beagle.widget.context.constant
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@DisplayName("Given a Operation")
internal class LogicOperationTest {

    @DisplayName("When passing parameters to and operation")
    @Nested
    inner class AndOperationTest {

        @Test
        @DisplayName("Then should return correct expression")
        fun checkOneParameterToAnd() {
            // GIVEN
            val expected = Bind.Expression<Boolean>(value = "@{and(true,true)}")

            // WHEN
            val result = and(constant(true), constant(true))

            // THEN
            assertEquals(expected, result)
        }

        @Test
        @DisplayName("Then should return correct expression")
        fun checkThreeParametersToAnd() {
            // GIVEN
            val expected = Bind.Expression<Boolean>(value = "@{and(true,true,true)}")

            // WHEN
            val result = and(constant(true), constant(true), constant(true))

            // THEN
            assertEquals(expected, result)
        }

        @Test
        @DisplayName("Then should return correct expression")
        fun checkThreeParametersWithFalseToAnd() {
            // GIVEN
            val expected = Bind.Expression<Boolean>(value = "@{and(true,true,false)}")

            // WHEN
            val result = and(constant(true), constant(true), constant(false))

            // THEN
            assertEquals(expected, result)
        }
    }

    @DisplayName("When passing parameters to condition operation")
    @Nested
    inner class ConditionOperationTest {

        @Test
        @DisplayName("Then should return correct expression")
        fun checkTrueCondition() {
            // GIVEN
            val expected = Bind.Expression<Boolean>(value = "@{condition(true,true,false)}")

            // WHEN
            val result = condition(constant(true), constant(true), constant(false))

            // THEN
            assertEquals(expected, result)
        }

        @Test
        @DisplayName("Then should return correct expression")
        fun checkFalseCondition() {
            // GIVEN
            val expected = Bind.Expression<Boolean>(value = "@{condition(false,true,false)}")

            // WHEN
            val result = condition(constant(false), constant(true), constant(false))

            // THEN
            assertEquals(expected, result)
        }

    }

        @DisplayName("When passing parameters to not operation")
        @Nested
        inner class NotOperationTest {

            @Test
            @DisplayName("Then should return correct expression")
            fun checkOperationNotReturnFalse() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{not(true)}")

                // WHEN
                val result = not(constant(true))

                // THEN
                assertEquals(expected, result)
            }

            @Test
            @DisplayName("Then should return correct expression")
            fun checkOperationNotReturnTrue() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{not(false)}")

                // WHEN
                val result = not(constant(false))

                // THEN
                assertEquals(expected, result)
            }
        }

        @DisplayName("When passing parameters to or operation")
        @Nested
        inner class OrOperationTest {
            @Test
            @DisplayName("Then should return correct expression")
            fun checkOneParameterToOr() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{or(true,true)}")

                // WHEN
                val result = or(constant(true), constant(true))

                // THEN
                assertEquals(expected, result)
            }

            @Test
            @DisplayName("Then should return correct expression")
            fun checkThreeParametersToOr() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{or(true,true,true)}")

                // WHEN
                val result = or(constant(true), constant(true), constant(true))

                // THEN
                assertEquals(expected, result)
            }

            @Test
            @DisplayName("Then should return correct expression")
            fun checkThreeParametersToOrWithFalse() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{or(true,false,true)}")

                // WHEN
                val result = or(constant(true), constant(false), constant(true))

                // THEN
                assertEquals(expected, result)
            }

            @Test
            @DisplayName("Then should return correct expression")
            fun checkThreeParametersToOrWithTwoFalse() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{or(false,false,true)}")

                // WHEN
                val result = or(constant(false), constant(false), constant(true))

                // THEN
                assertEquals(expected, result)
            }
        }
    }

