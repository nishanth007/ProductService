package com.scaler.ProductService.configurations.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RemoteUserDetailsService implements UserDetailsService {

    @Autowired
    private RestTemplate restTemplate;

    private final String authenticationServiceUrl = "http://userauthservice/api/user"; // URL of the authentication service


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Make a request to the remote authentication service to get the user details
        try {
            // Make a request to the remote authentication service to get the user details
            ResponseEntity<UserDetailsResponse> response = restTemplate.exchange(
                    authenticationServiceUrl + "?username=" + username,
                    HttpMethod.GET,
                    null,
                    UserDetailsResponse.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                UserDetailsResponse userDetailsResponse = response.getBody();

                // Convert the UserDetailsResponse to a Spring Security UserDetails object
                List<GrantedAuthority> authorities = userDetailsResponse.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());

                User.UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withUsername(username)
                        .password(userDetailsResponse.getPassword())
                        .authorities(authorities);

                return userBuilder.build();
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("Error fetching user details", e);
        }
    }
}
