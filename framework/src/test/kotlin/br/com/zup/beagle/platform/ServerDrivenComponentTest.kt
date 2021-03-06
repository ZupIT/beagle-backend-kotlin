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

package br.com.zup.beagle.platform

import br.com.zup.beagle.widget.ui.Button
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@DisplayName("Given a ServerDrivenComponent")
class ServerDrivenComponentTest {

    @DisplayName("When set platform")
    @Nested
    inner class SetPlatformTest {

        @Test
        @DisplayName("Then should return BeaglePlatformWrapper")
        fun testConvertWidgetToBeaglePlatformWrapper() {
            // Given
            val button = Button("")
            val platform = BeaglePlatform.ALL

            // When
            val platformWidget = button.setPlatform(platform)

            // Then
            val expected = BeaglePlatformWrapper(button, platform)
            assertEquals(expected, platformWidget)
        }
    }

}