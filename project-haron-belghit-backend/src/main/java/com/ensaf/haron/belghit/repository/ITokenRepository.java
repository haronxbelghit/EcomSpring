package com.ensaf.haron.belghit.repository;

import com.ensaf.haron.belghit.repository.entity.AuthenticationToken;
import com.ensaf.haron.belghit.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITokenRepository extends JpaRepository<AuthenticationToken, Long> {
    AuthenticationToken findTokenByUser(User user);
    AuthenticationToken findTokenByToken(String token);
}
