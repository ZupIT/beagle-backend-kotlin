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

package br.com.zup.beagle.processor.utils

import br.com.zup.beagle.annotation.ImplicitContext
import br.com.zup.beagle.widget.context.Context
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.type.MirroredTypeException

class WidgetFileBuilder(
    private val element: Element,
    processingEnvironment: ProcessingEnvironment
) {
    private val elementUtils = processingEnvironment.elementUtils
    private val className = element.simpleName.toString()
    private val pack = elementUtils.getPackageOf(element).toString()
    private val fileName = "${className}DSL"
    private val fileBuilder = FileSpec.builder(pack, fileName)
    private val classTypeName = element.asType().asTypeName()
    private val functionBuilder = FunSpec
        .builder(className.decapitalize())
        .returns(classTypeName)

    fun build(): FileSpec {
        val fields = element.enclosedElements.filter { it.kind == ElementKind.FIELD }

        if (containsImplicitContext(fields)) {
            val widgetFunction = createWidgetFunction(fields)
            fileBuilder.addFunction(widgetFunction.build())
        }

        return fileBuilder.build()
    }

    private fun createWidgetFunction(fields: List<Element>): FunSpec.Builder {
        fields.forEach { field ->
            val annotation = field.getAnnotation(ImplicitContext::class.java)

            if (annotation == null) {
                addParameter(field)
            } else {
                addImplicitContextParameter(field, annotation)
            }
        }

        resolveCodeBlock(functionBuilder, fields)
        return functionBuilder
    }

    private fun resolveCodeBlock(functionBuilder: FunSpec.Builder, fields: List<Element>) {
        var codeBlock = fields.fold("return ${className}(") { str, field ->
            str +
                if (field.getAnnotation(ImplicitContext::class.java) == null)
                    (field.simpleName.toString() + ", ")
                else
                    revolveImplicitContext(field)
        }
        codeBlock += ")"

        functionBuilder.addCode(codeBlock)
    }

    private fun revolveImplicitContext(field: Element): String {
        return try {
            resolveInvokeMethod(field, field.getAnnotation(ImplicitContext::class.java).input.asTypeName())
        } catch (e: MirroredTypeException) {
            resolveInvokeMethod(field, e.typeMirror.asTypeName())
        }
    }

    private fun resolveInvokeMethod(field: Element, typeName: TypeName): String {
        val id = resolveImplicitId(field)
        return (field.toString() + "${if (field.isNullable()) "?" else ""}.invoke(${typeName}(${Context::id.name} = \"${id}\"))")
    }

    private fun resolveImplicitId(field: Element): String {
        val annotation = field.getAnnotation(ImplicitContext::class.java)
        return if (annotation != null && annotation.id.isNotEmpty())
            annotation.id
        else
            field.simpleName.toString()
    }

    private fun addImplicitContextParameter(field: Element, annotation: ImplicitContext) {
        try {
            addLambdaParameter(field, annotation.input.asTypeName())
        } catch (e: MirroredTypeException) {
            addLambdaParameter(field, e.typeMirror.asTypeName())
        }
    }

    private fun addLambdaParameter(field: Element, inputType: TypeName) {
        val parameterType = LambdaTypeName.get(
            parameters = arrayOf(inputType),
            returnType = field.asType().asTypeName().javaToKotlinType()
        ).copy(nullable = field.isNullable())
        functionBuilder.addParameter(createParameter(field, parameterType).build())
    }

    private fun addParameter(field: Element) {
        val parameterType = field.asType().asTypeName().javaToKotlinType().copy(nullable = field.isNullable())
        functionBuilder.addParameter(createParameter(field, parameterType).build())
    }

    private fun createParameter(field: Element, parameterType: TypeName): ParameterSpec.Builder {
        val parameter = ParameterSpec.builder(name = field.simpleName.toString(), type = parameterType)
        if (field.isNullable()) {
            parameter.defaultValue("%L", null)
        }
        return parameter
    }

    private fun containsImplicitContext(fields: List<Element>): Boolean {
        fields.forEach { field ->
            if (field.getAnnotation(ImplicitContext::class.java) != null)
                return true
        }
        return false
    }
}