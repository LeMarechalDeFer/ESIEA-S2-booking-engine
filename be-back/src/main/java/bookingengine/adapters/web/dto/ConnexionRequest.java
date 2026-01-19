package bookingengine.adapters.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Requête de connexion d'un utilisateur")
public record ConnexionRequest(
        @Schema(description = "Nom d'utilisateur", example = "jean.dupont", required = true)
        String username,

        @Schema(description = "Mot de passe", example = "MonMotDePasse123!", required = true)
        String password
) {}
