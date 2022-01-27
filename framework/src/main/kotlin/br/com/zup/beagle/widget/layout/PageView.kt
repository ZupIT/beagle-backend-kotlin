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

package br.com.zup.beagle.widget.layout

import br.com.zup.beagle.core.ServerDrivenComponent
import br.com.zup.beagle.widget.action.Action
import br.com.zup.beagle.widget.context.Bind
import br.com.zup.beagle.widget.context.Context
import br.com.zup.beagle.widget.context.ContextComponent

/**
 *  The PageView component is a specialized container to hold pages (views) that will be displayed horizontally.
 *
 * @param children define a List of components (views) that are contained on this PageView. Consider the
 * @param context define the context that be set to pageView.
 * @param onPageChange List of actions that are performed when you are on the selected page.
 * @param currentPage Integer number that identifies that selected.
 * @param showArrow This attribute is specific to the web platform, with which it allows you to place the arrows
 * to change pages.
 */
data class PageView(
    val children: List<ServerDrivenComponent>? = null,
    override val context: Context? = null,
    val onPageChange: List<Action>? = null,
    val currentPage: Bind<Int>? = null,
    val showArrow: Boolean? = null
) : ServerDrivenComponent, ContextComponent
