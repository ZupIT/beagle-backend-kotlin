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
import br.com.zup.beagle.sample.constants.TEXT_IMAGE_REMOTE
import br.com.zup.beagle.widget.action.Alert
import br.com.zup.beagle.widget.core.AlignSelf
import br.com.zup.beagle.widget.core.EdgeValue
import br.com.zup.beagle.widget.core.Flex
import br.com.zup.beagle.widget.core.ImageContentMode
import br.com.zup.beagle.widget.core.ScrollAxis
import br.com.zup.beagle.widget.core.Size
import br.com.zup.beagle.widget.core.UnitValue
import br.com.zup.beagle.widget.layout.Container
import br.com.zup.beagle.widget.layout.NavigationBar
import br.com.zup.beagle.widget.layout.NavigationBarItem
import br.com.zup.beagle.widget.layout.Screen
import br.com.zup.beagle.widget.layout.ScreenBuilder
import br.com.zup.beagle.widget.layout.ScrollView
import br.com.zup.beagle.widget.ui.Image
import br.com.zup.beagle.widget.ui.ImagePath.Remote
import br.com.zup.beagle.widget.ui.Text

class ImageRemoteScreenBuilder(private val imagePath: String) : ScreenBuilder {

    override fun build() = Screen(
        navigationBar = NavigationBar(
            title = "Beagle Image Remote",
            showBackButton = true,
            navigationBarItems = listOf(
                NavigationBarItem(
                    text = "",
                    image = "informationImage",
                    onPress = listOf(
                        Alert(
                            title = "Image Remote",
                            message = "It is a widget that implements an image with a URL.",
                            labelOk = "OK"
                        )
                    )
                )
            )
        ),
        child = ScrollView(
            scrollDirection = ScrollAxis.VERTICAL,
            children = listOf(buildImage(title = "Image Remote")) +
                ImageContentMode.values().map { buildImage("Image Remote with Mode.$it", it) }
        )
    )

    private fun buildImage(title: String, mode: ImageContentMode? = null) = Container(
        children = listOf(
            buildText(title),
            Image(Remote(this.imagePath), mode).setStyle {
                flex = Flex(
                    alignSelf = AlignSelf.CENTER
                )
                size = Size(
                    width = UnitValue.real(150),
                    height = UnitValue.real(130)
                )
            }
        )
    )

    private fun buildText(text: String) = Text(
        text = text,
        styleId = TEXT_IMAGE_REMOTE
    ).setStyle {
        flex = Flex(
            alignSelf = AlignSelf.CENTER
        )
        margin = EdgeValue(
            top = UnitValue.real(8)
        )
    }
}
