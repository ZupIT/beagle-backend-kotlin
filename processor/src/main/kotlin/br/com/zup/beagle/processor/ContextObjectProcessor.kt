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

import br.com.zup.beagle.annotation.ContextObject
import br.com.zup.beagle.annotation.GlobalContext
import br.com.zup.beagle.widget.context.Context
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.asTypeName
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@AutoService(Processor::class)
class ContextObjectProcessor: AbstractProcessor() {
    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(ContextObject::class.java.name, GlobalContext::class.java.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latest()

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        val contextObjectElements = roundEnv.getElementsAnnotatedWith(ContextObject::class.java)

        contextObjectElements
            .forEach {
                if (isAnnotationValid(it)) {
                    processAnnotation(it)
                } else {
                    return true
                }
            }

        val globalContextElements = roundEnv.getElementsAnnotatedWith(GlobalContext::class.java)
        if (globalContextElements.count() > 1) {
            processingEnv.messager.printMessage(
                Diagnostic.Kind.ERROR,
                "Just one class can be annotated with @GlobalContext"
            )
            return true
        }

        globalContextElements.forEach { processAnnotation(it, true) }

        return false
    }

    private fun isAnnotationValid(element: Element): Boolean {
        if (element.kind != ElementKind.CLASS) {
            processingEnv.messager.printMessage(
                Diagnostic.Kind.ERROR,
                "Only classes can be annotated with @ContextObject"
            )
            return false
        }

        val typeElement = processingEnv.elementUtils.getTypeElement(element.asType().toString())
        val inheritFromContextObject = typeElement.interfaces.any { int ->
            int.asTypeName().toString() == Context::class.java.name
        }

        if (!inheritFromContextObject) {
            processingEnv.messager.printMessage(
                Diagnostic.Kind.ERROR,
                "Only classes that inherit from Context can be annotated with @ContextObject",
                element
            )
            return false
        }

        return true
    }

    private fun processAnnotation(element: Element, isGlobal: Boolean = false) {
        val builder = ContextObjectExtensionsFileBuilder(element, processingEnv, isGlobal)

        val file = builder.build()
        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        file.writeTo(File(kaptKotlinGeneratedDir))
    }
}