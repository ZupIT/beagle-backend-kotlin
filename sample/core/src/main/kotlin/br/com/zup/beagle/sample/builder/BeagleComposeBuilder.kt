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
import br.com.zup.beagle.sample.constants.SCREEN_ACTION_CLICK_ENDPOINT
import br.com.zup.beagle.sample.widget.Channel
import br.com.zup.beagle.sample.widget.ChannelCard
import br.com.zup.beagle.sample.widget.ChannelCardRow
import br.com.zup.beagle.sample.widget.ComposeText
import br.com.zup.beagle.widget.action.Alert
import br.com.zup.beagle.widget.core.AlignItems
import br.com.zup.beagle.widget.core.JustifyContent
import br.com.zup.beagle.widget.layout.Container
import br.com.zup.beagle.widget.layout.NavigationBar
import br.com.zup.beagle.widget.layout.Screen
import br.com.zup.beagle.widget.layout.ScreenBuilder
import br.com.zup.beagle.widget.ui.Text

object BeagleComposeBuilder : ScreenBuilder {
    override fun build() = Screen(
        navigationBar = NavigationBar(
            title = "Compose",
            showBackButton = true
        ),
        child = Container(
            children = listOf(
                ComposeText(
                    "Click me",
                    onClick = listOf(Alert(message = "You have clicked!"))
                ).setFlex {
                    justifyContent = JustifyContent.CENTER
                    alignItems = AlignItems.CENTER
                },
                ChannelCardRow(
                    listOf(
                        Channel("https://upload.wikimedia.org/wikipedia/commons/2/27/Channel_A_Logo.jpg"),
                        Channel("https://upload.wikimedia.org/wikipedia/commons/2/27/Channel_A_Logo.jpg"),
                        Channel("https://upload.wikimedia.org/wikipedia/commons/2/27/Channel_A_Logo.jpg"),
                        Channel("https://upload.wikimedia.org/wikipedia/commons/2/27/Channel_A_Logo.jpg"),
                        Channel("https://upload.wikimedia.org/wikipedia/commons/2/27/Channel_A_Logo.jpg")
                    )
                )
            )
        )
    )
}