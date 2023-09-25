package br.com.fluentia.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JWTAuthentication extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = 1L;
    private final Long idUsuario;

    public JWTAuthentication(Object principal, Collection<? extends GrantedAuthority> authorities, Long idUsuario) {
        super(principal, null, authorities);
        this.idUsuario = idUsuario;
    }

    public Long getIdUsuario() {
        return this.idUsuario;
    }
    
}