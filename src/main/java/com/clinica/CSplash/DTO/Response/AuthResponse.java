package com.clinica.CSplash.DTO.Response;

import com.clinica.CSplash.Model.Enum.Cargo;

public record AuthResponse(

        String nome,
        String email,
        Cargo cargo


) {

}
