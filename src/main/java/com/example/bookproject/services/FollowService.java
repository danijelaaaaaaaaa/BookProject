package com.example.bookproject.services;

import com.example.bookproject.models.User;
import com.example.bookproject.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FollowService {

    @Autowired
    private UserRepository userRepository;

    public void followUser(Long followerId, Long userToFollowId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("Follower not found"));
        User userToFollow = userRepository.findById(userToFollowId)
                .orElseThrow(() -> new IllegalArgumentException("User to follow not found"));

        if (followerId.equals(userToFollowId)) {
            throw new IllegalArgumentException("Cannot follow yourself");
        }

        follower.getFollowing().add(userToFollow);
        userRepository.save(follower);
    }

    public void unfollowUser(Long followerId, Long userToUnfollowId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("Follower not found"));
        User userToUnfollow = userRepository.findById(userToUnfollowId)
                .orElseThrow(() -> new IllegalArgumentException("User to unfollow not found"));

        follower.getFollowing().remove(userToUnfollow);
        userRepository.save(follower);
    }

    public boolean isFollowing(Long followerId, Long followedUserId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return follower.getFollowing().stream()
                .anyMatch(user -> user.getId().equals(followedUserId));
    }
}