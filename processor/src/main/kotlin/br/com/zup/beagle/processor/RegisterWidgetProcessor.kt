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

import br.com.zup.beagle.annotation.ImplicitContext
import br.com.zup.beagle.annotation.RegisterWidget
import br.com.zup.beagle.processor.utils.isNullable
import br.com.zup.beagle.processor.utils.javaToKotlinType
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.asTypeName
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.lang.model.type.MirroredTypeException

@AutoService(Processor::class)
class RegisterWidgetProcessor : AbstractProcessor() {
    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(RegisterWidget::class.java.name)
    }
    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latest()
    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        roundEnv.getElementsAnnotatedWith(RegisterWidget::class.java).forEach { processAnnotation(it) }
        return false
    }
    private fun processAnnotation(element: Element) {
        val className = element.simpleName.toString()
        val pack = processingEnv.elementUtils.getPackageOf(element).toString()
        val fileName = "${className}DSL"
        val fileBuilder = FileSpec.builder(pack, fileName)
        val fields = element.enclosedElements.filter { it.kind == ElementKind.FIELD }
        val classTypeName = element.asType().asTypeName()
        var isGenerate = false
        val functionBuilder = FunSpec
            .builder(className.decapitalize())
            .returns(element.asType().asTypeName())
        fields.forEach { field ->
            val annotation = field.getAnnotation(ImplicitContext::class.java)

            if (annotation == null) {
                var parameterType = field.asType().asTypeName().javaToKotlinType().copy(nullable = field.isNullable())
                var parameter = ParameterSpec.builder(name = field.simpleName.toString(), type = parameterType)
                if (field.isNullable()) {
                    parameter.defaultValue("%L", null)
                }
                functionBuilder.addParameter(parameter.build())
            } else {

                isGenerate = true
                try {
                    var parameterType = LambdaTypeName.get(
                        parameters = arrayOf(annotation.input.asTypeName()),
                        returnType = field.asType().asTypeName().javaToKotlinType()
                    ).copy(nullable = field.isNullable())

                    var parameter = ParameterSpec.builder(name = field.simpleName.toString(), type = parameterType)
                    if (field.isNullable()) {
                        parameter.defaultValue("%L", null)
                    }

                    functionBuilder.addParameter(
                        parameter.build()
                    )

                } catch (e: MirroredTypeException) {
                    var parameterType = LambdaTypeName.get(
                        parameters = arrayOf(e.typeMirror.asTypeName()),
                        returnType = field.asType().asTypeName().javaToKotlinType()
                    ).copy(nullable = field.isNullable())

                    var parameter = ParameterSpec.builder(name = field.simpleName.toString(), type = parameterType)
                    if (field.isNullable()) {
                        parameter.defaultValue("%L", null)
                    }

                    functionBuilder.addParameter(
                        parameter.build()
                    )
                }
            }
        }
        var codeBlock = fields.fold("return ${className}(") { str, field ->
            str +
                if(field.getAnnotation(ImplicitContext::class.java) == null)
                    (field.simpleName.toString() + ",")
                else
                    try {
                        (field.toString() + "${if (field.isNullable()) "?" else ""}.invoke(${field.getAnnotation(ImplicitContext::class.java).input.asTypeName()}(id = ${field.simpleName}))" )
                    } catch (e: MirroredTypeException) {
                        (field.simpleName.toString() + "${if (field.isNullable()) "?" else ""}.invoke(${e.typeMirror.asTypeName()}(id = \"${field.simpleName}\"))" )
                    }
        }
        codeBlock += ")"
        functionBuilder.addCode(codeBlock)
        if (isGenerate) {
            fileBuilder.addFunction(functionBuilder.build())
            val file = fileBuilder.build()
            val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
            file.writeTo(File(kaptKotlinGeneratedDir))
        }
    }
}