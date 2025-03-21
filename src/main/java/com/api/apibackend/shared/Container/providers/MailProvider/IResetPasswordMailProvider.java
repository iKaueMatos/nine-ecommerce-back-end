/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nine
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.api.apibackend.shared.Container.providers.MailProvider;

import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;

public interface IResetPasswordMailProvider {
    public void resetPasswordMail(MimeMessageHelper helper, String resetCode, String templateType, String user) throws MessagingException;
}
