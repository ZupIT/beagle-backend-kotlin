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

package br.com.zup.beagle.processor

import br.com.zup.beagle.annotation.ContextObject
import br.com.zup.beagle.annotation.ImplicitContext
import br.com.zup.beagle.annotation.RegisterWidget
import br.com.zup.beagle.widget.Widget
import br.com.zup.beagle.widget.action.Action
import br.com.zup.beagle.widget.context.Context
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@RegisterWidget
class SampleWidget(
    val placeholder: String? = null,
    @ImplicitContext(inputClass = TestOnChange::class)
    val onChange: List<Action>? = null)
    : Widget()

@ContextObject
data class TestOnChange(override val id: String, val value: String? = null) : Context

class SampleAction : Action

@DisplayName("Given a widget annotated with RegisterWidget")
class WidgetProcessorTest {

    @Test
    @DisplayName("Then should generate widget dsl fun")
    fun testWidgetFun() {
        val onChangeActions = listOf(SampleAction())
        val expectedWidget = SampleWidget(placeholder = "placeholder", onChange = onChangeActions)
        val widgetFunResult = sampleWidget(placeholder = "placeholder", onChange = { onChangeActions })

        Assertions.assertEquals(expectedWidget.placeholder, widgetFunResult.placeholder)
        Assertions.assertEquals(expectedWidget.onChange, widgetFunResult.onChange)
    }

    @Test
    @DisplayName("Then should generate widget dsl fun with default values for the parameters")
    fun testWidgetFunDefaultValues() {
        val widgetFunResult = sampleWidget()

        Assertions.assertNull(widgetFunResult.placeholder)
        Assertions.assertNull(widgetFunResult.onChange)
    }
}