package com.efremovkirill.myapplication

import org.junit.Test

class UtilTest {

    @Test
    fun `good date`() {
        val result = Util.cutDate("2020-01-05 13:42:15.823766")

        assert(result == "2020-01-05 13:42:15") {
            "Date = $result"
        }
    }

    @Test
    fun `bad date`() {
        val result = Util.cutDate("2020-01-05 13:42:15.82376")

        assert(result == "2020-01-05 13:42:1") {
            "Date = $result"
        }
    }
}