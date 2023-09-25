package br.com.fluentia.security.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TokenAuthenticationService {
    
    private final long EXPIRATION_TIME;
    private final long REFRESH_EXPIRATION_TIME;
    private final String SECRET;
    public static final String HEADER_AUTHORIZATION = "Authorization";
	public static final String HEADER_REFRESH = "Refresh";
	public static final String BEARER = "Bearer ";
    
    public TokenAuthenticationService(Environment env) {
        this.EXPIRATION_TIME = Long.parseLong(env.getProperty("jwt.token.validity"));
        this.REFRESH_EXPIRATION_TIME = Long.parseLong(env.getProperty("jwt.token.refresh.validity"));
        this.SECRET = env.getProperty("jwt.signing.key"); 
    }

 
    public Authentication getRefreshAuthentication(HttpServletRequest request,	HttpServletResponse response)
	{
		String token = request.getHeader(HEADER_AUTHORIZATION);
        try {
			if (token != null) {

                SignedJWT jwsObject = SignedJWT.parse( token.replace(BEARER, "") );
                JWSVerifier verifier = new MACVerifier( SECRET.getBytes() );
                if ( jwsObject.verify( verifier )) {
                    Date expirationTime = jwsObject.getJWTClaimsSet().getExpirationTime();
                    if (expirationTime.before(new Date())) {
                        return null;
                    }



                    JWTClaimsSet claim =  JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
    			
    				String email = claim.getSubject();
                    List<String> role = claim.getStringListClaim("role");
                    String nomeUsuario = claim.getStringClaim("name");
                    Long usuario = claim.getLongClaim("usuario");


                    response.addHeader(HEADER_AUTHORIZATION, generateToken(email, role, nomeUsuario, usuario, EXPIRATION_TIME, SECRET));
                    response.addHeader(HEADER_REFRESH, generateToken(email, role, nomeUsuario, usuario, REFRESH_EXPIRATION_TIME, SECRET));

                    return new JWTAuthentication(email, getGrantedAuthorities(role), usuario);
                }
            }
        } catch (Exception e) {
            return null;
        }
		return null;
	}
    
    


	public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_AUTHORIZATION);

        try {
            if (token != null) {
                
                SignedJWT jwsObject = SignedJWT.parse( token.replace(BEARER, "") );
                JWSVerifier verifier = new MACVerifier( SECRET.getBytes() );
                if ( jwsObject.verify( verifier )) {
                    
                    Date expirationTime = jwsObject.getJWTClaimsSet().getExpirationTime();
                    if (expirationTime.before(new Date())) {
                        return null;
                    }
                    
                    JWTClaimsSet claim =  JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
    			
    				String email = claim.getSubject();
                    List<String> role = claim.getStringListClaim("role");
                    Long usuario = claim.getLongClaim("usuario");

                    return new JWTAuthentication(email, getGrantedAuthorities(role), usuario);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
    
    
    private List<GrantedAuthority> getGrantedAuthorities(List<String> role) {
        
    	
        List<GrantedAuthority> authorities = new ArrayList<>();
        /*
        for (String s : role)
            authorities.add(new SimpleGrantedAuthority(s));
        */
        if (role != null && role.size() > 0)
        	authorities.add(new SimpleGrantedAuthority("AUTENTICADO"));
        return authorities;
    }

    public String generateToken(String username, List<String> rolesGrupo, 
    		String nomeUsuario, Long idUsuario, long expirationTime, String secret)
	{
        try {
        	
            
            JWSSigner signer = new MACSigner(secret.getBytes());
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(username)
                    .expirationTime(new Date(System.currentTimeMillis() + expirationTime))
                    .claim("role", rolesGrupo)
                    .claim("name", nomeUsuario)
                    .claim("usuario", idUsuario)
                    .build();

            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(claims.toJSONObject()));

            
            
            jwsObject.sign(signer);
            return "Bearer " + jwsObject.serialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}

    
    public void updateContext(String email, List<String> rolesGrupo, Long idUsuario) {
        SecurityContextHolder.getContext().setAuthentication(new JWTAuthentication(email, getGrantedAuthorities(rolesGrupo), idUsuario));
    }

}