package me.levani.authorizationserver.model.core;

import me.levani.authorizationserver.model.response.CertificateResponseBody;

public interface CertificateManager {
    CertificateResponseBody getCertificates(String realms);
}
