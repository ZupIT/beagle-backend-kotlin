package br.com.zup.beagle.sample.builder

import br.com.zup.beagle.ext.setStyle
import br.com.zup.beagle.sample.GlobalObject
import br.com.zup.beagle.sample.MyContext
import br.com.zup.beagle.sample.Person
import br.com.zup.beagle.sample.change
import br.com.zup.beagle.sample.changeValue
import br.com.zup.beagle.sample.firstNameExpression
import br.com.zup.beagle.sample.lastNameExpression
import br.com.zup.beagle.sample.normalize
import br.com.zup.beagle.sample.streetExpression
import br.com.zup.beagle.sample.valueExpression
import br.com.zup.beagle.sample.widget.sampleTextField
import br.com.zup.beagle.sample.widget.valueExpression
import br.com.zup.beagle.widget.context.Bind
import br.com.zup.beagle.widget.core.AlignSelf
import br.com.zup.beagle.widget.core.EdgeValue
import br.com.zup.beagle.widget.core.Flex
import br.com.zup.beagle.widget.core.JustifyContent
import br.com.zup.beagle.widget.layout.Container
import br.com.zup.beagle.widget.layout.Screen
import br.com.zup.beagle.widget.layout.ScreenBuilder
import br.com.zup.beagle.widget.layout.ScrollView
import br.com.zup.beagle.widget.ui.Text

object DSLContextScreenBuilder : ScreenBuilder {
    private var myContext = MyContext("myContext",
        person = Person(
            id = "person",
            firstName = "firstName local",
            lastName = "lastName local")
    ).normalize("myContext")
    private var globalObject = GlobalObject().normalize()

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
            sampleTextField(
                placeholder = "",
                onChange = {
                    listOf(
                        myContext.changeValue(it.valueExpression)
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