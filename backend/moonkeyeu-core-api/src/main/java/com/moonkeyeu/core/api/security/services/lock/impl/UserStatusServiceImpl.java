package com.moonkeyeu.core.api.security.services.lock.impl;


import com.moonkeyeu.core.api.security.services.lock.UserStatusService;
import com.moonkeyeu.core.api.user.model.User;
import com.moonkeyeu.core.api.user.reporitory.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStatusServiceImpl implements UserStatusService {

    private final UserRepository userRepository;

    private static final long MAX_GRADUAL_LOCK_EXPIRATION = 60 * 60 * 1000; // 1 hour
    private static final long MIN_LOCK_EXPIRATION = 10 * 60 * 1000; // 10 minutes
    private static final long MAX_LOCK_EXPIRATION = 60 * 60 * 1000; // 1 hour in milliseconds

    public boolean isAccountLocked(User user) {
        return user.isAccountLocked() && Instant.now().isBefore(user.getLockExpiresAt());
    }
    @Override
    public void disableAccount(User user) {
        user.setLockedAt(Instant.now());
        user.setEnable(false);
        user.setAccountLocked(true);
        updateLock(user);
    }
    @Override
    public void lockAccount(User user, boolean isEnableGradualLock) {
        user.setLockedAt(Instant.now());
        user.setAccountLocked(true);
        user.setBlocks(user.getBlocks() != null ? user.getBlocks() + 1 : 1);
        user.setLockExpiresAt(isEnableGradualLock ? calculateGradualLockAccountExpiration(user) : calculateStaticLockAccountExpiration(user));
        updateLock(user);
    }
    @Override
    public void resetAccount(User user) {
        user.setAccountLocked(false);
        user.setAttempts(0);
        updateLock(user);
    }
    @Override
    public void unlockAccount(User user) {
        user.setAccountLocked(false);
        user.setAttempts(0);
        user.setBlocks(0);
        //log.info(String.format("user after lock change: " + user));
        updateLock(user);
    }
    @Override
    public void attemptsIncrement(User user){
        user.setAttempts(user.getAttempts() != null? user.getAttempts() + 1 : 1);
        updateLock(user);
    }
    @Override
    public String getLockExpiration(User user) {
        Duration duration = Duration.between(Instant.now(), user.getLockExpiresAt());
        long totalSeconds = Math.max(duration.getSeconds(), 0);
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        if (hours > 0) {
            return hours + " hours, " + minutes + " minutes and " + seconds + " seconds";
        } else if (minutes > 0) {
            return minutes + " minutes and " + seconds + " seconds";
        } else {
            return seconds + " seconds";
        }
    }


    private Instant calculateStaticLockAccountExpiration(User user) {
        return (user.getBlocks() != null && user.getBlocks() > 1)
                ? Instant.now().plusSeconds(MAX_LOCK_EXPIRATION / 1000)
                : Instant.now().plusSeconds(MIN_LOCK_EXPIRATION / 1000);
    }
    private Instant calculateGradualLockAccountExpiration(User user) {
        int blocks = user.getBlocks() != null ? user.getBlocks() : 0;
        long gradualLockDuration = blocks * MIN_LOCK_EXPIRATION;
        long lockDuration = Math.min(gradualLockDuration, MAX_GRADUAL_LOCK_EXPIRATION);
        return Instant.now().plusSeconds(lockDuration / 1000);
    }
    private void updateLock(User user) {
        userRepository.save(user);
    }

}
