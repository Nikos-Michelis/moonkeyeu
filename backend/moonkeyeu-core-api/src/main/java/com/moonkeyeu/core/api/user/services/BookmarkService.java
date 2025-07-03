package com.moonkeyeu.core.api.user.services;

import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.launch.LaunchSummarizedDTO;
import com.moonkeyeu.core.api.launch.dto.paging.PageSortingDTO;
import com.moonkeyeu.core.api.launch.repository.specifications.MyLaunchesSpecification;
import com.moonkeyeu.core.api.user.dto.BookmarkDTO;
import com.moonkeyeu.core.api.user.model.User;
import com.moonkeyeu.core.api.user.dto.request.RequestBookmark;
import com.moonkeyeu.core.api.user.dto.request.RequestCreateBookmark;
import com.moonkeyeu.core.api.user.dto.request.RequestEditBookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

public interface BookmarkService {
    List<BookmarkDTO> getUserBookmarks(User user);
    Page<DTOEntity> searchLaunchesByUserAndBookmarkName(
            User user,
            String name,
            Map<String, String> requestParams,
            PageSortingDTO pageSortingDTO
    );
    BookmarkDTO createBookmark(User user, RequestCreateBookmark requestCreateBookmark);
    void addToBookmarkList(User user, RequestBookmark requestBookmark);
    BookmarkDTO removeBookmark(User user, String bookmarkName);
    BookmarkDTO editBookmark(User user, RequestEditBookmark requestEditBookmark);
    Map<String, Object> removeFromBookmark(User user, String bookmarkName, String launchId);
}
