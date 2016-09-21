package com.hymane.materialhome;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        int l = 0;
        int r = 3000 * 1000;
        long count = 0;
        do {
            l = l + (r - l) / 2;
            count++;
        } while ((count <= 10000 && (r - l) > 2));
        System.out.println("count==" + count);

        assertEquals(4, 2 + 2);
    }
}