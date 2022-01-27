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

package br.com.zup.beagle.sample.builder

import br.com.zup.beagle.annotation.ContextObject
import br.com.zup.beagle.core.CornerRadius
import br.com.zup.beagle.ext.setFlex
import br.com.zup.beagle.ext.setStyle
import br.com.zup.beagle.sample.constants.LOGO_BEAGLE
import br.com.zup.beagle.sample.constants.TITLE_SCREEN
import br.com.zup.beagle.widget.action.Alert
import br.com.zup.beagle.widget.action.SetContext
import br.com.zup.beagle.widget.context.Context
import br.com.zup.beagle.widget.context.constant
import br.com.zup.beagle.widget.context.expressionOf
import br.com.zup.beagle.widget.core.AlignSelf
import br.com.zup.beagle.widget.core.EdgeValue
import br.com.zup.beagle.widget.core.ImageContentMode
import br.com.zup.beagle.widget.core.ScrollAxis
import br.com.zup.beagle.widget.core.UnitValue
import br.com.zup.beagle.widget.layout.NavigationBar
import br.com.zup.beagle.widget.layout.NavigationBarItem
import br.com.zup.beagle.widget.layout.Screen
import br.com.zup.beagle.widget.layout.ScreenBuilder
import br.com.zup.beagle.widget.layout.ScrollView
import br.com.zup.beagle.widget.ui.Button
import br.com.zup.beagle.widget.ui.Image
import br.com.zup.beagle.widget.ui.ImagePath.Local
import br.com.zup.beagle.widget.ui.Text

@ContextObject
data class ImageContext(
    override val id: String,
    val informationImage: String? = null,
    val radius: Double? = null,
    val topLeft: Double? = null,
    val topRight: Double? = null,
    val bottomLeft: Double? = null,
    val bottomRight: Double? = null,
) : Context

object ImageLocalScreenBuilder : ScreenBuilder {
    private var contextData = ImageContext("context",
        topLeft = 10.0,
        bottomRight = 10.0,
        informationImage = "informationImage"
    )
    override fun build() = Screen(
        context = contextData,
        navigationBar = NavigationBar(
            title = "Beagle Image",
            showBackButton = true,
            navigationBarItems = listOf(
                NavigationBarItem(
                    text = "",
                    image = expressionOf("@{context.informationImage}"),
                    action = Alert(
                        title = "Image",
                        message = "This widget will define a image view natively using the server driven " +
                            "information received through Beagle.",
                        labelOk = "OK"
                    )
                )
            )
        ),
        child = ScrollView(
            scrollDirection = ScrollAxis.VERTICAL,
            children = listOf(
                createText("Image").setFlex { alignSelf = AlignSelf.CENTER },
                Image(Local.justMobile(LOGO_BEAGLE))
                    .setStyle {
                        cornerRadius = CornerRadius(
                            radius = expressionOf("@{context.radius}"),
                            topLeft = expressionOf("@{context.topLeft}"),
                            topRight = expressionOf("@{context.topRight}"),
                            bottomLeft = expressionOf("@{context.bottomLeft}"),
                            bottomRight = expressionOf("@{context.bottomRight}"),
                        )
                        margin = EdgeValue(all = UnitValue(constant(16.0)))
                    },
                Button(text = "Change image radius", onPress = listOf(
                    SetContext(
                        contextId = "context",
                        value = ImageContext("context",
                            informationImage = "informationImage",
                            bottomLeft = 16.0,
                            topRight = 16.0
                        )
                    )
                )).setStyle { margin = EdgeValue(all = UnitValue(constant(16.0))) }
            ) + ImageContentMode.values().flatMap(this::createImageWithModeAndText) +
                Button(text = "Change icon menu", onPress = listOf(
                    SetContext(
                        contextId = "context",
                        value = ImageContext("context",
                            informationImage = "beagle"
                        )
                    )
                ))
        )
    )

    private fun createText(text: String) = Text(text = text, styleId = TITLE_SCREEN)

    private fun createImageWithModeAndText(mode: ImageContentMode) =
        listOf(
            createText("Image with Mode = $mode").setFlex { alignSelf = AlignSelf.CENTER },
            Image(Local.justMobile(LOGO_BEAGLE), mode)
        )
}
