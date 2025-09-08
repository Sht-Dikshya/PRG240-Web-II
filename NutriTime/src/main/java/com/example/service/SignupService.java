package com.example.service;

import com.example.model.Signup;

public interface SignupService {

    /**
     * Register a new user
     * @param user the login to register
     * @return the registered login
     */
    Signup registerSignup(Signup  user);

    /**
     * Get login by ID (for future use)
     * @param id the login ID
     * @return the login if found, null otherwise
     */
    Signup getSignupById(Long id);
}
