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

package br.com.zup.beagle.widget.context

import java.io.Serializable

/**
 * Bind is a representation of an expression in the form of a string that can then become any type of data
 */
sealed class Bind<T> : Serializable {

    abstract val value: Any

    /**
     * Represents the expression of bind
     */
    data class Expression<T>(override val value: String) : Bind<T>()

    /**
     * Represents the value of bind
     */
    data class Value<T : Any>(override val value: T) : Bind<T>()

    companion object {

        /**
         * Transform the reference value of the expression string to Bind.Expression<Type>
         */
        fun <T> expression(expression: String) = Expression<T>(expression)

        /**
         * Transform Type value to Bind<Type>.
         */
        fun <T : Any> constant(value: T) = Value(value)

        /**
         * Checks if the value is null. Returns if the value is not null.
         */
        fun <T : Any> constantNullable(value: T?) = value?.let { constant(it) }
    }
}

/**
 * Transform the reference value of the expression string to Bind.Expression<Type>
 */
fun <T> expressionOf(expression: String) = Bind.expression<T>(expression)

/**
 * Transform Type value to Bind<Type>.
 */
fun <T : Any> constant(value: T) = Bind.constant(value)

/**
 * Checks if the value is null. Returns if the value is not null.
 */
fun <T : Any> constantNullable(value: T?) = Bind.constantNullable(value)

/**
 * Access elements of a list type expression with a given index
 */
operator fun <T, J> Bind.Expression<T>.get(i: Int): Bind.Expression<J> where T : List<J> {
    val regularExpression = "(?<=\\@\\{).+?(?=\\})".toRegex()

    regularExpression.find(this.value)?.value?.let {
        return expressionOf("@{$it[$i]}")
    }

    return expressionOf(this.value)
}

/**
 * Separates a given full context identification path into a contextId and a path
 */
fun splitContextId(input: String): Pair<String, String?> {
    val contextIdRegex = ".*?(?=\\.)".toRegex()
    val pathRegex = "(?<=\\.).*".toRegex()
    val contextId = contextIdRegex.find(input)?.value
    val path = pathRegex.find(input)?.value

    return Pair(contextId ?: input, path)
}

