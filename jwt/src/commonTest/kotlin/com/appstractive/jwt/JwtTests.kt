﻿package com.appstractive.jwt

import com.appstractive.jwt.utils.claim
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.time.Duration.Companion.hours

private enum class TestEnum {
    VALUE1,
    VALUE2,
    VALUE3,
}

@Serializable
private data class TestClass(
    val long: Long,
    val int: Int,
    val double: Double,
    val float: Float,
    val bool: Boolean,
    val string: String,
    val enum: TestEnum,
    val list: List<TestClass>,
    val nullable: Nothing? = null,
)

class JwtTests {

    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun createJwt() = runTest {
        val jwt = jwt {
            header {

            }

            claims {
                issuer = "example.com"
                subject = "me"
                audience = "api.example.com"
                expiresAt = Clock.System.now() + 24.hours
                notBeforeNow()
                issuedNow()
                id = "123456"

                claim("double", 1.1)
                claim("long", 1L)
                claim("bool", true)
                claim("string", "test")
                claim("null", null)
                objectClaim("object") {
                    put("key", JsonPrimitive("value"))
                }
                arrayClaim("list") {
                    add(JsonPrimitive(0))
                    add(JsonPrimitive(1))
                    add(JsonPrimitive(2))
                }

                claim(
                    key = "complex",
                    value = TestClass(
                        long = 123443543642353L,
                        int = 1,
                        double = 124321412.325325,
                        float = 1.2414f,
                        bool = true,
                        string = "test",
                        enum = TestEnum.VALUE1,
                        list = listOf(
                            TestClass(
                                long = 123443543642353L,
                                int = 1,
                                double = 124321412.325325,
                                float = 1.2414f,
                                bool = false,
                                string = "test2",
                                enum = TestEnum.VALUE2,
                                list = emptyList(),
                            ),
                        ),
                    ),
                )
            }

            signature {
                hs256 {
                    secret = "your-256-bit-secret"
                }
            }
        }

        println(jwt.toString())
    }

}