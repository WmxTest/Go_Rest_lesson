package rest;

import org.junit.jupiter.api.*;
import rest_assured.gorest_co_in.dto.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseRestTest {

    protected static User user;

    @BeforeAll
    public void beforeAll() {

    }
}