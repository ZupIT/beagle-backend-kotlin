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
import kotlin.test.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Given a Operation")
internal class ComparisonOperationTest {

    @DisplayName("When passing parameters to eq operation")
    @Nested
    inner class Eq {

        @Test
        @DisplayName("Then should return correct expression")
        fun checkEqExpression() {
            // GIVEN
            val expected = Bind.Expression<Boolean>(value = "@{eq(1,2)}")

            // WHEN
            val result = eq(constant(1), constant(2))

            // THEN
            assertEquals(expected, result)
        }

        @Test
        @DisplayName("Then should return correct expression")
        fun checkEqDoubleExpression() {
            // GIVEN
            val expected = Bind.Expression<Boolean>(value = "@{eq(2.5,2.5)}")

            // WHEN
            val result = eq(constant(2.5), constant(2.5))

            // THEN
            assertEquals(expected, result)
        }

        @Test
        @DisplayName("Then should return correct expression")
        fun checkNotEqualsInt() {
            // GIVEN
            val expected = Bind.Expression<Boolean>(value = "@{eq(2,5)}")

            // WHEN
            val result = eq(constant(2), constant(5))

            // THEN
            assertEquals(expected, result)
        }

        @Test
        @DisplayName("Then should return correct expression")
        fun checkNotEqualsDouble() {
            // GIVEN
            val expected = Bind.Expression<Boolean>(value = "@{eq(2.5,5.0)}")

            // WHEN
            val result = eq(constant(2.5), constant(5.0))

            // THEN
            assertEquals(expected, result)
        }
    }

    @DisplayName("When passing parameters to gt operation")
    @Nested
    inner class GtOperationTest {

        @Test
        @DisplayName("Then should return correct expression")
        fun checkGreaterThanInteger() {
            // GIVEN
            val expected = Bind.Expression<Boolean>(value = "@{gt(10,5)}")

            // WHEN
            val result = gt(constant(10), constant(5))

            // THEN
            assertEquals(expected, result)
        }

        @Test
        @DisplayName("Then should return correct expression")
        fun checkGtEqualInteger() {
            // GIVEN
            val expected = Bind.Expression<Boolean>(value = "@{gt(2,2)}")

            // WHEN
            val result = gt(constant(2), constant(2))

            // THEN
            assertEquals(expected, result)
        }

        @Test
        @DisplayName("Then should return correct expression")
        fun checkGtNotEqualInteger() {
            // GIVEN
            val expected = Bind.Expression<Boolean>(value = "@{gt(5,10)}")

            // WHEN
            val result = gt(constant(5), constant(10))

            // THEN
            assertEquals(expected, result)
        }

        @Test
        @DisplayName("Then should return correct expression")
        fun checkGreaterThanDouble() {
            // GIVEN
            val expected = Bind.Expression<Boolean>(value = "@{gt(10.5,5.0)}")

            // WHEN
            val result = gt(constant(10.5), constant(5.0))

            // THEN
            assertEquals(expected, result)
        }

        @Test
        @DisplayName("Then should return correct expression")
        fun checkGtEqualDouble() {
            // GIVEN
            val expected = Bind.Expression<Boolean>(value = "@{gt(2.0,2.0)}")

            // WHEN
            val result = gt(constant(2.0), constant(2.0))

            // THEN
            assertEquals(expected, result)
        }

        @Test
        @DisplayName("Then should return correct expression")
        fun checkGtNotEqualDouble() {
            // GIVEN
            val expected = Bind.Expression<Boolean>(value = "@{gt(5.0,10.0)}")

            // WHEN
            val result = gt(constant(5.0), constant(10.0))

            // THEN
            assertEquals(expected, result)
        }
    }

        @DisplayName("When passing parameters to gte operation")
        @Nested
        inner class GteOperationTest {

            @Test
            @DisplayName("Then should return correct expression")
            fun checkGteInteger() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{gte(10,5)}")

                // WHEN
                val result = gte(constant(10), constant(5))

                // THEN
                assertEquals(expected, result)
            }

            @Test
            @DisplayName("Then should return correct expression")
            fun checkGteEqualInteger() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{gte(2,2)}")

                // WHEN
                val result = gte(constant(2), constant(2))

                // THEN
                assertEquals(expected, result)
            }

            @Test
            @DisplayName("Then should return correct expression")
            fun checkGteNotEqualInteger() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{gte(5,10)}")

                // WHEN
                val result = gte(constant(5), constant(10))

                // THEN
                assertEquals(expected, result)
            }

            @Test
            @DisplayName("Then should return correct expression")
            fun checkGteDouble() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{gte(10.5,5.0)}")

                // WHEN
                val result = gte(constant(10.5), constant(5.0))

                // THEN
                assertEquals(expected, result)
            }

            @Test
            @DisplayName("Then should return correct expression")
            fun checkGteEqualDouble() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{gte(2.0,2.0)}")

                // WHEN
                val result = gte(constant(2.0), constant(2.0))

                // THEN
                assertEquals(expected, result)
            }

            @Test
            @DisplayName("Then should return correct expression")
            fun checkGteNotEqualDouble() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{gte(5.0,10.0)}")

                // WHEN
                val result = gte(constant(5.0), constant(10.0))

                // THEN
                assertEquals(expected, result)
            }

        }

        @DisplayName("When passing parameters to lt operation")
        @Nested
        inner class LtOperationTest {

            @Test
            @DisplayName("Then should return correct expression")
            fun checkLtInteger() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{lt(2,5)}")

                // WHEN
                val result = lt(constant(2), constant(5))

                // THEN
                assertEquals(expected, result)
            }

            @Test
            @DisplayName("Then should return correct expression")
            fun checkNotLteInteger() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{lt(5,2)}")

                // WHEN
                val result = lt(constant(5), constant(2))

                // THEN
                assertEquals(expected, result)
            }

            @Test
            @DisplayName("Then should return correct expression")
            fun checkLtEqualInteger() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{lt(2,2)}")

                // WHEN
                val result = lt(constant(2), constant(2))

                // THEN
                assertEquals(expected, result)
            }

            @Test
            @DisplayName("Then should return correct expression")
            fun checkLessThanDouble() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{lt(2.0,5.0)}")

                // WHEN
                val result = lt(constant(2.0), constant(5.0))

                // THEN
                assertEquals(expected, result)
            }

            @Test
            @DisplayName("Then should return correct expression")
            fun checkNotLessThanDouble() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{lt(5.0,2.0)}")

                // WHEN
                val result = lt(constant(5.0), constant(2.0))

                // THEN
                assertEquals(expected, result)
            }

            @Test
            @DisplayName("Then should return correct expression")
            fun checkLtEqualDouble() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{lt(2.0,2.0)}")

                // WHEN
                val result = lt(constant(2.0), constant(2.0))

                // THEN
                assertEquals(expected, result)
            }
        }

        @DisplayName("When passing parameters to lte operation")
        @Nested
        inner class LteOperationTest {

            @Test
            @DisplayName("Then should return correct expression")
            fun checkLessThanEqInteger() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{lte(2,5)}")

                // WHEN
                val result = lte(constant(2), constant(5))

                // THEN
                assertEquals(expected, result)
            }

            @Test
            @DisplayName("Then should return correct expression")
            fun checkNotLessThanInteger() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{lte(5,2)}")

                // WHEN
                val result = lte(constant(5), constant(2))

                // THEN
                assertEquals(expected, result)
            }

            @Test
            @DisplayName("Then should return correct expression")
            fun checkLteEqualInteger() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{lte(2,2)}")

                // WHEN
                val result = lte(constant(2), constant(2))

                // THEN
                assertEquals(expected, result)
            }

            @Test
            @DisplayName("Then should return correct expression")
            fun checkLessThanEqDouble() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{lte(2.0,5.0)}")

                // WHEN
                val result = lte(constant(2.0), constant(5.0))

                // THEN
                assertEquals(expected, result)
            }

            @Test
            @DisplayName("Then should return correct expression")
            fun checkNotLessThanEqDouble() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{lte(5.0,2.0)}")

                // WHEN
                val result = lte(constant(5.0), constant(2.0))

                // THEN
                assertEquals(expected, result)
            }

            @Test
            @DisplayName("Then should return correct expression")
            fun checkLteEqualDouble() {
                // GIVEN
                val expected = Bind.Expression<Boolean>(value = "@{lte(2.0,2.0)}")

                // WHEN
                val result = lte(constant(2.0), constant(2.0))

                // THEN
                assertEquals(expected, result)
            }
        }
    }

