package com.korea.triplocation.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.korea.triplocation.domain.review.entity.Review;
import com.korea.triplocation.domain.review.entity.ReviewImg;

@Mapper
public interface ReviewRepository {
	
	public List<Review> getReviewListByUserId(int userId);
    public List<Review> getReviewListByRating();
    
    public int registerReviews(Review reviews);
    public int registerReviewImgs(List<ReviewImg> reviewImgs);
}
