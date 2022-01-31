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

package br.com.zup.beagle.serialization.action.stubs

import br.com.zup.beagle.widget.action.Alert
import br.com.zup.beagle.widget.action.Confirm

fun makeActionAlertJson() =
    """
    {
        "_beagleAction_": "beagle:alert",
        "title": "A title",
        "message": "A message",
        "onPressOk": [{
             "_beagleAction_": "beagle:alert",
             "title": "Another title",
             "message": "Another message",
             "labelOk": "Ok"
        }],
        "labelOk": "Ok"
    }
    """

fun makeActionAlertObject() = Alert(
    title = "A title",
    message = "A message",
    labelOk = "Ok",
    onPressOk = listOf(
        Alert(
            title = "Another title",
            message = "Another message",
            labelOk = "Ok"
        )
    )
)

fun makeActionConfirmJson() =
    """
    {
        "_beagleAction_": "beagle:confirm",
        "title": "A title",
        "message": "A message",
        "onPressOk": [{
             "_beagleAction_": "beagle:alert",
             "title": "Another title",
             "message": "Another message",
             "labelOk": "Ok"
        }],
        "onPressCancel": [{
             "_beagleAction_": "beagle:alert",
             "title": "Another title",
             "message": "Another message",
             "labelOk": "Ok"
        }],
        "labelOk": "Ok",
        "labelCancel": "Cancel"
    }
    """

fun makeActionConfirmObject() = Confirm(
    title = "A title",
    message = "A message",
    labelOk = "Ok",
    onPressOk = listOf(
        Alert(
            title = "Another title",
            message = "Another message",
            labelOk = "Ok"
        )
    ),
    labelCancel = "Cancel",
    onPressCancel = listOf(
        Alert(
            title = "Another title",
            message = "Another message",
            labelOk = "Ok"
        )
    )
)