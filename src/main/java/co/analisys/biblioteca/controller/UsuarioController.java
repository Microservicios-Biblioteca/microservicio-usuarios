package co.analisys.biblioteca.controller;

import co.analisys.biblioteca.model.Email;
import co.analisys.biblioteca.model.Usuario;
import co.analisys.biblioteca.model.UsuarioId;
import co.analisys.biblioteca.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "API para la gestión de usuarios de la biblioteca")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Obtener usuario por ID", description = "Retorna la información completa de un usuario dado su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado - Token JWT requerido"),
            @ApiResponse(responseCode = "403", description = "No autorizado - Rol insuficiente"),
            @ApiResponse(responseCode = "500", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public Usuario obtenerUsuario(
            @Parameter(description = "ID del usuario a consultar", required = true, example = "1") @PathVariable String id) {
        return usuarioService.obtenerUsuario(new UsuarioId(id));
    }

    @Operation(summary = "Cambiar email de usuario", description = "Actualiza la dirección de email de un usuario existente. "
            + "Requiere rol ADMIN o USUARIO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email actualizado exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado - Token JWT requerido"),
            @ApiResponse(responseCode = "403", description = "No autorizado - Se requiere rol ADMIN o USUARIO"),
            @ApiResponse(responseCode = "500", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}/email")
    public void cambiarEmail(
            @Parameter(description = "ID del usuario a actualizar", required = true, example = "1") @PathVariable String id,
            @Parameter(description = "Nueva dirección de email", required = true, example = "nuevo@email.com") @RequestBody String nuevoEmail) {
        usuarioService.cambiarEmailUsuario(new UsuarioId(id), new Email(nuevoEmail));
    }
}
