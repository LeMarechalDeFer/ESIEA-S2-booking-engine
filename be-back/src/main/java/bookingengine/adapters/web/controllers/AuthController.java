package bookingengine.adapters.web.controllers;

import bookingengine.adapters.web.dto.ConnexionRequest;
import bookingengine.adapters.web.dto.InscriptionRequest;
import bookingengine.domain.entities.Utilisateur;
import bookingengine.usecase.auth.AuthUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("auth")
@Tag(name = "Authentification", description = "Gestion de l'authentification")
public class AuthController {

    private final AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @PostMapping("inscription")
    @Operation(
            summary = "Inscrire un nouvel utilisateur",
            description = "Crée un nouveau compte utilisateur avec un nom d'utilisateur, mot de passe et email uniques"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou utilisateur/email déjà existant", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content)
    })
    public ResponseEntity<Map<String, String>> inscrire(@RequestBody InscriptionRequest request) {
        Utilisateur utilisateur = authUseCase.inscrire(
                request.username(),
                request.password(),
                request.email()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "Utilisateur créé avec succès",
                        "username", utilisateur.getUsername()
                ));
    }

    @PostMapping("connexion")
    @Operation(
            summary = "Connecter un utilisateur",
            description = "Vérifie les identifiants et connecte l'utilisateur"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Connexion réussie"),
            @ApiResponse(responseCode = "401", description = "Identifiants invalides", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content)
    })
    public ResponseEntity<Map<String, Object>> connexion(@RequestBody ConnexionRequest request) {
        boolean valid = authUseCase.verifierMotDePasse(request.username(), request.password());
        if (!valid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Identifiants invalides"));
        }
        Utilisateur utilisateur = authUseCase.obtenirUtilisateur(request.username());
        return ResponseEntity.ok(Map.of(
                "message", "Connexion réussie",
                "username", utilisateur.getUsername(),
                "email", utilisateur.getEmail(),
                "role", utilisateur.getRole()
        ));
    }
}
