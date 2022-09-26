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

package br.com.zup.beagle.spring.filter

import br.com.zup.beagle.platform.BeaglePlatformUtil
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.web.util.ContentCachingResponseWrapper
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class BeaglePlatformFilter(private val objectMapper: ObjectMapper) : Filter {

    override fun doFilter(
        request: ServletRequest?,
        response: ServletResponse?,
        chain: FilterChain?
    ) {
        val httpServletResponse = response as? HttpServletResponse
        val httpServletRequest = request as? HttpServletRequest
        val platformHeader = httpServletRequest?.getHeader(BeaglePlatformUtil.BEAGLE_PLATFORM_HEADER)
        val isServletRequestResponse = request is HttpServletRequest && response is HttpServletResponse

        if (chain != null && isServletRequestResponse && platformHeader != null) {
            request.setAttribute(BeaglePlatformUtil.BEAGLE_PLATFORM_HEADER, platformHeader)
            val responseWrapper = ContentCachingResponseWrapper(httpServletResponse)
            chain.doFilter(request, responseWrapper)
            treatResponse(responseWrapper, platformHeader)
            responseWrapper.copyBodyToResponse()
        }
    }

    private fun treatResponse(
        responseWrapper: ContentCachingResponseWrapper,
        currentPlatform: String?
    ) {
        if (responseWrapper.contentType == MediaType.APPLICATION_JSON_VALUE) {
            val jsonData = this.objectMapper.readTree(responseWrapper.contentAsByteArray).also {
                BeaglePlatformUtil.treatBeaglePlatform(currentPlatform, it)
            }
            responseWrapper.resetBuffer()
            responseWrapper.outputStream.write(this.objectMapper.writeValueAsBytes(jsonData))
        }
    }
}