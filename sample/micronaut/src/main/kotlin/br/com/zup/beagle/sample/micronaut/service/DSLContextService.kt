package br.com.zup.beagle.sample.micronaut.service

import br.com.zup.beagle.sample.builder.DSLContextScreenBuilder
import javax.inject.Singleton

@Singleton
class DSLContextService {
    fun createDSLContextScreen() = DSLContextScreenBuilder
}
