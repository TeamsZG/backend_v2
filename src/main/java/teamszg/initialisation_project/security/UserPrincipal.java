package teamszg.initialisation_project.security;

import java.io.Serializable;

public class UserPrincipal implements Serializable{
    private final Long id;
    private final String email;
    public UserPrincipal(Long id, String email) { this.id = id; this.email = email; }
    public Long getId() { return id; }
    public String getEmail() { return email; }
}
