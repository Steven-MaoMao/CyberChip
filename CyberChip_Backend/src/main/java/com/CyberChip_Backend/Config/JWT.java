package com.CyberChip_Backend.Config;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWT {
    private static final String signature = "CyberChip";

    //  加密token
    public static String createToken(String roomName, String name) {
        JwtBuilder jwtBuilder = Jwts.builder();
        return jwtBuilder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("roomName", roomName)
                .claim("name", name)
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();
    }

    //  解密token
    public static Map parseToken(String jwtToken) {
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(jwtToken);
        Claims body = claimsJws.getBody();
        Map<String, String> map = new HashMap<>();
        map.put("roomName", body.get("roomName").toString());
        map.put("name", body.get("name").toString());
        return map;
    }
}
