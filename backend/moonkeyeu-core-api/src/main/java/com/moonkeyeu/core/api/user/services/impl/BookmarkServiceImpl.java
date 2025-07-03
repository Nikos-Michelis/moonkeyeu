package com.moonkeyeu.core.api.user.services.impl;

import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.launch.LaunchNormalDTO;
import com.moonkeyeu.core.api.launch.dto.launch.LaunchSummarizedDTO;
import com.moonkeyeu.core.api.launch.dto.paging.PageSortingDTO;
import com.moonkeyeu.core.api.launch.model.launch.Launch;
import com.moonkeyeu.core.api.launch.repository.LaunchRepository;
import com.moonkeyeu.core.api.launch.repository.specifications.MyLaunchesSpecification;
import com.moonkeyeu.core.api.settings.exceptions.ConflictException;
import com.moonkeyeu.core.api.user.dto.BookmarkDTO;
import com.moonkeyeu.core.api.user.dto.request.RequestBookmark;
import com.moonkeyeu.core.api.user.dto.request.RequestCreateBookmark;
import com.moonkeyeu.core.api.user.dto.request.RequestEditBookmark;
import com.moonkeyeu.core.api.user.model.Bookmark;
import com.moonkeyeu.core.api.user.model.User;
import com.moonkeyeu.core.api.user.reporitory.BookmarkListRepository;
import com.moonkeyeu.core.api.user.reporitory.BookmarkRepository;
import com.moonkeyeu.core.api.user.services.BookmarkService;
import com.moonkeyeu.core.api.configuration.utils.DtoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookmarkServiceImpl implements BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final LaunchRepository launchRepository;
    private final DtoConverter dtoConverter;
    private final BookmarkListRepository bookmarkListRepository;
    @Override
    public List<BookmarkDTO> getUserBookmarks(User user) {
        List<Bookmark> bookmarks = bookmarkRepository.findBookmarkByUser(user);
        return bookmarks
                .stream()
                .map(bookmark -> dtoConverter.convertToDto(bookmark, BookmarkDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<DTOEntity> searchLaunchesByUserAndBookmarkName(User user, String name, Map<String, String> requestParams, PageSortingDTO pageSortingDTO) {
        Specification<Launch> spec = Specification.where(null);
        spec = spec.and(MyLaunchesSpecification.rootSpecification());
        spec = spec.and(MyLaunchesSpecification.hasUserIdAndBookmarkName(user.getUserId(), name));
        if (requestParams != null && !requestParams.isEmpty()) {
            if (requestParams.containsKey("search")) {
                String searchKey = requestParams.get("search");
                spec = spec.and(MyLaunchesSpecification.hasSearchKey(searchKey));
            }
        }
        Sort sortObject = "desc".equalsIgnoreCase(pageSortingDTO.getSort())
                ? Sort.by(pageSortingDTO.getField()).descending()
                : Sort.by(pageSortingDTO.getField()).ascending();
        int page = (pageSortingDTO.getPage() > 0) ? pageSortingDTO.getPage() - 1 : 0;
        Pageable pageable = PageRequest.of(page, pageSortingDTO.getLimit(), sortObject);
        Page<Launch> launches = launchRepository.findAll(spec, pageable);
        return launches.map(launch -> dtoConverter.convertToDto(launch, LaunchNormalDTO.class));
    }

    @Transactional
    @Override
    public BookmarkDTO createBookmark(User user, RequestCreateBookmark requestCreateBookmark) {
        if (bookmarkRepository.countByUser(user) >= 20) {
            throw new ConflictException("You can only have up to 20 bookmarks.");
        }

        if (isBookmarkPresent(requestCreateBookmark.getBookmarkName(), user)) {
            throw new ConflictException("Bookmark already exists, use a different name.");
        }

        Bookmark newBookmark = bookmarkRepository.save(
                Bookmark.builder()
                        .user(user)
                        .bookmarkName(requestCreateBookmark.getBookmarkName())
                        .build());
        return dtoConverter.convertToDto(newBookmark, BookmarkDTO.class);
    }
    @Transactional
    @Override
    public BookmarkDTO editBookmark(User user, RequestEditBookmark requestEditBookmark) {
        Bookmark bookmark = bookmarkRepository.findBookmarkByBookmarkNameAndUser(requestEditBookmark.getCurrentName(), user)
                .orElseThrow(() -> new ResourceNotFoundException("Bookmark with name '"
                                + requestEditBookmark.getCurrentName() + "' not found for user '" + user.getUsername() + "'"));

        if (isBookmarkPresent(requestEditBookmark.getNewName(), user)) {
            throw new ConflictException("Bookmark already exists, use a different name.");
        }

        bookmark.setBookmarkName(requestEditBookmark.getNewName());
        Bookmark updatedBookmark = bookmarkRepository.save(bookmark);
        return dtoConverter.convertToDto(updatedBookmark, BookmarkDTO.class);
    }
    @Transactional
    @Override
    public BookmarkDTO removeBookmark(User user, String bookmarkName) {
        Bookmark bookmark = bookmarkRepository.findBookmarkByBookmarkNameAndUser(bookmarkName, user)
                .orElseThrow(() -> new ResourceNotFoundException("Bookmark Name Not Found."));
        bookmark.deleteBookmark(bookmark);
        bookmarkRepository.delete(bookmark);
        return dtoConverter.convertToDto(bookmark, BookmarkDTO.class);
    }

    @Transactional
    @Override
    public void addToBookmarkList(User user, RequestBookmark requestBookmark) {
        Bookmark bookmark = bookmarkRepository.findBookmarkByBookmarkNameAndUser(requestBookmark.getBookmarkName(), user)
                .orElseThrow(() -> new ResourceNotFoundException("Bookmark not found."));
        Launch launch = launchRepository.findLaunchWithLaunchId(requestBookmark.getLaunchId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Not found launch with id: " + requestBookmark.getLaunchId()
                ));

        if (bookmark.getLaunches().size() >= 20) {
            throw new ConflictException("This bookmark already contains the maximum of 20 launches.");
        }

        if (!bookmark.getLaunches().contains(launch)) {
            bookmark.addLaunch(launch);
            bookmarkRepository.save(bookmark);
        } else {
            throw new ConflictException("This launch has already been added to your list.");

        }
    }
    @Transactional
    @Override
    public Map<String, Object> removeFromBookmark(User user, String bookMarkName, String launchId) {
        Bookmark bookmark = bookmarkRepository.findBookmarkByBookmarkNameAndUser(bookMarkName, user)
                .orElseThrow(() -> new ResourceNotFoundException("Bookmark Name Not Found."));

        if (bookmark.getLaunches().size() >= 100) {
            throw new ConflictException("This bookmark already contains the maximum of 100 launches.");
        }

        Launch launch = launchRepository.findLaunchWithBookmarkIdAndLaunchId(bookmark.getBookmarkId(), launchId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found launch with id: " + launchId));

        if (!bookmark.getLaunches().contains(launch)) {
            throw new ResourceNotFoundException("Launch is not part of the specified bookmark.");
        }

        bookmarkListRepository.deleteByBookmarkIdAndLaunchId(bookmark.getBookmarkId(), launch.getLaunchId());
        log.debug("Removed launch with ID {} from bookmark {}", launchId, bookMarkName);
        LaunchSummarizedDTO launchSummarizedDTO = dtoConverter.convertToDto(launch, LaunchSummarizedDTO.class);

        Map<String, Object> map = new HashMap<>();
        map.put("bookmark", bookmark.getBookmarkName());
        map.put("removed", launchSummarizedDTO);
        return map;
    }

    private boolean isBookmarkPresent(String bookmarkName, User user){
        return bookmarkRepository
                .findBookmarkByBookmarkNameAndUser(bookmarkName, user)
                .isPresent();
    }
}
