package com.ensaf.haron.belghit.service;

import com.ensaf.haron.belghit.exceptions.CustomException;
import com.ensaf.haron.belghit.repository.IWishRepository;
import com.ensaf.haron.belghit.repository.entity.User;
import com.ensaf.haron.belghit.repository.entity.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishListService {

    @Autowired
    IWishRepository wishRepository;

    public void saveWishList(Wish wishList) {
        wishRepository.save(wishList);
    }

    public List<Wish> readWishList(Long userId) {
        return wishRepository.findAllByUserIdOrderByCreatedDateDesc(userId);
    }

    public void deleteWish(Integer wishId, User user) {
    Optional<Wish> optionalWish = wishRepository.findById(wishId);
        if(optionalWish.isEmpty()){
            throw new CustomException("Wish is invalid => " + wishId);
        }
        Wish wish = optionalWish.get();
        if(wish.getUser()!= user){
            throw new CustomException("Wish does not belong to user: " + wishId);
        }
        wishRepository.delete(wish);
    }

}
