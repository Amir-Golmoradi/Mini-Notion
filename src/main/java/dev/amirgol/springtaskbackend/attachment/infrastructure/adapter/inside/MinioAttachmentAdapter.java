package dev.amirgol.springtaskbackend.attachment.infrastructure.adapter.outside;

import dev.amirgol.springtaskbackend.attachment.application.port.inside.AttachmentMetadata;
import dev.amirgol.springtaskbackend.attachment.application.port.outside.AttachmentPort;
import dev.amirgol.springtaskbackend.attachment.application.usecase.DeleteAttachment;
import dev.amirgol.springtaskbackend.attachment.application.usecase.DownloadAttachment;
import dev.amirgol.springtaskbackend.attachment.application.usecase.FindAttachment;
import dev.amirgol.springtaskbackend.attachment.application.usecase.UploadAttachment;
import dev.amirgol.springtaskbackend.core.exception.StorageException;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MinioAttachmentAdapter implements AttachmentPort {
    private final MinioClient client;

    @Override
    public Optional<AttachmentMetadata> findAttachment(FindAttachment storage) {
        try {
            StatObjectResponse status = client.statObject(
                    StatObjectArgs
                            .builder()
                            .bucket(storage.bucketName())
                            .object(storage.objectName())
                            .build()
            );
            return Optional.of(
                    AttachmentMetadata
                            .builder()
                            .objectName(storage.objectName())
                            .size(status.size())
                            .contentType(status.contentType())
                            .build()
            );

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteAttachment(DeleteAttachment storage) {
        try {
            client.removeObject(
                    RemoveObjectArgs
                            .builder()
                            .bucket(storage.bucketName())
                            .object(storage.objectName())
                            .build());

        } catch (Exception e) {
            throw new StorageException("Failed to delete attachment: " + storage.objectName(), e);
        }
    }

    @Override
    public String uploadAttachment(UploadAttachment storage) {
        try {
            client
                    .putObject(
                            PutObjectArgs
                                    .builder()
                                    .bucket(storage.bucketName())
                                    .object(storage.objectName())
                                    .stream(storage.data(), -1, 10485760)
                                    .contentType(storage.contentType())
                                    .build()
                    );
            return storage.objectName();
        } catch (Exception e) {
            throw new StorageException("Failed to upload attachment: " + storage.objectName(), e);
        }
    }

    @Override
    public byte[] downloadAttachment(DownloadAttachment storage) {
        try (InputStream stream = client.getObject(
                GetObjectArgs
                        .builder()
                        .bucket(storage.bucketName())
                        .object(storage.objectName())
                        .build()
        )) {
            return stream.readAllBytes();
        } catch (Exception e) {
            throw new StorageException("Failed to download attachment: " + storage.objectName(), e);
        }
    }
}
