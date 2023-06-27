package com.asierg.cqrspoc.shared.domain

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.cfg.ConstructorDetector
import com.fasterxml.jackson.databind.json.JsonMapper

class JacksonObjectMapper {

    companion object {

        private val PROPERTY_BASED_OBJECT_MAPPER: ObjectMapper = JsonMapper.builder()
            .constructorDetector(ConstructorDetector.USE_PROPERTIES_BASED)
            .findAndAddModules()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .build()

        private val DEFAULT_OBJECT_MAPPER: ObjectMapper = JsonMapper.builder()
            .findAndAddModules()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .build()

        fun convertValue(body: Any, clazz: Class<*>): Any {
            return readValue(writeValueAsString(body), clazz)
        }

        private fun writeValueAsString(body: Any?): String {
            return try {
                PROPERTY_BASED_OBJECT_MAPPER.writeValueAsString(body)
            } catch (ignored: JsonProcessingException) {
                try {
                    DEFAULT_OBJECT_MAPPER.writeValueAsString(body)
                } catch (exception: JsonProcessingException) {
                    throw RuntimeException("Domain message serialization was not possible", exception)
                }
            }
        }

        private fun readValue(body: String, clazz: Class<*>): Any {
            return try {
                PROPERTY_BASED_OBJECT_MAPPER.readValue(body, clazz)
            } catch (ignored: JsonProcessingException) {
                try {
                    DEFAULT_OBJECT_MAPPER.readValue(body, clazz)
                } catch (exception: JsonProcessingException) {
                    throw java.lang.RuntimeException("Body decoding was not possible for $clazz : $body", ignored)
                }
            }
        }
    }
}
