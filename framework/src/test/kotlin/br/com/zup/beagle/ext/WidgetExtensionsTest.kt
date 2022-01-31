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

package br.com.zup.beagle.ext

import br.com.zup.beagle.widget.Widget
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@DisplayName("Given an Style Component")
class WidgetExtensionsTest {

    private val widget: Widget = object : Widget() {}

    @DisplayName("When call id")
    @Nested
    inner class WidgetIdTest {

        @Test
        @DisplayName("Then should return widget")
        fun testWidgetSetId() {
            // GIVEN
            val id = "id"

            // WHEN
            val result = widget.setId(id)


            // THEN
            assertNotNull(result)
            assertEquals(id, result.id)
        }
    }
}