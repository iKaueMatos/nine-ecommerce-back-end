package com.api.apibackend.modules.Mail.Application.repository;

public interface ISendEmail {
    void sendTextEmail(String to, String subject, String body);
    void sendHtmlEmail(String to, String subject, String htmlBody);
    void sendAttachmentEmail(String to, String subject, String body, String attachmentPath);
}
