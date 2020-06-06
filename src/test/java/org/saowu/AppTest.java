package org.saowu;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void aop() {
        Suitors proxy = (Suitors) new CglibProxyUtils()
                .getProxy(Suitors.class
                        , () -> System.out.println("befor")
                        , () -> System.out.println("after")
                );
        proxy.call();
    }
}
