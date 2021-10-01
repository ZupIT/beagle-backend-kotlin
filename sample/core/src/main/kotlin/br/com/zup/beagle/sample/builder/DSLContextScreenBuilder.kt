package br.com.zup.beagle.sample.builder

import br.com.zup.beagle.annotation.ContextObject
import br.com.zup.beagle.ext.setStyle
import br.com.zup.beagle.sample.GlobalObject
import br.com.zup.beagle.sample.change
import br.com.zup.beagle.sample.streetExpression
import br.com.zup.beagle.sample.widget.input
import br.com.zup.beagle.widget.context.Bind
import br.com.zup.beagle.widget.context.Context
import br.com.zup.beagle.widget.context.constant
import br.com.zup.beagle.widget.context.expressionOf
import br.com.zup.beagle.widget.core.AlignSelf
import br.com.zup.beagle.widget.core.EdgeValue
import br.com.zup.beagle.widget.core.Flex
import br.com.zup.beagle.widget.core.JustifyContent
import br.com.zup.beagle.widget.layout.Container
import br.com.zup.beagle.widget.layout.Screen
import br.com.zup.beagle.widget.layout.ScreenBuilder
import br.com.zup.beagle.widget.layout.ScrollView
import br.com.zup.beagle.widget.ui.Text

@ContextObject
data class Person(
    override val id: String,
    val firstName: String = "",
    val lastName: String = ""
) : Context

@ContextObject
data class MyContext(
    override val id: String,
    val value: String = "",
    val person: Person = Person("")
) : Context

object DSLContextScreenBuilder : ScreenBuilder {
    private var myContext = MyContext("myContext",
        person = Person(
            id = "person",
            firstName = "firstName local",
            lastName = "lastName local")
    )
    private var globalObject = GlobalObject()

    override fun build() = Screen(
        child = ScrollView(
            children = listOf(
                Container(
                    onInit = listOf(
                        globalObject.change(
                            GlobalObject("Street A",
                                Person("", "firstName global", "lastName global"))),
                        globalObject.person.change(Person("", "firstName global changed", "lastName global changed"))
                    ),
                    context = myContext,
                    children = listOf(
                        createTitle("DSL Context Screen"),
                        localContextInfo(),
                        globalContextInfo()
                    )
                ).setStyle {
                    padding = EdgeValue.only(left = 20, right = 20)
                    flex = Flex(
                        justifyContent = JustifyContent.SPACE_AROUND,
                        grow = 1.0
                    )
                }
            )
        )
    )

    private fun localContextInfo() = Container(
        children = listOf(
            createTitle("Local Context"),
            createText("Value"),
            input(
                hint = constant(""),
                onTextChange = {
                    listOf(
                        myContext.changeValue(expressionOf("@{${it}}"))
                    )
                }
            ),
            createText(myContext.valueExpression),
            createText("First name"),
            createText(myContext.person.firstNameExpression),
            createText("Last name"),
            createText(myContext.person.lastNameExpression)
        )
    )

    private fun globalContextInfo() = Container(
        children = listOf(
            createTitle("Global Context"),
            createText("FirstName"),
            createText(globalObject.person.firstNameExpression),
            createText("LastName"),
            createText(globalObject.person.lastNameExpression),
            createText("Street"),
            createText(globalObject.streetExpression)
        )
    )

    private fun createText(name: String) = Text(
        name, textColor = "#00c91b"
    ).setStyle {
        margin = EdgeValue.only(bottom = 20)
    }

    private fun createText(valueExpression: Bind.Expression<String>) = Text(
        valueExpression
    ).setStyle {
        margin = EdgeValue.only(bottom = 20)
    }

    private fun createTitle(title: String) = Text(
        text = title, textColor = "#345beb"
    ).setStyle {
        margin = EdgeValue.only(bottom = 20, top = 20)
        flex = Flex(alignSelf = AlignSelf.CENTER)
    }
}