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

package br.com.zup.beagle.serialization.action

import br.com.zup.beagle.serialization.DefaultSerializerTest
import br.com.zup.beagle.serialization.action.stubs.makeActionAlertJson
import br.com.zup.beagle.serialization.action.stubs.makeActionAlertObject
import br.com.zup.beagle.serialization.action.stubs.makeActionConfirmJson
import br.com.zup.beagle.serialization.action.stubs.makeActionConfirmObject
import br.com.zup.beagle.serialization.action.stubs.makeActionNavigationPopStackJson
import br.com.zup.beagle.serialization.action.stubs.makeActionNavigationPopToViewJson
import br.com.zup.beagle.serialization.action.stubs.makeActionNavigationPopViewJson
import br.com.zup.beagle.serialization.action.stubs.makeActionNavigationPushStackJson
import br.com.zup.beagle.serialization.action.stubs.makeActionNavigationPushViewJson
import br.com.zup.beagle.serialization.action.stubs.makeActionNavigationResetApplicationJson
import br.com.zup.beagle.serialization.action.stubs.makeActionNavigationResetStackJson
import br.com.zup.beagle.serialization.action.stubs.makeObjectNavigationPopStack
import br.com.zup.beagle.serialization.action.stubs.makeObjectNavigationPopToView
import br.com.zup.beagle.serialization.action.stubs.makeObjectNavigationPopView
import br.com.zup.beagle.serialization.action.stubs.makeObjectNavigationPushStack
import br.com.zup.beagle.serialization.action.stubs.makeObjectNavigationPushView
import br.com.zup.beagle.serialization.action.stubs.makeObjectNavigationResetApplication
import br.com.zup.beagle.serialization.action.stubs.makeObjectNavigationResetStack
import br.com.zup.beagle.widget.action.Action
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.provider.Arguments

@DisplayName("Given an Action")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DefaultActionSerializerTest : DefaultSerializerTest<Action>() {
    override fun testArguments() = listOf(
        Arguments.of(makeActionNavigationPushViewJson(), makeObjectNavigationPushView()),
        Arguments.of(makeActionNavigationPushStackJson(), makeObjectNavigationPushStack()),
        Arguments.of(makeActionNavigationPopStackJson(), makeObjectNavigationPopStack()),
        Arguments.of(makeActionNavigationPopViewJson(), makeObjectNavigationPopView()),
        Arguments.of(makeActionNavigationPopToViewJson(), makeObjectNavigationPopToView()),
        Arguments.of(makeActionNavigationResetApplicationJson(), makeObjectNavigationResetApplication()),
        Arguments.of(makeActionNavigationResetStackJson(), makeObjectNavigationResetStack()),
        Arguments.of(makeActionNavigationResetStackJson(), makeObjectNavigationResetStack()),
        Arguments.of(makeActionNavigationResetStackJson(), makeObjectNavigationResetStack()),
        Arguments.of(makeActionAlertJson(), makeActionAlertObject()),
        Arguments.of(makeActionConfirmJson(), makeActionConfirmObject()),
    )
}