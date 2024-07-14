package util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.squirrel.constant.SecurityConstants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author luobaosong
 * @date 2024-06-13 17:51
 */
public class JwtTokenUtils {

    /**
     * 访问token密钥
     */
    public static final String ACCESS_TOKEN_SECRET = "mySecretKeyForAccessToken";
    private static final byte[] API_KEY_SECRET_BYTES = DatatypeConverter.parseBase64Binary(ACCESS_TOKEN_SECRET);
    private static final SecretKeySpec SECRET_KEY = new SecretKeySpec(API_KEY_SECRET_BYTES, "HmacSHA256");


    /**
     * 刷新token密钥
     */
    public static final String REFRESH_TOKEN_SECRET = "mySecretKeyForRefreshToken";
    private static final byte[] REFRESH_API_KEY_SECRET_BYTES = DatatypeConverter.parseBase64Binary(REFRESH_TOKEN_SECRET);
    private static final SecretKeySpec REFRESH_SECRET_KEY = new SecretKeySpec(REFRESH_API_KEY_SECRET_BYTES, "HmacSHA256");


    /**
     * 访问token有效期
     */
    public static final long ACCESS_TOKEN_EXPIRATION = 60_000;
    /**
     * 刷新token有效期
     */
    public static final long REFRESH_TOKEN_EXPIRATION = 1_209_600_000;


    public static String generateToken(String userName, Integer userId, List<String> roles) {
        return JWT.create()
                .withSubject(userName)
                .withJWTId(String.valueOf(userId))
                .withClaim(SecurityConstants.ROLE_CLAIMS, roles)
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC512(SECRET_KEY.getEncoded()));
    }

    public static String generateRefreshToken(String userName, Integer userId, List<String> roles) {
        return JWT.create()
                .withSubject(userName)
                .withJWTId(String.valueOf(userId))
                .withClaim(SecurityConstants.ROLE_CLAIMS, roles)
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC512(REFRESH_SECRET_KEY.getEncoded()));
    }


    private static DecodedJWT getJwt(String token, SecretKeySpec secretKey) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(secretKey.getEncoded())).build();
        return jwtVerifier.verify(token);
    }


    public static String getId(String token) {
        DecodedJWT jwt = getJwt(token, SECRET_KEY);
        return jwt.getId();
    }

    public static String getRefreshId(String token) {
        DecodedJWT jwt = getJwt(token, REFRESH_SECRET_KEY);
        return jwt.getId();
    }

    private static List<SimpleGrantedAuthority> getAuthorities(DecodedJWT jwt) {
        Map<String, Claim> claims = jwt.getClaims();
        return Arrays.asList(claims.get(SecurityConstants.ROLE_CLAIMS).asArray(SimpleGrantedAuthority.class));
    }


    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        DecodedJWT jwt = getJwt(token, SECRET_KEY);
        List<SimpleGrantedAuthority> authorities = getAuthorities(jwt);
        String userName = jwt.getSubject();
        return new UsernamePasswordAuthenticationToken(userName, token, authorities);
    }


}
