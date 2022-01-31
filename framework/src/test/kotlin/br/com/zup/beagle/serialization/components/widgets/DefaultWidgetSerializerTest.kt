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

package br.com.zup.beagle.serialization.components.widgets

import br.com.zup.beagle.serialization.DefaultSerializerTest
import br.com.zup.beagle.serialization.components.stubs.makeButtonJson
import br.com.zup.beagle.serialization.components.stubs.makeContainerJson
import br.com.zup.beagle.serialization.components.stubs.makeContainerWithCustomContextJson
import br.com.zup.beagle.serialization.components.stubs.makeImageWithLocalPathJson
import br.com.zup.beagle.serialization.components.stubs.makeImageWithRemotePathJson
import br.com.zup.beagle.serialization.components.stubs.makeJsonGridView
import br.com.zup.beagle.serialization.components.stubs.makeLazyComponentJson
import br.com.zup.beagle.serialization.components.stubs.makeListViewJson
import br.com.zup.beagle.serialization.components.stubs.makeObjectButton
import br.com.zup.beagle.serialization.components.stubs.makeObjectContainer
import br.com.zup.beagle.serialization.components.stubs.makeObjectContainerWithCustomContext
import br.com.zup.beagle.serialization.components.stubs.makeObjectGridView
import br.com.zup.beagle.serialization.components.stubs.makeObjectImageWithLocalPath
import br.com.zup.beagle.serialization.components.stubs.makeObjectImageWithRemotePath
import br.com.zup.beagle.serialization.components.stubs.makeObjectLazyComponent
import br.com.zup.beagle.serialization.components.stubs.makeObjectListView
import br.com.zup.beagle.serialization.components.stubs.makeObjectScreenComponent
import br.com.zup.beagle.serialization.components.stubs.makeObjectScrollView
import br.com.zup.beagle.serialization.components.stubs.makeObjectSimpleForm
import br.com.zup.beagle.serialization.components.stubs.makeObjectTabBar
import br.com.zup.beagle.serialization.components.stubs.makeObjectText
import br.com.zup.beagle.serialization.components.stubs.makeObjectTextInput
import br.com.zup.beagle.serialization.components.stubs.makeObjectTextInputWithExpression
import br.com.zup.beagle.serialization.components.stubs.makeObjectTouchable
import br.com.zup.beagle.serialization.components.stubs.makeObjectWebView
import br.com.zup.beagle.serialization.components.stubs.makeObjectWebViewWithExpression
import br.com.zup.beagle.serialization.components.stubs.makePullToRefreshJson
import br.com.zup.beagle.serialization.components.stubs.makePullToRefreshObject
import br.com.zup.beagle.serialization.components.stubs.makePullToRefreshWithoutExpressionJson
import br.com.zup.beagle.serialization.components.stubs.makePullToRefreshWithoutExpressionObject
import br.com.zup.beagle.serialization.components.stubs.makeScreenComponentJson
import br.com.zup.beagle.serialization.components.stubs.makeScrollViewJson
import br.com.zup.beagle.serialization.components.stubs.makeSimpleFormJson
import br.com.zup.beagle.serialization.components.stubs.makeTabBarJson
import br.com.zup.beagle.serialization.components.stubs.makeTextInputJson
import br.com.zup.beagle.serialization.components.stubs.makeTextInputWithExpressionJson
import br.com.zup.beagle.serialization.components.stubs.makeTextJson
import br.com.zup.beagle.serialization.components.stubs.makeTouchableJson
import br.com.zup.beagle.serialization.components.stubs.makeWebViewJson
import br.com.zup.beagle.serialization.components.stubs.makeWebViewWithExpressionJson
import br.com.zup.beagle.widget.Widget
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.provider.Arguments

@DisplayName("Given a Widget")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DefaultWidgetSerializerTest : DefaultSerializerTest<Widget>() {
    override fun testArguments() = listOf(
        Arguments.of(makeButtonJson(), makeObjectButton()),
        Arguments.of(makeContainerJson(), makeObjectContainer()),
        Arguments.of(makeJsonGridView(), makeObjectGridView()),
        Arguments.of(makeImageWithLocalPathJson(), makeObjectImageWithLocalPath()),
        Arguments.of(makeImageWithRemotePathJson(), makeObjectImageWithRemotePath()),
        Arguments.of(makeLazyComponentJson(), makeObjectLazyComponent()),
        Arguments.of(makeListViewJson(), makeObjectListView()),
        Arguments.of(makeScreenComponentJson(), makeObjectScreenComponent()),
        Arguments.of(makeScrollViewJson(), makeObjectScrollView()),
        Arguments.of(makeSimpleFormJson(), makeObjectSimpleForm()),
        Arguments.of(makeTabBarJson(), makeObjectTabBar()),
        Arguments.of(makeTextInputJson(), makeObjectTextInput()),
        Arguments.of(makeTextInputWithExpressionJson(), makeObjectTextInputWithExpression()),
        Arguments.of(makeTextJson(), makeObjectText()),
        Arguments.of(makeTouchableJson(), makeObjectTouchable()),
        Arguments.of(makeWebViewJson(), makeObjectWebView()),
        Arguments.of(makeWebViewWithExpressionJson(), makeObjectWebViewWithExpression()),
        Arguments.of(makePullToRefreshJson(), makePullToRefreshObject()),
        Arguments.of(makePullToRefreshWithoutExpressionJson(), makePullToRefreshWithoutExpressionObject()),
        Arguments.of(makeContainerWithCustomContextJson(), makeObjectContainerWithCustomContext()),
    )
}