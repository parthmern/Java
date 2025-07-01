package org.example.uberauthservice.helper;

import org.example.uberauthservice.models.Passenger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

// why this class is needed ?
// spring security works on UserDetails polymorphic type for auth
public class AuthPassengerDetails extends Passenger implements UserDetails {

    private String username;

    private String password;

    public AuthPassengerDetails(Passenger passenger){
        this.username = passenger.getEmail();   // unique
        this.password = passenger.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    // below methods are not much imp due to business logic of ours

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
