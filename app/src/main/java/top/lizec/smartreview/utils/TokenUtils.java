package top.lizec.smartreview.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import top.lizec.smartreview.entity.User;

import java.util.Date;

public class TokenUtils {
    private static final long ONE_DAY = 24 * 60 * 60 * 1000;
    private static final long EXPIRATION = 30 * ONE_DAY;

    /**
     * 生成 Token 字符串 必须 setAudience 接收者 setExpiration 过期时间 role 用户角色
     */
    public static String createToken(User user) {
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION);
        String token = Jwts.builder()
                .setIssuer("SmartReview")
                .setAudience(user.getEmail())
                .setExpiration(expirationDate)
                .setIssuedAt(new Date())
                .claim("role", user.getRoles())
                .claim("name", user.getUsername())
                .claim("userId", user.getId())
                .signWith(RsaUtils.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
        return String.format("Bearer %s", token);
    }


    /**
     * 验证 Token ，并获取到用户名和用户权限信息
     *
     * @param token Token 字符串
     * @return sysUser 用户信息
     */
    public static User validationToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(RsaUtils.getPublicKey())
                    .build().parseClaimsJws(token).getBody();

            User user = new User();
            user.setEmail(claims.getAudience());
            user.setUsername(claims.get("name", String.class));
            user.setRoles(claims.get("role", String.class));
            user.setId(claims.get("userId", Integer.class));
            return user;
        } catch (ExpiredJwtException e) {
            // 如果过期
            return null;
        }


    }

}
