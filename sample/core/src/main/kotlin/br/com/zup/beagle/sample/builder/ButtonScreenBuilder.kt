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

import br.com.zup.beagle.annotation.ContextObject
import br.com.zup.beagle.core.Accessibility
import br.com.zup.beagle.core.CornerRadius
import br.com.zup.beagle.core.Style
import br.com.zup.beagle.ext.setStyle
import br.com.zup.beagle.sample.constants.BLACK
import br.com.zup.beagle.sample.constants.BUTTON_STYLE
import br.com.zup.beagle.sample.constants.BUTTON_STYLE_APPEARANCE
import br.com.zup.beagle.sample.constants.CYAN_BLUE
import br.com.zup.beagle.sample.constants.LIGHT_ORANGE
import br.com.zup.beagle.sample.constants.RED
import br.com.zup.beagle.sample.constants.SCREEN_ACTION_CLICK_ENDPOINT
import br.com.zup.beagle.widget.Widget
import br.com.zup.beagle.widget.action.Action
import br.com.zup.beagle.widget.action.Alert
import br.com.zup.beagle.widget.action.Navigate
import br.com.zup.beagle.widget.action.Route
import br.com.zup.beagle.widget.action.SetContext
import br.com.zup.beagle.widget.context.Context
import br.com.zup.beagle.widget.context.constant
import br.com.zup.beagle.widget.context.expressionOf
import br.com.zup.beagle.widget.core.EdgeValue
import br.com.zup.beagle.widget.core.Size
import br.com.zup.beagle.widget.core.UnitValue
import br.com.zup.beagle.widget.layout.Container
import br.com.zup.beagle.widget.layout.NavigationBar
import br.com.zup.beagle.widget.layout.NavigationBarItem
import br.com.zup.beagle.widget.layout.Screen
import br.com.zup.beagle.widget.layout.ScreenBuilder
import br.com.zup.beagle.widget.ui.Button

@ContextObject
data class ButtonContext(
    override val id: String = "buttonContext",
    var color: String = CYAN_BLUE,
    val radius: Double? = null,
    val topLeft: Double? = null,
    val topRight: Double? = null,
    val bottomLeft: Double? = null,
    val bottomRight: Double? = null,
    val borderColor: String? = null,
    val borderWidth: Double? = null,
    val marginLeft: Double? = 25.0,
    val marginRight: Double? = 25.0,
    val marginTop: Double? = 16.0,
    val marginBottom: Double? = null,
    val paddingLeft: Double? = null,
    val paddingRight: Double? = null,
    val paddingTop: Double? = null,
    val paddingBottom: Double? = null,
    val positionLeft: Double? = null,
    val positionRight: Double? = null,
    val positionTop: Double? = null,
    val positionBottom: Double? = null,
    val sizeWidth: Double? = null
) : Context

object ButtonScreenBuilder : ScreenBuilder {
    private var buttonContextData = ButtonContext()
    override fun build() = Screen(
        navigationBar = NavigationBar(
            title = "Beagle Button",
            showBackButton = true,
            navigationBarItems = listOf(
                NavigationBarItem(
                    text = "",
                    image = "informationImage",
                    onPress = listOf(
                        Alert(
                            title = "Button",
                            message = "This is a widget that will define a button natively using the server " +
                                "driven information received through Beagle.",
                            labelOk = "OK"
                        )
                    )
                )
            )
        ),
        child = Container(
            context = buttonContextData,
            children = createContentList()
        )
    )

    private fun createContentList() =  listOf(
        buttonWithAppearanceAndStyle(text = "Button with Appearance"),
        buttonWithAppearanceAndStyle(
            text = "Button with Appearance and style",
            styleId = BUTTON_STYLE_APPEARANCE
        )

    ) + createExpressionButtonSample() +
        createPositionExamples() +
        createSizeExamples() +
        createPaddingExamples() +
        createMarginExamples() +
        createBorderExamples() +
        createRadiusBorderColorExamples() +
        createAccessibilityExamples()

    private fun createBorderExamples(): List<Widget> {
        return createExpressionButton(
            text = "CHANGE BUTTON BORDER",
            onPress = listOf(SetContext(
                contextId = "buttonContext",
                value = ButtonContext("buttonContext",
                    borderColor = LIGHT_ORANGE,
                    borderWidth = 2.0
                )
            ))
        )
    }

    private fun createMarginExamples(): List<Widget> {
        return createExpressionButton(
            text = "CHANGE MARGIN",
            onPress = listOf(SetContext(
                contextId = "buttonContext",
                value = ButtonContext(
                    marginBottom = 48.0,
                    marginLeft = 64.0,
                    marginRight = 64.0,
                    marginTop = 48.0
                )
            ))
        )
    }

    private fun createPositionExamples(): List<Widget> {
        return createExpressionButton(onPress = listOf(SetContext(
            contextId = "buttonContext",
            value = ButtonContext(
                positionLeft = 30.0,
                positionTop = 32.0
            )
        )), text = "CHANGE POSITION")
    }

    private fun createPaddingExamples(): List<Widget> {
        return createExpressionButton(text = "CHANGE PADDING",
            onPress = listOf(SetContext(
                contextId = "buttonContext",
                value = ButtonContext("buttonContext",
                    paddingBottom = 50.0,
                    paddingLeft = 50.0,
                    paddingRight = 50.0,
                    paddingTop = 50.0
                )
            )))
    }

    private fun createSizeExamples(): List<Widget> {
        return createExpressionButton(onPress =  listOf(SetContext(
            contextId = "buttonContext",
            value = ButtonContext(
                sizeWidth = 450.0
            )
        )), text = "CHANGE SIZE")
    }

    private fun createExpressionButton(onPress: List<Action> = listOf(), text: String = ""): List<Widget> {
        return listOf(Button(
            text = text,
            onPress = onPress
        ).setStyle {
            backgroundColor = constant(CYAN_BLUE)
            cornerRadius = CornerRadius(radius = constant(16.0))
            margin = EdgeValue(
                left = UnitValue.real(25),
                right = UnitValue.real(25),
                top = UnitValue.real(15)
            )
        })
    }

    private fun createRadiusBorderColorExamples(): List<Widget> {
        return createExpressionButton(text = "CHANGE COLOR AND RADIUS WITH EXPRESSION",
            onPress = listOf(SetContext(
                contextId = "buttonContext",
                value = ButtonContext(
                    color = RED,
                    radius = 16.0,
                    borderColor = BLACK,
                    borderWidth = 2.0
                )
            )))
    }

    private fun createAccessibilityExamples(): List<Widget> {
        return listOf(createButton(
            text = "Button with accessibility false",
            style = Style(
                margin = EdgeValue(
                    top = UnitValue.real(15)
                )
            ),
            accessibility = Accessibility(accessible = false)
        ),
        createButton(
            text = "Button with accessibilityLabel",
            style = Style(
                margin = EdgeValue(
                    top = UnitValue.real(15)
                )
            ),
            accessibility = Accessibility(accessibilityLabel = "Alternative label")
        ),
            createButton(
                text = "Button accessibility isHeader true",
                styleId = BUTTON_STYLE,
                style = Style(
                    margin = EdgeValue(
                        top = UnitValue.real(15)
                    )
                ),
                accessibility = Accessibility(accessibilityLabel = "Alternative label", isHeader = true)
            ))
    }

    private fun createExpressionButtonSample(): List<Widget> {
        return listOf(createButton(text = "Expression sample", style = Style(
            margin = EdgeValue(
                left = UnitValue(expressionOf("@{buttonContext.marginLeft}")),
                right = UnitValue(expressionOf("@{buttonContext.marginRight}")),
                top = UnitValue(expressionOf("@{buttonContext.marginTop}")),
                bottom = UnitValue(expressionOf("@{buttonContext.marginBottom}"))
            ),
            cornerRadius = CornerRadius(
                radius = expressionOf("@{buttonContext.radius}"),
                topLeft = expressionOf("@{buttonContext.topLeft}"),
                topRight = expressionOf("@{buttonContext.topRight}"),
                bottomLeft = expressionOf("@{buttonContext.bottomLeft}"),
                bottomRight = expressionOf("@{buttonContext.bottomRight}"),
            ),
            borderColor = expressionOf("@{buttonContext.borderColor}"),
            borderWidth = expressionOf("@{buttonContext.borderWidth}"),
            backgroundColor = expressionOf("@{buttonContext.color}"),
            size = Size(width = UnitValue(expressionOf("@{buttonContext.sizeWidth}"))),
            padding = EdgeValue(
                left = UnitValue(expressionOf("@{buttonContext.paddingLeft}")),
                right = UnitValue(expressionOf("@{buttonContext.paddingRight}")),
                top = UnitValue(expressionOf("@{buttonContext.paddingTop}")),
                bottom = UnitValue(expressionOf("@{buttonContext.paddingBottom}"))
            ),
            position = EdgeValue(
                left = UnitValue(expressionOf("@{buttonContext.positionLeft}")),
                right = UnitValue(expressionOf("@{buttonContext.positionRight}")),
                top = UnitValue(expressionOf("@{buttonContext.positionTop}")),
                bottom = UnitValue(expressionOf("@{buttonContext.positionBottom}"))
            )
        )))
    }

    private fun buttonWithAppearanceAndStyle(text: String, styleId: String? = null) = createButton(
        text = text,
        styleId = styleId,
        style = Style(
            backgroundColor = constant(CYAN_BLUE),
            cornerRadius = CornerRadius(radius = constant(16.0)),
            margin = EdgeValue(
                left = UnitValue.real(25),
                right = UnitValue.real(25),
                top = UnitValue.real(15)
            )
        )
    )

    private fun createButton(
        text: String,
        styleId: String? = null,
        style: Style? = null,
        accessibility: Accessibility? = null
    ): Widget {
        val button = Button(
            text = text,
            styleId = styleId,
            onPress = listOf(Navigate.PushView(Route.Remote(SCREEN_ACTION_CLICK_ENDPOINT, true)))
        )

        if (style != null) {
            button.style = style
        }

        if (accessibility != null) {
            button.accessibility = accessibility
        }

        return button
    }
}
