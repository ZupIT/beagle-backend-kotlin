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

package br.com.zup.beagle.sample.builder

import br.com.zup.beagle.ext.setStyle
import br.com.zup.beagle.operations.builtin.and
import br.com.zup.beagle.operations.builtin.capitalize
import br.com.zup.beagle.operations.builtin.concat
import br.com.zup.beagle.operations.builtin.condition
import br.com.zup.beagle.operations.builtin.contains
import br.com.zup.beagle.operations.builtin.divide
import br.com.zup.beagle.operations.builtin.double
import br.com.zup.beagle.operations.builtin.eq
import br.com.zup.beagle.operations.builtin.gt
import br.com.zup.beagle.operations.builtin.gte
import br.com.zup.beagle.operations.builtin.insert
import br.com.zup.beagle.operations.builtin.int
import br.com.zup.beagle.operations.builtin.isEmpty
import br.com.zup.beagle.operations.builtin.isNull
import br.com.zup.beagle.operations.builtin.length
import br.com.zup.beagle.operations.builtin.lowercase
import br.com.zup.beagle.operations.builtin.lt
import br.com.zup.beagle.operations.builtin.lte
import br.com.zup.beagle.operations.builtin.multiply
import br.com.zup.beagle.operations.builtin.not
import br.com.zup.beagle.operations.builtin.or
import br.com.zup.beagle.operations.builtin.plus
import br.com.zup.beagle.operations.builtin.remove
import br.com.zup.beagle.operations.builtin.removeIndex
import br.com.zup.beagle.operations.builtin.string
import br.com.zup.beagle.operations.builtin.substring
import br.com.zup.beagle.operations.builtin.subtract
import br.com.zup.beagle.operations.builtin.sum
import br.com.zup.beagle.operations.builtin.toBindString
import br.com.zup.beagle.operations.builtin.toLowerCase
import br.com.zup.beagle.operations.builtin.toUpperCase
import br.com.zup.beagle.operations.builtin.union
import br.com.zup.beagle.operations.builtin.uppercase
import br.com.zup.beagle.widget.context.Bind
import br.com.zup.beagle.widget.context.ContextData
import br.com.zup.beagle.widget.context.constant
import br.com.zup.beagle.widget.context.expressionOf
import br.com.zup.beagle.widget.core.EdgeValue
import br.com.zup.beagle.widget.core.UnitValue
import br.com.zup.beagle.widget.layout.Container
import br.com.zup.beagle.widget.layout.NavigationBar
import br.com.zup.beagle.widget.layout.Screen
import br.com.zup.beagle.widget.layout.ScreenBuilder
import br.com.zup.beagle.widget.layout.ScrollView
import br.com.zup.beagle.widget.ui.Text

data class Array(val array1: Any, val array2: Any)

object OperationScreenBuilder : ScreenBuilder {
    override fun build() = Screen(
        navigationBar = NavigationBar(
            title = "Operations",
            showBackButton = true
        ),
        child = ScrollView(
            children = listOf(
                Container(
                    context = ContextData("text", "tEsT"),
                    children = listOf(
                        stringOperations(),
                        numberOperations(),
                        conversionOperations(),
                        comparisonOperations(),
                        logicOperations(),
                        arrayOperations(),
                        otherOperations()
                    )
                ).setStyle {
                    margin =
                        EdgeValue(all = UnitValue.real(10))
                }
            )
        )
    )

    private fun stringOperations() = Container(
        children = listOf(
            Text("String", textColor = "#00c91b"),
            Text(uppercase(expressionOf("@{text}")).capitalize().toBindString()),
            Text(concat(constant("aaa"), constant("bbb"), expressionOf("@{text}")).toBindString()),

            Text(lowercase(expressionOf("@{text}")).toBindString()),
            Text((constant("TeStINg".substring(3)).toLowerCase()).toBindString()),

            Text(uppercase(expressionOf("@{text}")).toBindString()),
            Text(uppercase(expressionOf("@{text}")).toUpperCase().toBindString()),

            Text(substring(constant("testing"), constant(3)).toBindString())
        )
    )
    private fun conversionOperations() = Container(
        children = listOf(
            Container(
                context = ContextData("number", 4),
                children = listOf(
                    Text("Conversion operations", textColor = "#00c91b"),
                    Text("Int"),
                    Text(int(expressionOf("@{number}")).toBindString()),
                    Text(int(constant(1)).toBindString()),
                    Text(int(constant(1.1)).toBindString()),
                    Text(int(constant("1")).toBindString()),
                    Text(int(constant("1.1")).toBindString()),
                    Text(int(constant("string")).toBindString()),
                    Text("Double"),
                    Text(double(expressionOf("@{number}")).toBindString()),
                    Text(double(constant(1)).toBindString()),
                    Text(double(constant(1.1)).toBindString()),
                    Text(double(constant("1")).toBindString()),
                    Text(double(constant("1.1")).toBindString()),
                    Text(double(constant("string")).toBindString()),
                    Text("String"),
                    Text(string(expressionOf("@{number}")).toBindString()),
                    Text(string(constant(1)).toBindString()),
                    Text(string(constant(1.1)).toBindString()),
                    Text(string(constant("1")).toBindString()),
                    Text(string(constant("1.1")).toBindString()),
                    Text(string(constant("string")).toBindString()),
                )
            ).setStyle {
                margin =
                    EdgeValue(top = UnitValue.Companion.real(10), bottom = UnitValue.Companion.real(10))
            }
        )
    )

    @Suppress("LongMethod")
    private fun numberOperations() = Container(
        children = listOf(
            Container(
                context = ContextData("number", 4),
                children = listOf(
                    Text("Number", textColor = "#00c91b"),
                    Text(constant("Soma 1 + 2 = ").plus(sum(constant(1), constant(2)).toBindString())),
                    Text(expressionOf<String>("Soma 1 + 4 = ").toBindString() +
                            sum(constant(1), expressionOf("@{number}")).toBindString()),

                    Text(sum(constant(1), constant(2), expressionOf("@{number}")).toBindString()),
                    Text(sum(expressionOf("@{number}"), expressionOf("@{number}")).toBindString()),

                    Text(sum(constant(1), constant(2)).toBindString()),
                    Text(sum(constant(1),constant(2),sum(constant(2),
                        constant(2),expressionOf("@{number}"))).toBindString()),

                    Text(subtract(constant(1), constant(2), expressionOf("@{number}")).toBindString()),
                    Text(multiply(constant(1), constant(2), expressionOf("@{number}")).toBindString()),
                    Text(divide(constant(10.0), constant(2.0), expressionOf("@{number}")).toBindString()),
                    Text("Type Coercions", textColor = "#00c91b"),
                    Text("Subtract"),
                    Text(subtract(constant(4), constant(4)).toBindString()),
                    Text(subtract(constant(4.5), constant(6.0)).toBindString()),
                    Text(subtract(constant(4), constant(6), constant(4)).toBindString()),
                    Text(subtract(constant(2.5), constant(4.5), constant(6.0)).toBindString()),
                    Text(subtract(constant(1), constant(1.5)).toBindString()),
                    Text(subtract(constant(2.0), constant(1)).toBindString()),
                    Text(subtract(constant("1"), constant(1.0)).toBindString()),
                    Text(subtract(constant(2.5), constant("1.0")).toBindString()),
                    Text(subtract(constant("1"), constant("1")).toBindString()),
                    Text(subtract(constant("2"), constant(1)).toBindString()),
                    Text(subtract(constant("3.5"), constant(2)).toBindString()),
                    Text(subtract(constant(1), constant(true)).toBindString()),
                    Text(subtract().toBindString()),

                    Text("Sum"),
                    Text(sum(constant(4), constant(4)).toBindString()),
                    Text(sum(constant(4.5), constant(6.0)).toBindString()),
                    Text(sum(constant(4), constant(6), constant(4)).toBindString()),
                    Text(sum(constant(2.5), constant(4.5), constant(6.0)).toBindString()),
                    Text(sum(constant(1), constant(1.5)).toBindString()),
                    Text(sum(constant(2.0), constant(1)).toBindString()),
                    Text(sum(constant("1"), constant(1.0)).toBindString()),
                    Text(sum(constant(2.5), constant("1.0")).toBindString()),
                    Text(sum(constant("1"), constant("1")).toBindString()),
                    Text(sum(constant("2"), constant(1)).toBindString()),
                    Text(sum(constant("3.5"), constant(2)).toBindString()),
                    Text(sum(constant(1), constant(true)).toBindString()),
                    Text(sum().toBindString()),

                    Text("Divide"),
                    Text(divide(constant(4), constant(4)).toBindString()),
                    Text(divide(constant(4.5), constant(6.0)).toBindString()),
                    Text(divide(constant(4), constant(6), constant(4)).toBindString()),
                    Text(divide(constant(2.5), constant(4.5), constant(6.0)).toBindString()),
                    Text(divide(constant(1), constant(1.5)).toBindString()),
                    Text(divide(constant(2.0), constant(1)).toBindString()),
                    Text(divide(constant("1"), constant(1.0)).toBindString()),
                    Text(divide(constant(2.5), constant("1.0")).toBindString()),
                    Text(divide(constant("1"), constant("1")).toBindString()),
                    Text(divide(constant("2"), constant(1)).toBindString()),
                    Text(divide(constant("3.5"), constant(2)).toBindString()),
                    Text(divide(constant(1), constant(true)).toBindString()),
                    Text(divide().toBindString()),

                    Text("Multiply"),
                    Text(multiply(constant(4), constant(4)).toBindString()),
                    Text(multiply(constant(4.5), constant(6.0)).toBindString()),
                    Text(multiply(constant(4), constant(6), constant(4)).toBindString()),
                    Text(multiply(constant(2.5), constant(4.5), constant(6.0)).toBindString()),
                    Text(multiply(constant(1), constant(1.5)).toBindString()),
                    Text(multiply(constant(2.0), constant(1)).toBindString()),
                    Text(multiply(constant("1"), constant(1.0)).toBindString()),
                    Text(multiply(constant(2.5), constant("1.0")).toBindString()),
                    Text(multiply(constant("1"), constant("1")).toBindString()),
                    Text(multiply(constant("2"), constant(1)).toBindString()),
                    Text(multiply(constant("3.5"), constant(2)).toBindString()),
                    Text(multiply(constant(1), constant(true)).toBindString()),
                    Text(multiply().toBindString()),
                )
            ).setStyle {
                margin =
                    EdgeValue(top = UnitValue.Companion.real(10), bottom = UnitValue.Companion.real(10))
            }
        )
    )

    private fun comparisonOperations() = Container(
        context = ContextData("comparison", 3),
        children = listOf(
            Text("comparison", textColor = "#00c91b"),
            Text(eq(constant(3), expressionOf("@{comparison}")).toBindString()),

            Text(gt(expressionOf("@{comparison}"), constant(3.2)).toBindString()),
            Text(gt(expressionOf("@{comparison}"), constant(4)).toBindString()),

            Text(gte(expressionOf("@{comparison}"), constant(2)).toBindString()),

            Text(lt(constant(2), expressionOf("@{comparison}")).toBindString()),

            Text(lte(constant(2), expressionOf("@{comparison}")).toBindString()),
            Text(lte(expressionOf("@{comparison}"), expressionOf("@{comparison}")).toBindString()),
            Text("Type Coercions", textColor = "#00c91b"),
            //gt
            Text(constant("3 gt true = ").plus(gt(expressionOf("@{comparison}"), constant("true")).toBindString())),
            Text(constant("`2` gt 1 = ").plus(gt(constant("2"), constant(1)).toBindString())),
            Text(constant("`2.0` gt 1 = ").plus(gt(constant("2.0"), constant(1)).toBindString())),
            Text(constant("`2.0` gt 2 = ").plus(gt(constant("2.0"), constant(2)).toBindString())),
            Text(constant("`2` gt 2 = ").plus(gt(constant("2"), constant(2)).toBindString())),

            //eq
            Text(constant("3 eq true = ").plus(eq(expressionOf("@{comparison}"), constant("true")).toBindString())),
            Text(constant("`2` eq 1 = ").plus(eq(constant("2"), constant(1)).toBindString())),
            Text(constant("`2.0` eq 1 = ").plus(eq(constant("2.0"), constant(1)).toBindString())),
            Text(constant("`2.0` eq 2 = ").plus(eq(constant("2.0"), constant(2)).toBindString())),
            Text(constant("`2` eq 2 = ").plus(eq(constant("2"), constant(2)).toBindString())),

            //gte
            Text(constant("3 gte true = ").plus(gte(expressionOf("@{comparison}"), constant("true")).toBindString())),
            Text(constant("`2` gte 1 = ").plus(gte(constant("2"), constant(1)).toBindString())),
            Text(constant("`2.0` gte 1 = ").plus(gte(constant("2.0"), constant(1)).toBindString())),
            Text(constant("`2.0` gte 2 = ").plus(gte(constant("2.0"), constant(2)).toBindString())),
            Text(constant("`2` gte 2 = ").plus(gte(constant("2"), constant(2)).toBindString())),

            //lte
            Text(constant("3 lte true = ").plus(lte(expressionOf("@{comparison}"), constant("true")).toBindString())),
            Text(constant("`2` lte 1 = ").plus(lte(constant("2"), constant(1)).toBindString())),
            Text(constant("`2.0` lte 1 = ").plus(lte(constant("2.0"), constant(1)).toBindString())),
            Text(constant("`2.0` lte 2 = ").plus(lte(constant("2.0"), constant(2)).toBindString())),
            Text(constant("`2` lte 2 = ").plus(lte(constant("2"), constant(2)).toBindString())),

            //lt
            Text(constant("3 lt true = ").plus(lt(expressionOf("@{comparison}"), constant("true")).toBindString())),
            Text(constant("`2` lt 1 = ").plus(lt(constant("2"), constant(1)).toBindString())),
            Text(constant("`2.0` lt 1 = ").plus(lt(constant("2.0"), constant(1)).toBindString())),
            Text(constant("`2.0` lt 2 = ").plus(lt(constant("2.0"), constant(2)).toBindString())),
            Text(constant("`2` lt 2 = ").plus(lt(constant("2"), constant(2)).toBindString())),
        )
    ).setStyle {
        margin = EdgeValue(top = UnitValue.real(10))
    }

    private fun logicOperations() = Container(
        context = ContextData("logic", false),
        children = listOf(
            Text("logic", textColor = "#00c91b"),
            Text(and(constant(true), expressionOf("@{logic}")).toBindString()),
            Text(
                condition<Bind<Boolean>>(
                    constant(true), expressionOf("@{logic}"),
                    expressionOf("@{logic}")
                ).toBindString()
            ),
            Text(not(expressionOf("@{logic}")).toBindString()),
            Text(or(constant(true), expressionOf("@{logic}")).toBindString())
        )
    ).setStyle { margin = EdgeValue(bottom = UnitValue.Companion.real(10)) }

    private fun arrayOperations() = Container(
        children = listOf(
            Container(
                context = ContextData(
                    id = "array",
                    Array(array1 = arrayOf(1, 2, 3), array2 = arrayOf(4, 5, 6))
                ),
                children = listOf(
                    Text("Array", textColor = "#00c91b"),
                    Text(contains(expressionOf("@{array.array1}"), constant(0)).toBindString()),
                    Text(remove(expressionOf("@{array.array2}"), constant(4)).toBindString()),
                    Text(
                        removeIndex<Int>(
                            expressionOf("@{array.array1}"),
                            constant(0)
                        ).toBindString()
                    ),
                    Text(insert(expressionOf("@{array.array2}"), constant(7)).toBindString()),
                    Text(
                        union<Number>(
                            expressionOf("@{array.array1}"),
                            expressionOf("@{array.array2}")
                        ).toBindString()
                    )
                )
            ).setStyle { margin = EdgeValue(bottom = UnitValue.Companion.real(10)) },
        )
    ).setStyle {
        margin = EdgeValue(bottom = UnitValue.Companion.real(10))
    }

    private fun otherOperations() = Container(
        context = ContextData("other", arrayOf(0, 1, 2, 3, 4)),
        children = listOf(
            Text("other", textColor = "#00c91b"),
            Text(isEmpty(expressionOf("@{other}")).toBindString()),
            Text(isNull(expressionOf("@{other}")).toBindString()),
            Text(length(expressionOf("@{other}")).toBindString())
        )
    ).setStyle { margin = EdgeValue(bottom = UnitValue.Companion.real(50)) }
}