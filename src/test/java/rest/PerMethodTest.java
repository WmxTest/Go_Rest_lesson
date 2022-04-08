package rest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@Disabled
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class PerMethodTest {

    private final long currentTime = System.currentTimeMillis();

    @Test
    void name1() {
       System.out.println(currentTime);
    }

    @Test
    void name2() {
        System.out.println(currentTime);
    }

    @Test
    void name3() {
        System.out.println(currentTime);
    }
}

/* TestInstance.Lifecycle.PER_CLASS
1649401785105
1649401785105
1649401785105

TestInstance.Lifecycle.PER_METHOD
1649401841476
1649401841511
1649401841515
*/