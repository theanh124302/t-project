package tproject.authservice.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tproject.authservice.service.JwtService;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtServiceImpl.class);

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode("ugytuytquwyetutqutqwuetyuytyutyyuqtweuytyuytutqwuetututqwueytuytuqytweuytuytqweqeadasdqweqweasd");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    @Override
    public String generateToken(UserDetails userDetails){
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+60*60*240000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
//
//    @Override
//    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails){
//        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis()+60*60*240000))
//                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }




    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
        log.info("Claims: {}", claims);
        return claimsResolvers.apply(claims);
    }


    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return (extractClaim(token, Claims::getSubject).equals(userDetails.getUsername()) &&
                !extractClaim(token, Claims::getExpiration).before(new Date()));
    }
}


