package ru.niceone.math.tests

import org.junit.Assert
import org.junit.Test

import static java.lang.Math.PI
import static ru.niceone.math.Math.sec

class SecTest {
    final double PRECISION = 1E-6;

    @Test
    public void nan() {
        Assert.assertEquals(Double.NaN, sec(Double.NaN), PRECISION);
    }

    @Test
    public void negative_infinity() {
        Assert.assertEquals(Double.NaN, sec(Double.NEGATIVE_INFINITY), PRECISION);
    }

    @Test
    public void positive_infinity() {
        Assert.assertEquals(Double.NaN, sec(Double.POSITIVE_INFINITY), PRECISION);
    }

    @Test
    public void near_zero_negative() {
        Assert.assertEquals(1.0000500020834180589907146248, sec(-0.01), PRECISION);
    }

    @Test
    public void zero() {
        Assert.assertEquals(1.0, sec(0.0), PRECISION);
    }

    @Test
    public void near_zero_positive() {
        Assert.assertEquals(1.0000500020834180589907146248, sec(0.01), PRECISION);
    }

    @Test
    public void near_half_PI_less() {
        Assert.assertEquals(100.00166668611131613966602662726726, sec(PI/2.0 - 0.01), PRECISION);
    }

    @Test
    public void half_PI() {
        Assert.assertEquals(Double.POSITIVE_INFINITY, sec(PI/2.0), PRECISION);
    }

    @Test
    public void near_half_PI_greater() {
        Assert.assertNotEquals(-100.00166668611131613966602662726726, sec(PI/2.0 + 0.01), PRECISION);
    }
}
