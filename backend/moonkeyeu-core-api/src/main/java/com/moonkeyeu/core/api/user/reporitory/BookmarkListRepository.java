package com.moonkeyeu.core.api.user.reporitory;

import com.moonkeyeu.core.api.user.model.BookmarkList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkListRepository extends JpaRepository<BookmarkList, Long> {
    @Modifying
    @Query("DELETE FROM BookmarkList bl WHERE bl.bookmark.bookmarkId = :bookmarkId AND bl.launch.launchId = :launchId")
    void deleteByBookmarkIdAndLaunchId(Long bookmarkId, String launchId);




}
