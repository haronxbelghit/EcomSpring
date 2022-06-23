package com.ensaf.haron.belghit.service;

import com.ensaf.haron.belghit.config.AuthenticationMessage;
import com.ensaf.haron.belghit.utils.Helper;
import com.ensaf.haron.belghit.exceptions.AuthenticationFailException;
import com.ensaf.haron.belghit.repository.ITokenRepository;
import com.ensaf.haron.belghit.repository.entity.AuthenticationToken;
import com.ensaf.haron.belghit.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    ITokenRepository tokenRepository;


    public void authenticate(String token) throws AuthenticationFailException {
        if (!Helper.notNull(token)) {
            throw new AuthenticationFailException(AuthenticationMessage.AUTH_TOEKN_NOT_PRESENT);
        }
        if (!Helper.notNull(getUser(token))) {
            throw new AuthenticationFailException(AuthenticationMessage.AUTH_TOEKN_NOT_VALID);
        }
    }

    public User getUser(String token) {
        AuthenticationToken authenticationToken = tokenRepository.findTokenByToken(token);
        if (Helper.notNull(authenticationToken)) {
            if (Helper.notNull(authenticationToken.getUser())) {
                return authenticationToken.getUser();
            }
        }
        return null;
    }

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return  tokenRepository.findTokenByUser(user);
    }

}