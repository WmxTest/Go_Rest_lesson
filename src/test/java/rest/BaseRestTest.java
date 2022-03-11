package rest;

import org.junit.jupiter.api.*;
import rest_assured.gorest_co_in.dto.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseRestTest {

    protected static User user;

    @BeforeAll
    public void beforeAll() {

    }
}