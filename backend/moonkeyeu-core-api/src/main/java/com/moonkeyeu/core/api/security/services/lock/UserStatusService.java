package com.moonkeyeu.core.api.security.services.lock;

import com.moonkeyeu.core.api.user.model.User;

public interface UserStatusService {
    void disableAccount(User user);
    boolean isAccountLocked(User savedUser);
    void lockAccount(User user, boolean hasGradualLock);
    void resetAccount(User user);
    void unlockAccount(User user);
    void attemptsIncrement(User user);
    String getLockExpiration(User user);
}
