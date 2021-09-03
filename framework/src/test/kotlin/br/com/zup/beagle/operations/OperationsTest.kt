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

package br.com.zup.beagle.operations

import br.com.zup.beagle.operations.builtin.plus
import br.com.zup.beagle.operations.builtin.sum
import br.com.zup.beagle.operations.builtin.toBindString
import br.com.zup.beagle.widget.context.Bind
import br.com.zup.beagle.widget.context.constant
import br.com.zup.beagle.widget.context.expressionOf
import kotlin.test.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Given a Operation")
internal class OperationTest {

    @DisplayName("When param is a Bind")
    @Nested
    inner class PlusTest {

        @Test
        @DisplayName("Then should return correct expression")
        fun checkPlusWhenValueAndParamAreBindOfValue() {
            // GIVEN
            val expected: Bind<String> = expressionOf("Sum 1 + 2 = @{sum(1,2)}")

            // WHEN
            val result = constant("Sum 1 + 2 = ").plus(sum(constant(1), constant(2)).toBindString())

            // THEN
            assertEquals(expected, result)
        }

        @Test
        @DisplayName("Then should return correct expression")
        fun checkPlusWhenValueIsBindOfExpressionAndParamIsBindOfValue() {
            // GIVEN
            val expected: Bind<String> = expressionOf("@{test}@{sum(1,2)}")

            // WHEN
            val result = expressionOf<String>("@{test}")
                .plus(sum(constant(1), constant(2)).toBindString())

            // THEN
            assertEquals(expected, result)
        }

        @Test
        @DisplayName("Then should return correct expression")
        fun checkPlusWhenValueIsBindOfValueAndParamIsBindOfExpression() {
            // GIVEN
            val expected: Bind<String> = expressionOf("Sum = @{sum(number,number)}")

            // WHEN
            val result = constant("Sum = ")
                .plus(sum(expressionOf("@{number}"), expressionOf("@{number}")).toBindString())

            // THEN
            assertEquals(expected, result)
        }

        @Test
        @DisplayName("Then should return correct expression")
        fun checkPlusWhenValueAndParamAreBindOfExpression() {
            // GIVEN
            val expected: Bind<String> = expressionOf("@{test}@{sum(number,number)}")

            // WHEN
            val result = expressionOf<String>("@{test}")
                .plus(sum(expressionOf("@{number}"), expressionOf("@{number}")).toBindString())

            // THEN
            assertEquals(expected, result)
        }
    }
}