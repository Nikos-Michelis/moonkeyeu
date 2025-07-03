package com.moonkeyeu.core.api.user.reporitory;

import com.moonkeyeu.core.api.user.model.Bookmark;
import com.moonkeyeu.core.api.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query("""
        SELECT b
        FROM Bookmark b
        LEFT JOIN FETCH b.user u
        INNER JOIN FETCH u.roles r
        INNER JOIN FETCH u.tokens t
        LEFT JOIN FETCH r.permissions p
        LEFT JOIN FETCH b.launches l
        LEFT JOIN FETCH l.launchStatus
        LEFT JOIN FETCH l.launchPad lp
        LEFT JOIN FETCH lp.location
        LEFT JOIN FETCH l.rocket rt
        LEFT JOIN FETCH rt.rocketConfiguration rc
        LEFT JOIN FETCH rc.agencies
        LEFT JOIN FETCH rc.rocketConfImages
        WHERE b.user = :user
        ORDER BY b.bookmarkName ASC
    """)
    List<Bookmark> findBookmarkByUser(@Param("user") User user);

    @Query("""
        SELECT b
        FROM Bookmark b
        LEFT JOIN FETCH b.user u
        INNER JOIN FETCH u.roles r
        INNER JOIN FETCH u.tokens
        LEFT JOIN FETCH r.permissions p
        WHERE b.bookmarkName = :bookmarkName AND b.user = :user
    """)
    Optional<Bookmark> findBookmarkByBookmarkNameAndUser(@Param("bookmarkName") String bookmarkName, @Param("user") User user);
    @Query("SELECT COUNT(b) FROM Bookmark b WHERE b.user = :user")
    long countByUser(@Param("user") User user);
}
