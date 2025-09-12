package dev.amirgol.springtaskbackend.attachment.domain.value_object;

import dev.amirgol.springtaskbackend.core.common.ValueObject;
import lombok.Getter;

import java.util.Collection;
import java.util.List;

@Getter
public final class AttachmentSize extends ValueObject {
    private final long size;
    private static final long maxSize = 50L * 1024 * 1024;


    public AttachmentSize(long size) {
        validateSize(size);
        this.size = size;
    }


    public static AttachmentSize of(long size) {
        return new AttachmentSize(size);
    }

    private void validateSize(long size) {
        // Size Must be bigger than Zero
        if (size <= 0) {
            throw new IllegalStateException("Attachment size must be greater than zero.");
        }

        if (size > maxSize) {
            throw new IllegalStateException("Attachment size must be less than or equal to maximum size.");
        }

    }

    public boolean isGreaterThanMaxSize(AttachmentSize other) {
        return this.size > other.size;
    }

    public boolean isSmallerThanMaxSize(AttachmentSize other) {
        return this.size < other.size;
    }

    @Override
    public Collection<Object> getAtomicValues() {
        return List.of(size);
    }

}
