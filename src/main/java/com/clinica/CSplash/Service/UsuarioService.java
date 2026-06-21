package com.clinica.CSplash.Service;


import com.clinica.CSplash.DTO.Request.CadastroRequest;
import com.clinica.CSplash.DTO.Request.LoginRequest;
import com.clinica.CSplash.DTO.Response.AuthResponse;
import com.clinica.CSplash.Model.Usuario;
import com.clinica.CSplash.Repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse cadastrarUsuario(CadastroRequest request){
        Usuario usuario=new Usuario();
                usuario.setNome(request.nome());
                usuario.setEmail(request.email());
                usuario.setSenha(passwordEncoder.encode(request.senha()));
                usuario.setCargo(request.cargo());


                usuarioRepository.save(usuario);

                return new AuthResponse(usuario.getNome(),usuario.getEmail(),usuario.getCargo());
    }

    public AuthResponse loginUsuario(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha())
        );

        Usuario usuario=(Usuario)usuarioRepository.findByEmail(request.email());

        if(usuario==null){
            throw new UsernameNotFoundException("Usuario não encontrado");
        }

        return new AuthResponse(usuario.getNome(),usuario.getEmail(),usuario.getCargo());

    }
}
