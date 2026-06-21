package com.clinica.CSplash.Controllers;

import com.clinica.CSplash.DTO.Request.CadastroRequest;
import com.clinica.CSplash.DTO.Request.LoginRequest;
import com.clinica.CSplash.DTO.Response.AuthResponse;
import com.clinica.CSplash.DTO.Response.LoginResponse;
import com.clinica.CSplash.Model.Usuario;
import com.clinica.CSplash.Security.TokensService;
import com.clinica.CSplash.Service.AutenticacaoService;
import com.clinica.CSplash.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {
    @Autowired
    private AutenticacaoService autenticacaoService;


    @Autowired
    private UsuarioService usuarioService;



    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokensService tokensService;

    @PostMapping("/cadastrar")
    public ResponseEntity<AuthResponse> cadastrar(@RequestBody @Valid CadastroRequest cadastroRequest) {
        return new ResponseEntity<>(usuarioService.cadastrarUsuario(cadastroRequest), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        // Cria o token de autenticação com o email e senha digitados
        var userPassWord = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.senha());

        //O AuthenticationManager valida se a senha bate com o banco de dados
        var authentication = authenticationManager.authenticate(userPassWord);

        //  Pega o usuário que foi autenticado com sucesso
        var usuario = (Usuario) authentication.getPrincipal();

        //GERAR o token de verdade para o usuário!
        String token = tokensService.gerarToken(usuario);


        // Devolve os dados no AuthResponse com o status 202 Accepted
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new LoginResponse(token));
    }
}


