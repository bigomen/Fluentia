
package br.com.fluentia.security.jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {

    final Environment env;
    
    public JWTAuthenticationFilter(Environment env) 
    {
        super();
        this.env = env;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        TokenAuthenticationService tks = new TokenAuthenticationService(env);
        
		Authentication authentication = null;
		
		if(isRefreshToken(req))
		{
			authentication = tks.getRefreshAuthentication((HttpServletRequest)req, (HttpServletResponse)resp);
		} else {
			authentication = tks.getAuthentication((HttpServletRequest)req);
		}
		
		if (authentication != null) {
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        filterChain.doFilter(req, resp);
		} else {
			if (isRefreshToken(req)) {
				((HttpServletResponse)resp).setStatus(403);
				resp.flushBuffer();
			} else if (!isPublic(req)) {
				((HttpServletResponse)resp).setStatus(401);
				resp.flushBuffer();
			} else {
		        filterChain.doFilter(req, resp);
			}
		}
    }
    
    private boolean isRefreshToken(ServletRequest request)
    {
        if (request instanceof HttpServletRequest) {
            return ((HttpServletRequest)request).getRequestURI().contains("/refresh");
        }
        return false;
    }
    
    private boolean isPublic(ServletRequest req) {
        if (req instanceof HttpServletRequest) {
        	HttpServletRequest request = (HttpServletRequest) req;
	    	
	    	if (request.getRequestURI().indexOf("/fluentia-service/authenticate/forgot") == 0
	            || request.getRequestURI().indexOf("/fluentia-service/authenticate/register") == 0
	            || request.getRequestURI().indexOf("/fluentia-service/authenticate/login") == 0

	            || request.getRequestURI().indexOf("/fluentia-service/preMatricula/criar") == 0
	            || request.getRequestURI().indexOf("/fluentia-service/adesao/aluno") == 0
	            || request.getRequestURI().indexOf("/fluentia-service/aluno/criarAlunoAssinatura") == 0
	            || request.getRequestURI().indexOf("/fluentia-service/empresa/listaSimples") == 0
	            || request.getRequestURI().indexOf("/fluentia-service/preMatricula") == 0

	            //|| request.getRequestURI().indexOf("/fluentia-service/aluno/criar-auto") == 0
	            || request.getRequestURI().indexOf("/fluentia-service/plano/buscar") == 0
	            || request.getRequestURI().indexOf("/fluentia-service/plano/buscarTodos") == 0
	            || request.getRequestURI().indexOf("/fluentia-service/plano/listarAtivos") == 0
	                )
            return true;
        }
        return false;
    }

}

