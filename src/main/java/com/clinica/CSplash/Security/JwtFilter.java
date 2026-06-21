package com.clinica.CSplash.Security;

import com.clinica.CSplash.Repository.IUsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private TokensService tokensService;

    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Override

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var token = this.recoverToken(request);
        System.out.println("1. TOKEN RECUPERADO NO FILTRO: " + token); // 👈 ADICIONE ISSO!

        if (token != null) {
            var subject = tokensService.validarToken(token);
            System.out.println("2. EMAIL DE DENTRO DO TOKEN (SUBJECT): " + subject); // 👈 ADICIONE ISSO!

            if (!subject.isEmpty()) {
                UserDetails user = usuarioRepository.findByEmail(subject);
                System.out.println("3. USUARIO ENCONTRADO NO BANCO: " + (user != null ? user.getUsername() : "NULO")); // 👈 ADICIONE ISSO!

                if (user != null) {
                    var autenticacao = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(autenticacao);
                    System.out.println("4. SESSÃO DO SPRING SECURITY CONFIGURADA COM SUCESSO!"); // 👈 ADICIONE ISSO!
                }
            }
        }

        filterChain.doFilter(request, response);
    }
    private String recoverToken(HttpServletRequest request){

        var authorization=request.getHeader("Authorization");
        if (authorization==null){
            return null;
        }
        // Limpa a palavra Bearer com segurança removendo espaços extras
        if (authorization.toLowerCase().startsWith("bearer ")) {
            return authorization.substring(7).trim();
        }
        return authorization.trim();
    }
}
