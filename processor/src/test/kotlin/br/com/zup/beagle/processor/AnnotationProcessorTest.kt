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

package br.com.zup.beagle.processor

import br.com.zup.beagle.widget.action.SetContext
import br.com.zup.beagle.widget.context.Bind
import br.com.zup.beagle.widget.context.expressionOf
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Given a context annotated with ContextObject")
internal class AnnotationProcessorTest {

    companion object {
        const val contextId = "contextId"
        const val globalId = "global"

        private val person = Person(
            name = "yan",
            age = 23,
            address = Address(
                street = "Av Rondon",
                zupCode = "000-000",
                contact = Contact(
                    email = "yan@mail.com",
                    number = "9999-9999"
                )
            ),
            orders = listOf(
                Order(
                    products = listOf(),
                    value = 10.000
                )
            )
        ).normalize(contextId)

        private val global = Global(
            name = "yan",
            age = 23,
            contact = Contact(
                email = "yan@mail.com",
                number = "00000000"
            )
        ).normalize()
    }

    @DisplayName("When use normalize")
    @Nested
    inner class NormalizerTest {

        @DisplayName("Then should updated context ids")
        @Test
        fun testGeneratedNormalize() {
            val expectedAddressContextId = "$contextId.${Person::address.name}"
            val expectedContactContextId = "$contextId.${Person::address.name}.${Address::contact.name}"
            val expectedOrderContextId = "$contextId.${Person::orders.name}[0]"

            Assertions.assertEquals(contextId, person.id)
            Assertions.assertEquals(expectedAddressContextId, person.address.id)
            Assertions.assertEquals(expectedContactContextId, person.address.contact.id)
            Assertions.assertEquals(expectedOrderContextId, person.orders[0].id)
        }
    }

    @DisplayName("When use expressions")
    @Nested
    inner class ExpressionsTests {

        @DisplayName("Then should return correct expressions with correct types")
        @Test
        fun testGeneratedRootExpression() {
            val expectedPersonExpression: Bind.Expression<Int> = expressionOf("@{${person.id}}")
            val expectedAddressExpression: Bind.Expression<String> = expressionOf("@{${person.address.id}}")
            val expectedContactExpression: Bind.Expression<String> = expressionOf("@{${person.address.contact.id}}")
            val expectedOrdersExpression: Bind.Expression<List<String>> = expressionOf("@{$contextId.${Person::orders.name}}")
            val expectedOrderElementExpression: Bind.Expression<Order> = expressionOf("@{${person.orders[0].id}}")

            Assertions.assertEquals(expectedPersonExpression, person.expression)
            Assertions.assertEquals(expectedAddressExpression, person.address.expression)
            Assertions.assertEquals(expectedAddressExpression, person.addressExpression)
            Assertions.assertEquals(expectedContactExpression, person.address.contact.expression)
            Assertions.assertEquals(expectedContactExpression, person.address.contactExpression)
            Assertions.assertEquals(expectedOrdersExpression, person.ordersExpression)
            Assertions.assertEquals(expectedOrderElementExpression, person.orders[0].expression)
        }

        @DisplayName("Then should return correct expressions with correct types")
        @Test
        fun testGeneratedExpressions() {
            val expectedAgeExpression: Bind.Expression<Int> = expressionOf("@{${person.id}.${Person::age.name}}")
            val expectedStreetExpression: Bind.Expression<String> = expressionOf("@{${person.address.id}.${Address::street.name}}")
            val expectedEmailExpression: Bind.Expression<String> = expressionOf("@{${person.address.contact.id}.${Contact::email.name}}")
            val expectedProductsExpression: Bind.Expression<List<String>> = expressionOf("@{${person.orders[0].id}.${Order::products.name}}")

            Assertions.assertEquals(expectedAgeExpression, person.ageExpression)
            Assertions.assertEquals(expectedStreetExpression, person.address.streetExpression)
            Assertions.assertEquals(expectedEmailExpression, person.address.contact.emailExpression)
            Assertions.assertEquals(expectedProductsExpression, person.orders[0].productsExpression)
        }
    }

    @DisplayName("When use change")
    @Nested
    inner class ChangeTests {

        @DisplayName("Then should return a SetContext action with correct attributes")
        @Test
        fun testGeneratedRootChangeFunctions() {
            val newAddress = Address(id = contextId)
            val newPerson = Person(id = contextId)
            val newContact = Contact(id = contextId)
            val newOrder = Order(id = contextId)

            assertChange(newPerson, null, person.change(newPerson))
            assertChange(newAddress, Person::address.name, person.address.change(newAddress))
            assertChange(newContact, "${Person::address.name}.${Address::contact.name}", person.address.contact.change(newContact))
            assertChange(newOrder, Person::orders.name + "[0]", person.orders[0].change(newOrder))
        }

        @DisplayName("Then should return a SetContext action with correct attributes")
        @Test
        fun testGeneratedRootChangeFunctionsBind() {
            val newAddressBind = expressionOf<Address>("@{context.address}")
            val newPersonBind = expressionOf<Person>("@{context.person}")
            val newContactBind = expressionOf<Contact>("@{context.contact}")
            val newOrderBind = expressionOf<Order>("@{context.order}")

            assertChange(newPersonBind, null, person.change(newPersonBind))
            assertChange(newAddressBind, Person::address.name, person.address.change(newAddressBind))
            assertChange(newContactBind, "${Person::address.name}.${Address::contact.name}", person.address.contact.change(newContactBind))
            assertChange(newOrderBind, Person::orders.name + "[0]", person.orders[0].change(newOrderBind))
        }

        @DisplayName("Then should return a SetContext action with correct attributes")
        @Test
        fun testGeneratedChangeFunctions() {
            val newName = "pocas"
            val newStreet = "Av Joao Naves"
            val newEmail = "pocas@mail.com"
            val newValue = 15.000

            assertChange(newName, Person::name.name, person.changeName(newName))
            assertChange(newStreet, "${Person::address.name}.${Address::street.name}", person.address.changeStreet(newStreet))
            assertChange(newEmail, "${Person::address.name}.${Address::contact.name}.${Contact::email.name}", person.address.contact.changeEmail(newEmail))
            assertChange(newValue, "${Person::orders.name}[0].${Order::value.name}", person.orders[0].changeValue(newValue))
        }

        @DisplayName("Then should return a SetContext action with correct attributes")
        @Test
        fun testGeneratedChangeFunctionsBind() {
            val newName = expressionOf<String>("context.name")
            val newStreet = expressionOf<String>("context.street")
            val newEmail = expressionOf<String>("context.email")
            val newValue = expressionOf<Double>("context.value")

            assertChange(newName, Person::name.name, person.changeName(newName))
            assertChange(newStreet, "${Person::address.name}.${Address::street.name}", person.address.changeStreet(newStreet))
            assertChange(newEmail, "${Person::address.name}.${Address::contact.name}.${Contact::email.name}", person.address.contact.changeEmail(newEmail))
            assertChange(newValue, "${Person::orders.name}[0].${Order::value.name}", person.orders[0].changeValue(newValue))
        }
    }

    @DisplayName("When use list access functions")
    @Nested
    inner class ListAccessTest {

        @DisplayName("Then should return a element with correct contextId")
        @Test
        fun testGeneratedGetElementFunctions() {
            val index = 40
            val expectedOrderContextId = "contextId.orders[$index]"

            Assertions.assertEquals(expectedOrderContextId, person.ordersGetElementAt(index).id)
            Assertions.assertEquals(person.orders[0], person.ordersGetElementAt(0), "When element exist it must return it")
        }

        @DisplayName("Then should return a SetContext with correct attributes")
        @Test
        fun testGeneratedChangeElementFunctions() {
            val index = 30
            val newProduct = "newProduct"
            val newOrder = Order(id = "contextId", products = listOf("newProducts"), value = 30.00)

            assertChange(newProduct, "${Person::orders.name}[0].${Order::products.name}[$index]", person.orders[0].changeProductsElement(newProduct, index))
            assertChange(newOrder, "${Person::orders.name}[$index]", person.changeOrdersElement(newOrder, index))
        }

        @DisplayName("Then should return a SetContext with correct attributes")
        @Test
        fun testGeneratedChangeElementFunctionsBind() {
            val index = 30
            val newProduct = expressionOf<String>("context.product")
            val newOrder = expressionOf<Order>("context.order")

            assertChange(newProduct, "${Person::orders.name}[0].${Order::products.name}[$index]", person.orders[0].changeProductsElement(newProduct, index))
            assertChange(newOrder, "${Person::orders.name}[$index]", person.changeOrdersElement(newOrder, index))
        }
    }

    @DisplayName("When use Global Context generated functions")
    @Nested
    inner class GlobalContextTest {

        @DisplayName("Then should update context ids")
        @Test
        fun testNormalize() {
            val expectedContactContextId = "global.${Global::contact.name}"

            Assertions.assertEquals(expectedContactContextId, global.contact.id)
        }

        @DisplayName("Then should return correct expressions")
        @Test
        fun testExpressionProperties() {
            val expectedGlobalExpression: Bind.Expression<Global> = expressionOf("@{global}")
            val expectedNameExpression: Bind.Expression<String> = expressionOf("@{global.${Global::name.name}}")

            Assertions.assertEquals(expectedGlobalExpression, global.expression)
            Assertions.assertEquals(expectedNameExpression, global.nameExpression)
        }

        @DisplayName("Then should return correct SetContexts")
        @Test
        fun textChangeFunctions() {
            val newName = expressionOf<String>("context.name")
            val expectedSetContext = SetContext(contextId = "global", value = newName, path = "name")

            Assertions.assertEquals(expectedSetContext, global.changeName(newName))
        }
    }

    private fun assertChange(value: Any, path: String?, setContext: SetContext) {
        val expectedSetContact = SetContext(contextId = contextId, value = value, path = path)
        Assertions.assertEquals(expectedSetContact, setContext)
    }
}