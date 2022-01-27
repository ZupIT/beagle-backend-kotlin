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
@file:Suppress("TooManyFunctions")

package br.com.zup.beagle.operations.builtin

import br.com.zup.beagle.widget.context.Bind
import br.com.zup.beagle.widget.context.expressionOf

const val REMOVE_AT_SIGN_AND_OPEN_KEYS = 2
const val REMOVE_KEYS_END = 1
/** Number **/
fun sum(vararg params: Bind<Number>): Bind.Expression<Number> = createOperation("sum", params)
fun subtract(vararg params: Bind<Number>): Bind.Expression<Number> = createOperation("subtract", params)
fun divide(vararg params: Bind<Number>): Bind.Expression<Number> = createOperation("divide", params)
fun multiply(vararg params: Bind<Number>): Bind.Expression<Number> = createOperation("multiply", params)

/** String **/
fun capitalize(param: Bind<String>): Bind.Expression<String> = createOperation("capitalize", arrayOf(param))

@JvmName("BindCapitalize")
fun Bind<String>.capitalize(): Bind.Expression<String> = createOperation("capitalize", arrayOf(this))

fun concat(vararg params: Bind<String>): Bind.Expression<String> = createOperation("concat", params)

fun lowercase(param: Bind<String>): Bind.Expression<String> = createOperation("lowercase", arrayOf(param))
fun Bind<String>.toLowerCase(): Bind.Expression<String> = createOperation("lowercase", arrayOf(this))

fun uppercase(param: Bind<String>): Bind.Expression<String> = createOperation("uppercase", arrayOf(param))
fun Bind<String>.toUpperCase(): Bind.Expression<String> = createOperation("uppercase", arrayOf(this))

fun substring(param: Bind<String>, startIndex: Bind<Int>, endIndex: Bind<Int>? = null): Bind.Expression<String> =
    createOperation("substr", arrayOf(param, startIndex, endIndex))

/** comparison **/
fun <I> eq(firstParam: Bind<I>, secondParam: Bind<I>): Bind.Expression<Boolean> =
    createOperation("eq", arrayOf(firstParam, secondParam))

fun gt(firstParam: Bind<Number>, secondParam: Bind<Number>): Bind.Expression<Boolean> =
    createOperation("gt", arrayOf(firstParam, secondParam))

fun gte(firstParam: Bind<Number>, secondParam: Bind<Number>): Bind.Expression<Boolean> =
    createOperation("gte", arrayOf(firstParam, secondParam))

fun lt(firstParam: Bind<Number>, secondParam: Bind<Number>): Bind.Expression<Boolean> =
    createOperation("lt", arrayOf(firstParam, secondParam))

fun lte(firstParam: Bind<Number>, secondParam: Bind<Number>): Bind.Expression<Boolean> =
    createOperation("lte", arrayOf(firstParam, secondParam))

/** logic **/
fun and(vararg params: Bind<Boolean>): Bind.Expression<Boolean> = createOperation("and", params)

fun <I> condition(condition: Bind<Boolean>, firstParam: Bind<I>, secondParam: Bind<I>): Bind.Expression<I> =
    createOperation("condition", arrayOf(condition, firstParam, secondParam))

fun not(param: Bind<Boolean>): Bind.Expression<Boolean> = createOperation("not", arrayOf(param))
fun or(vararg params: Bind<Boolean>): Bind.Expression<Boolean> = createOperation("or", params)

/** Array **/
fun <I> contains(array: Bind<Array<I>>, element: Bind<I>): Bind.Expression<Boolean> =
    createOperation("contains", arrayOf(array, element))

fun <I> insert(array: Bind<Array<I>>, element: Bind<I>, index: Bind<Int>? = null): Bind.Expression<Array<I>> =
    createOperation("insert", arrayOf(array, element, index))

fun <I> remove(array: Bind<Array<I>>, element: Bind<I>): Bind.Expression<Array<I>> =
    createOperation("remove", arrayOf(array, element))

fun <I> removeIndex(array: Bind<Array<I>>, index: Bind<Int>): Bind.Expression<Array<I>> =
    createOperation("removeIndex", arrayOf(array, index))

fun <I> union(firstArray: Bind<Array<I>>, secondArray: Bind<Array<I>>): Bind.Expression<Array<I>> =
    createOperation("union", arrayOf(firstArray, secondArray))

/** other **/
fun isEmpty(param: Bind<Boolean>): Bind.Expression<Boolean> = createOperation("isEmpty", arrayOf(param))
fun isNull(param: Bind<Boolean>): Bind.Expression<Boolean> = createOperation("isNull", arrayOf(param))
fun length(param: Bind<Array<*>>): Bind.Expression<Int> = createOperation("length", arrayOf(param))

fun <O> createOperation(operationType: String, params: Array<out Any?>): Bind.Expression<O> {
    val values = params.filterNotNull().map {
        resolveParam(it as Bind<*>)
    }
    return expressionOf("@{${operationType}(${values.joinToString(",")})}")
}

private fun <I> resolveParam(param: Bind<I>?): Any? {
    return param?.let {
        if (param is Bind.Expression && param.value.isNotEmpty()) {
            param.value.drop(REMOVE_AT_SIGN_AND_OPEN_KEYS).dropLast(REMOVE_KEYS_END)
        } else {
            val resultValue = (param as Bind.Value).value
            if (resultValue is String) {
                "'${resultValue}'"
            } else {
                resultValue
            }
        }
    } ?: run {
        param
    }
}

operator fun Bind<String>.plus(param: Bind<String>): Bind<String> {
    return expressionOf(this.value.toString() + param.value.toString())
}

fun <T> Bind.Expression<T>.toBindString(): Bind<String> = expressionOf(this.value)