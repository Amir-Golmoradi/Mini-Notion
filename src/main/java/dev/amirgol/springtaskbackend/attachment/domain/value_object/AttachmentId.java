package dev.amirgol.springtaskbackend.attachment.domain.value_object;

import dev.amirgol.springtaskbackend.core.common.ValueObject;
import dev.amirgol.springtaskbackend.core.exception.NullOrBlankException;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class AttachmentId extends ValueObject {
    private final UUID id;

    public AttachmentId(UUID id) {
        validateId(id);
        this.id = id;
    }


    public static AttachmentId of(UUID id) {
        return new AttachmentId(id);
    }

    private void validateId(UUID id) {
        // Id cannot be Null or Empty
        if (Objects.isNull(id) || id.equals(new UUID(0, 0))) {
            throw new NullOrBlankException("AttachmentId");
        }
    }

    @Override
    public Collection<Object> getAtomicValues() {
        return List.of(id);
    }
}
