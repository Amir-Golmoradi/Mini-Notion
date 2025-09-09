package dev.amirgol.springtaskbackend.attachment.application.port.outside;

import dev.amirgol.springtaskbackend.attachment.application.port.inside.AttachmentMetadata;
import dev.amirgol.springtaskbackend.attachment.application.usecase.DeleteAttachment;
import dev.amirgol.springtaskbackend.attachment.application.usecase.DownloadAttachment;
import dev.amirgol.springtaskbackend.attachment.application.usecase.FindAttachment;
import dev.amirgol.springtaskbackend.attachment.application.usecase.UploadAttachment;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AttachmentPort {
    Optional<AttachmentMetadata> findAttachment(FindAttachment attachment);
    void deleteAttachment(DeleteAttachment attachment);
    String uploadAttachment(UploadAttachment attachment);
    byte[] downloadAttachment(DownloadAttachment attachment);
}
