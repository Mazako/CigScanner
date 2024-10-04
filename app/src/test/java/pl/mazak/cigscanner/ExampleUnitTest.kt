package pl.mazak.cigscanner

import org.junit.Test

import org.junit.Assert.*
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        var a1 = BigDecimal.valueOf(100.0).setScale(10)
        var a2 = BigDecimal.valueOf(4.3355).setScale(10)
        var r = a1 / a2
        r = r.setScale(2, RoundingMode.HALF_UP)
        println(r)
        println(BigDecimal.valueOf(0.023).setScale(2, RoundingMode.HALF_UP))
        println(BigDecimal.valueOf(0.024).setScale(2, RoundingMode.HALF_UP))
        println(BigDecimal.valueOf(0.020).setScale(2, RoundingMode.HALF_UP))
        println(BigDecimal.valueOf(0.025).setScale(2, RoundingMode.HALF_UP))
        println(BigDecimal.valueOf(0.026).setScale(2, RoundingMode.HALF_UP))
    }
}