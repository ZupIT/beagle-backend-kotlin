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

package br.com.zup.beagle.operations.builtin

import br.com.zup.beagle.widget.context.Bind
import br.com.zup.beagle.widget.context.constant
import br.com.zup.beagle.widget.context.expressionOf
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Given a Array Operations")
internal class ArrayOperationsTest {

    companion object {
        private val ARRAY_TEST = arrayOf(1, 2, 3)
        private val EMPTY_ARRAY_TEST = arrayOf<Any>()
    }

    @DisplayName("When use 'contains' operation")
    @Nested
    inner class ContainsOperationTest {

        @DisplayName("Then should return expression with contains operation")
        @Test
        fun checkContainsOperation() = run {
            val result = contains(constant(ARRAY_TEST), constant(0))
            val expected = Bind.expression<String>("@{contains($ARRAY_TEST,0)}")

            Assertions.assertEquals(result, expected)
        }

        @DisplayName("Then should return expression with contains operation")
        @Test
        fun checkContainsOperationEmptyList() = run {
            val result = contains(constant(EMPTY_ARRAY_TEST), constant(0))

            val expected = Bind.expression<String>("@{contains($EMPTY_ARRAY_TEST,0)}")

            Assertions.assertEquals(result, expected)
        }

        @Test
        @DisplayName("Then should return correct expression with contains operation")
        fun checkIfContainsValue() {
            val expected = Bind.Expression<Boolean>(value = "@{contains($ARRAY_TEST,2)}")

            val result = contains(expressionOf("@{$ARRAY_TEST}"), constant(2))

            Assertions.assertEquals(expected, result)
        }

        @Test
        @DisplayName("Then should return correct expression with contains operation")
        fun checkIfContainsNotValue() {
            val expected = Bind.Expression<Boolean>(value = "@{contains($ARRAY_TEST,0)}")

            val result = contains(expressionOf("@{$ARRAY_TEST}"), constant(0))

            Assertions.assertEquals(expected, result)
        }

    }

    @DisplayName("When use 'insert' operation")
    @Nested
    inner class InsertOperationTest {

        @DisplayName("Then should return expression with insert operation")
        @Test
        fun checkInsertOperationWithOneParameter() = run {
            val result = insert(constant(ARRAY_TEST), constant(5), constant(0))
            val expected = Bind.expression<String>("@{insert($ARRAY_TEST,5,0)}")

            Assertions.assertEquals(result, expected)
        }

        @DisplayName("Then should return expression with insert operation")
        @Test
        fun checkInsertOperationWithoutIndex() = run {
            val result = insert(constant(ARRAY_TEST), constant(5))
            val expected = Bind.expression<String>("@{insert($ARRAY_TEST,5)}")

            Assertions.assertEquals(result, expected)
        }
    }

    @DisplayName("When use 'remove' operation")
    @Nested
    inner class RemoveOperationTest {

        @DisplayName("Then should return expression with remove operation")
        @Test
        fun checkRemoveOperationWithOneParameter() = run {
            val result = remove(constant(ARRAY_TEST), constant(5))
            val expected = Bind.expression<String>("@{remove($ARRAY_TEST,5)}")

            Assertions.assertEquals(result, expected)
        }
    }

    @DisplayName("When use 'removeIndex' operation")
    @Nested
    inner class RemoveIndexOperationTest {

        @DisplayName("Then should return expression with removeIndex operation")
        @Test
        fun checkRemoveIndexOperationWithOneParameter() = run {
            val result = removeIndex(constant(ARRAY_TEST), constant(5))
            val expected = Bind.expression<String>("@{removeIndex($ARRAY_TEST,5)}")

            Assertions.assertEquals(result, expected)
        }
    }

    @DisplayName("When use 'union' operation")
    @Nested
    inner class UnionOperationTest {

        @DisplayName("Then should return expression with union operation")
        @Test
        fun checkUnionOperationWithOneParameter() = run {
            val result = union(constant(ARRAY_TEST), constant(ARRAY_TEST))
            val expected = Bind.expression<String>("@{union($ARRAY_TEST,$ARRAY_TEST)}")

            Assertions.assertEquals(result, expected)
        }
    }
}
