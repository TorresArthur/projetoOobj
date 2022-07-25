package br.com.oobj.projectnf.seguranca;

import br.com.oobj.projectnf.service.TokenService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Profile("!teste")
public class AutenticacaoFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    public AutenticacaoFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recuperaraToken(request);
        boolean valido = tokenService.isTokenValido(token);
        System.out.println(valido);
        if(valido){
            autenticarCliente();
        }
        filterChain.doFilter(request, response);

    }
    @Bean
    public FilterRegistrationBean<AutenticacaoFilter> filter()
    {
        FilterRegistrationBean<AutenticacaoFilter> bean = new FilterRegistrationBean<>();

        bean.setFilter(new AutenticacaoFilter(tokenService));
        return bean;
    }

    private void autenticarCliente(){
        ApiKeyAuth auth = new ApiKeyAuth(AuthorityUtils.NO_AUTHORITIES);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
    private String recuperaraToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");

        if(token == null || !(token.startsWith("Bearer ")) ){
            return null;
        }
        return token.substring(7, token.length());
    }
}