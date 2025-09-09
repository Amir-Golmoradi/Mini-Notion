package dev.amirgol.springtaskbackend;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ModulithTest {

    @Test
    void verifyModules() {
        ApplicationModules modules = ApplicationModules.of(App.class);
        modules.verify();
    }
}
