package teamszg.initialisation_project.security;

import java.io.Serializable;

/**
 * Représente un utilisateur authentifié dans le contexte de sécurité.
 * <p>
 * Cette classe est utilisée pour stocker les informations essentielles d'un utilisateur
 * une fois qu'il est authentifié via JWT, et est placée dans le contexte de sécurité Spring.
 * </p>
 */

public class UserPrincipal implements Serializable{
    private final Long id;
    private final String email;
    public UserPrincipal(Long id, String email) { this.id = id; this.email = email; }
    public Long getId() { return id; }
    public String getEmail() { return email; }
}
