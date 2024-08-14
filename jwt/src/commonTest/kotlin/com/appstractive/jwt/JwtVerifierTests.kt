﻿package com.appstractive.jwt

import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.hours

class JwtVerifierTests {

    @Test
    fun testVerifyAudSuccess() = runTest {
        val jwt = signedJwt {
            algorithm(
                type = Algorithm.HS256,
                algorithm = MockSignerVerifier,
            )
        }

        val result = jwt.verify {
            verifier(
                type = Algorithm.HS256,
                algorithm = MockSignerVerifier,
            )

            audience = "api.example.com"
        }

        assertTrue(result)
    }

    @Test
    fun testVerifyAudFail() = runTest {
        val jwt = signedJwt {
            algorithm(
                type = Algorithm.HS256,
                algorithm = MockSignerVerifier,
            )
        }

        val result = jwt.verify {
            verifier(
                type = Algorithm.HS256,
                algorithm = MockSignerVerifier,
            )

            audience = "example.com"
        }

        assertFalse(result)
    }

    @Test
    fun testVerifyIssSuccess() = runTest {
        val jwt = signedJwt {
            algorithm(
                type = Algorithm.HS256,
                algorithm = MockSignerVerifier,
            )
        }

        val result = jwt.verify {
            verifier(
                type = Algorithm.HS256,
                algorithm = MockSignerVerifier,
            )

            issuer = "example.com"
        }

        assertTrue(result)
    }

    @Test
    fun testVerifyIssFail() = runTest {
        val jwt = signedJwt {
            algorithm(
                type = Algorithm.HS256,
                algorithm = MockSignerVerifier,
            )
        }

        val result = jwt.verify {
            verifier(
                type = Algorithm.HS256,
                algorithm = MockSignerVerifier,
            )

            issuer = "example2.com"
        }

        assertFalse(result)
    }

    @Test
    fun testVerifyExpSuccess() = runTest {
        val jwt = signedJwt {
            algorithm(
                type = Algorithm.HS256,
                algorithm = MockSignerVerifier,
            )
        }

        val result = jwt.verify {
            verifier(
                type = Algorithm.HS256,
                algorithm = MockSignerVerifier,
            )

            expiresAt()
        }

        assertTrue(result)
    }

    @Test
    fun testVerifyExpFail() = runTest {
        val jwt = signedJwt {
            algorithm(
                type = Algorithm.HS256,
                algorithm = MockSignerVerifier,
            )
        }

        val result = jwt.verify {
            verifier(
                type = Algorithm.HS256,
                algorithm = MockSignerVerifier,
            )

            expiresAt(now = Clock.System.now() + 25.hours)
        }

        assertFalse(result)
    }

    @Test
    fun testVerifyNbfSuccess() = runTest {
        val jwt = signedJwt {
            algorithm(
                type = Algorithm.HS256,
                algorithm = MockSignerVerifier,
            )
        }

        val result = jwt.verify {
            verifier(
                type = Algorithm.HS256,
                algorithm = MockSignerVerifier,
            )

            notBefore()
        }

        assertTrue(result)
    }

    @Test
    fun testVerifyNbfFail() = runTest {
        val jwt = signedJwt {
            algorithm(
                type = Algorithm.HS256,
                algorithm = MockSignerVerifier,
            )
        }

        val result = jwt.verify {
            verifier(
                type = Algorithm.HS256,
                algorithm = MockSignerVerifier,
            )

            notBefore(now = Clock.System.now() - 1.hours)
        }

        assertFalse(result)
    }

}