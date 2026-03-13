package com.dimata.util.jwt;

import com.dimata.util.FileUtil;
import com.dimata.util.KeyUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.KeyAlgorithm;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

@ApplicationScoped
public class JwtHandler {

    private static final Logger log = LoggerFactory.getLogger(JwtHandler.class);
    private final String signKeyPath;
    private final String publicKeyPath;
    private final KeyPair keyPair;

    public static final KeyAlgorithm<PublicKey, PrivateKey> KEY_ALG = Jwts.KEY.RSA_OAEP_256;
    public static final AeadAlgorithm ENC_ALG = Jwts.ENC.A256GCM;

    public static final int EXPIRED_IN_SEC = 24 * 3600;

    private final String issuer;
    private final String audience;

    public JwtHandler(
            @ConfigProperty(name = "otw.jwt.sign-key") String signKeyPath,
            @ConfigProperty(name = "otw.jwt.ency-key") String encyKeyPath,
            @ConfigProperty(name = "otw.jwt.issuer") String issuer,
            @ConfigProperty(name = "otw.jwt.audience") String audience
    ) {
        this.signKeyPath = signKeyPath;
        this.publicKeyPath = encyKeyPath;
        this.keyPair = buildKey();
        this.issuer = issuer;
        this.audience = audience;
    }

    public String signJwt(JwtAppendable claims) {
        var update = claims.appendToBuilder(getDefaultBuilder());
        return signJwt(update);
    }

    public String signJwtWithExternalKey(JwtAppendable claims, PrivateKey privateKey) {
        var update = claims.appendToBuilder(getDefaultBuilder());
        return signJwtWithExternalKey(update, privateKey);
    }

    public String signJwt(JwtBuilder builder) {
        return builder.signWith(keyPair.getPrivate()).compact();
    }

    public String signJwtWithExternalKey(JwtBuilder builder, PrivateKey privateKey) {
        return builder.signWith(privateKey).compact();
    }

    /**
     * Ency jwt dengan inputan claims. Issuer dan Audience sudah di preinput.
     * Jika ingin mengganti itu, bisa di set ulang.
     *
     * @param claims claim payload
     * @return ency jwt.
     */
    public String encyJwt(JwtAppendable claims) {
        var update = claims.appendToBuilder(getDefaultBuilder());
        return encyJwt(update);
    }

    /**
     * Ency jwt dengan inputan claims. Issuer dan Audience sudah di preinput.
     * Enkripsi akan menggunakan public key inputan.
     *
     * @param claims    claim payload
     * @param publicKey key untuk enkripsi
     * @return ency jwt.
     * @see KeyUtil#buildRsaPublicKey(String)
     */
    public String encyJwtWithExternalKey(JwtAppendable claims, PublicKey publicKey) {
        var update = claims.appendToBuilder(getDefaultBuilder());
        return encyJwtWithExternalKey(update, publicKey);
    }

    public String encyJwt(JwtBuilder builder) {
        return builder.encryptWith(keyPair.getPublic(), KEY_ALG, ENC_ALG).compact();
    }

    public String encyJwtWithExternalKey(JwtBuilder builder, PublicKey publicKey) {
        return builder.encryptWith(publicKey, KEY_ALG, ENC_ALG).compact();
    }

    public Claims readJws(String jws) {
        var payload = Jwts.parser()
                .verifyWith(keyPair.getPublic())
                .build()
                .parseSignedClaims(jws)
                .getPayload();
        validatePayload(payload);
        return payload;
    }

    public Claims readJwsWithExternalKey(String jws, PublicKey publicKey) {
        var payload = Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(jws)
                .getPayload();

        validatePayload(payload);
        return payload;
    }

    public Claims decryptJwe(String jwe) {
        var payload = Jwts.parser()
                .decryptWith(keyPair.getPrivate())
                .build()
                .parseEncryptedClaims(jwe)
                .getPayload();

        validatePayload(payload);
        return payload;
    }

    public Claims decryptJweWithExternalKey(String jwe, PrivateKey privateKey) {
        var payload = Jwts.parser()
                .decryptWith(privateKey)
                .build()
                .parseEncryptedClaims(jwe)
                .getPayload();

        validatePayload(payload);
        return payload;
    }

    public RsaKeyGenResult generatePrivateAndPublicKey() {
        var keyPair = Jwts.SIG.RS256.keyPair().build();
        return new RsaKeyGenResult(keyPair);
    }

    private void validatePayload(Claims claim) {
        if (!claim.getAudience().contains(audience)) {
            throw new IllegalArgumentException("Audience salah");
        }

        if (!claim.getIssuer().equals(issuer)) {
            throw new IllegalArgumentException("Bukan jwe kita");
        }
    }

    private KeyPair buildKey() {
        try {
            var privateKey = FileUtil.getByteByFromResources(signKeyPath);
            var publicKey = FileUtil.getByteByFromResources(publicKeyPath);
            return KeyUtil.buildRsaKeyPair(new String(privateKey), new String(publicKey));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JwtBuilder getDefaultBuilder() {
        return Jwts.builder()
                .issuer(issuer)
                .audience().add(audience).and();
    }
}


