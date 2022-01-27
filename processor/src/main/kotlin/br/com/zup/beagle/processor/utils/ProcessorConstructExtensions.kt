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

package br.com.zup.beagle.processor.utils

import br.com.zup.beagle.widget.context.Bind
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import javax.lang.model.element.Element
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.Types
import kotlin.reflect.KClass
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap
import kotlin.reflect.jvm.internal.impl.name.FqName

val TypeMirror.elementType: TypeMirror get() = if (this is DeclaredType) this.typeArguments[0] else this

fun Types.isIterable(type: TypeMirror): Boolean = this.isSubtype(type, Iterable::class)

fun Types.isSubtype(type: TypeMirror, superType: KClass<*>): Boolean =
    this.isSubtype(type, superType.java.asTypeName().toString())

tailrec fun Types.getFinalElementType(type: TypeMirror): TypeMirror =
    if (this.isIterable(type)) this.getFinalElementType(type.elementType) else type

fun Types.isSubtype(type: TypeMirror, superTypeName: String): Boolean =
    when (this.erasure(type).asTypeName().toString()) {
        Any::class.java.canonicalName -> false
        superTypeName -> true
        else -> this.directSupertypes(type).any { this.isSubtype(it, superTypeName) }
    }

internal fun Types.isLeaf(type: TypeMirror) =
    type.kind.isPrimitive || type.asTypeName() in LEAF_TYPES || this.isSubtype(type, Enum::class)

fun TypeName.asBindType() = Bind.Expression::class.asTypeName().parameterizedBy(listOf(this))

fun TypeName.javaToKotlinType(): TypeName {
    return if (this is ParameterizedTypeName) {
        (rawType.javaToKotlinType() as ClassName)
            .parameterizedBy(*typeArguments.map { it.javaToKotlinType() }.toTypedArray())
    } else {
        val className =
            JavaToKotlinClassMap.INSTANCE.mapJavaToKotlin(FqName(toString()))
                ?.asSingleFqName()?.asString()

        return if (className == null) {
            this
        } else {
            ClassName.bestGuess(className)
        }
    }
}

fun Element.isNullable(): Boolean = getAnnotation(org.jetbrains.annotations.Nullable::class.java) != null