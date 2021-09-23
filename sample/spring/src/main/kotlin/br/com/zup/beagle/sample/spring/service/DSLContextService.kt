package br.com.zup.beagle.sample.spring.service

import br.com.zup.beagle.sample.builder.DSLContextScreenBuilder
import org.springframework.stereotype.Service

@Service
class DSLContextService {
    fun createDSLContextScreen() = DSLContextScreenBuilder
}
