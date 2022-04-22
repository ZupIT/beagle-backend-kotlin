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

import br.com.zup.beagle.ext.setFlex
import br.com.zup.beagle.ext.setStyle
import br.com.zup.beagle.sample.widget.ActionExecutor
import br.com.zup.beagle.sample.widget.ActionExecutorType
import br.com.zup.beagle.widget.action.Navigate
import br.com.zup.beagle.widget.action.NavigationContext
import br.com.zup.beagle.widget.action.Route
import br.com.zup.beagle.widget.action.SetContext
import br.com.zup.beagle.widget.context.ContextData
import br.com.zup.beagle.widget.context.expressionOf
import br.com.zup.beagle.widget.core.EdgeValue
import br.com.zup.beagle.widget.core.FlexDirection
import br.com.zup.beagle.widget.core.UnitValue
import br.com.zup.beagle.widget.layout.Container
import br.com.zup.beagle.widget.layout.NavigationBar
import br.com.zup.beagle.widget.layout.Screen
import br.com.zup.beagle.widget.layout.ScreenBuilder
import br.com.zup.beagle.widget.ui.Button
import br.com.zup.beagle.widget.ui.Text

object NavigationContextScreenBuilder : ScreenBuilder {
    override fun build() = Screen(
        navigationBar = NavigationBar(
            title = "Navigation Context",
            showBackButton = true
        ),
        child = this.createContent()
    )

    private fun createContent() = Container(
        context = ContextData(id = "text", value = false),
        children = listOf(
            Text(expressionOf("@{navigationContext.shouldRetry}")),
            Button(
                text = "Go to next page",
                onPress = listOf(
                    SetContext(contextId = "text", value = true),
                    Navigate.PushView(
                        navigationContext = NavigationContext(
                            value = "Click to go back",
                            path = "shouldRetry"
                        ),
                        route = Route.Local(
                            Screen(
                                navigationBar = NavigationBar(
                                    title = "Navigation Context",
                                    showBackButton = true
                                ),
                                child = Container(
                                    children = listOf(
                                        Button(
                                            text = expressionOf("@{navigationContext.shouldRetry}"),
                                            onPress = listOf(
                                                Navigate.PopView(
                                                    navigationContext = NavigationContext(
                                                        value = ActionExecutorType.NAVIGATE,
                                                        path = "shouldRetry"
                                                    )
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            ),
            createActionExecutor()
        )
    ).setFlex {
        flexDirection = FlexDirection.COLUMN
    }.setStyle {
        margin = EdgeValue(all = UnitValue.real(20.0))
    }

    private fun createActionExecutor() = ActionExecutor(
        trigger = expressionOf("@{navigationContext.shouldRetry}"),
        actions = listOf(
            Navigate.PushView(
                route = Route.Local(
                    screen = Screen(
                        navigationBar = NavigationBar(
                            title = "Navigation Context",
                            showBackButton = true
                        ),
                        child = Container(
                            children = listOf(
                                Button(
                                    text = "SIGNUP",
                                    onPress = listOf(
                                        Navigate.PopView(
                                            navigationContext = NavigationContext(
                                                value = ActionExecutorType.NAVIGATE,
                                                path = "shouldRetry"
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
    )
}
