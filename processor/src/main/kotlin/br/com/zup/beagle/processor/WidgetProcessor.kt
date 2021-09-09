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
import br.com.zup.beagle.annotation.ImplicitContext
import br.com.zup.beagle.annotation.RegisterWidget
import br.com.zup.beagle.processor.utils.WidgetFileBuilder
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
import javax.lang.model.type.MirroredTypeException
import javax.lang.model.type.TypeMirror
import javax.tools.Diagnostic

@AutoService(Processor::class)
class WidgetProcessor : AbstractProcessor() {
    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(RegisterWidget::class.java.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latest()

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        roundEnv.getElementsAnnotatedWith(RegisterWidget::class.java).forEach {
            if (isValidImplicitContext(it))
                processAnnotation(it)
        }
        return false
    }

    private fun processAnnotation(element: Element) {
        val builder = WidgetFileBuilder(element, processingEnv)

        val file = builder.build()
        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        file.writeTo(File(kaptKotlinGeneratedDir))
    }

    private fun isValidImplicitContext(element: Element): Boolean {
        val fields = element.enclosedElements.filter { it.kind == ElementKind.FIELD }

        fields.forEach { field ->

            val annotation = field.getAnnotation(ImplicitContext::class.java)
            if (annotation != null) {
                val typeMirror = getTypeMirror(annotation)
                if (typeMirror?.asTypeName().toString() != "java.lang.String") {
                    try {
                        if (processingEnv.typeUtils.asElement(typeMirror)
                                .getAnnotation(ContextObject::class.java) == null) {
                            return errorImplicitContext(field)
                        }
                    } catch (e: Exception) {
                        return errorImplicitContext(field)
                    }
                }
            }
        }

        return true
    }

    private fun getTypeMirror(annotation: ImplicitContext): TypeMirror? {
        try {
            annotation.inputClass
        } catch (e: MirroredTypeException) {
            return e.typeMirror
        }

        return null
    }

    private fun errorImplicitContext(element: Element): Boolean {
        processingEnv.messager.printMessage(
            Diagnostic.Kind.ERROR,
            "Only classes that inherit from Context can be used for implicit context",
            element
        )
        return false
    }
}