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

package br.com.zup.beagle.processor

import br.com.zup.beagle.annotation.ContextObject
import br.com.zup.beagle.annotation.GlobalContext
import br.com.zup.beagle.widget.context.Context

@ContextObject
data class Person(
    override val id: String = "",
    val name: String,
    val age: Int,
    val orders: List<Order>,
    val address: Address
): Context {
    constructor(id: String): this(id, "", 12, listOf(), Address(""))
}

@ContextObject
data class Order(
    override val id: String = "",
    val products: List<String>,
    val value: Double
): Context {
    constructor(id: String): this(id, listOf(), 0.0)
}

@ContextObject
data class Address(
    override val id: String = "",
    val street: String,
    val zupCode: String,
    val contact: Contact
): Context {
    constructor(id: String): this(id, "", "", Contact(""))
}

@ContextObject
data class Contact(
    override val id: String = "",
    val email: String,
    val number: String
): Context {
    constructor(id: String): this(id, "", "")
}

@GlobalContext
data class Global(
    val name: String,
    val age: Int,
    val contact: Contact
)