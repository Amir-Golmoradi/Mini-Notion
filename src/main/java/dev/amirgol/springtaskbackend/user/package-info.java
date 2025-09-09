@ApplicationModule(
        displayName = "User",
        allowedDependencies = { "task::TaskModel", "core::Exceptions" }
)
package dev.amirgol.springtaskbackend.user;

import org.springframework.modulith.ApplicationModule;