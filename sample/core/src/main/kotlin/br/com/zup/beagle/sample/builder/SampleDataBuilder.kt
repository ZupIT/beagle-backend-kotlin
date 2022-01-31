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

package br.com.zup.beagle.sample.builder

import kotlin.random.Random

object SampleDataBuilder {
    fun getStringArray(delay: Long = 0): List<String> {
        if(delay > 0)
            Thread.sleep(delay)
        return (1..Random.nextInt(100))
            .map { randomString() }
    }

    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    private fun randomString() =
        (1..15)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
}