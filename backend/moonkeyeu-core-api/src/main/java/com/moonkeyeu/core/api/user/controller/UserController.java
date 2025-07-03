package com.moonkeyeu.core.api.user.controller;

import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.paging.PageSortingDTO;
import com.moonkeyeu.core.api.security.services.cookie.CookieServiceProvider;
import com.moonkeyeu.core.api.user.dto.BookmarkDTO;
import com.moonkeyeu.core.api.user.dto.UserDTO;
import com.moonkeyeu.core.api.security.dto.request.ChangePasswordRequest;
import com.moonkeyeu.core.api.security.limiter.RateLimited;
import com.moonkeyeu.core.api.security.services.AuthenticationService;
import com.moonkeyeu.core.api.user.dto.request.RequestBookmark;
import com.moonkeyeu.core.api.user.dto.request.RequestCreateBookmark;
import com.moonkeyeu.core.api.user.dto.request.RequestEditBookmark;
import com.moonkeyeu.core.api.user.dto.response.ResponseDTO;
import com.moonkeyeu.core.api.user.model.User;
import com.moonkeyeu.core.api.user.services.BookmarkService;
import com.moonkeyeu.core.api.user.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Tag(name = "user")
@Slf4j
public class UserController {

    @Value("${application.backend.url}")
    private String backendUrl;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final BookmarkService bookmarkService;
    private final CookieServiceProvider cookieServiceProvider;
    private final int MAX_ITEMS = 50;

    @PostMapping("/change-password")
    @RateLimited(requests = 5, durationSeconds = 60)
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal User user,
                                            @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(changePasswordRequest, user);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Password successfully changed.");
        return ResponseEntity.ok().body(map);
    }

    @DeleteMapping("/account/deactivate")
    @RateLimited(requests = 2, durationSeconds = 60)
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal User user){
        authenticationService.deleteAccount(user);
        ResponseCookie refreshTokenCookie = cookieServiceProvider.clearRefreshTokenCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(ResponseDTO.builder()
                        .timestamp(Instant.now())
                        .message("Your account has been disabled and will be permanently deleted after 30 days.")
                        .build());
    }

    @GetMapping("/my-account")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<UserDTO> myAccount(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(userService.getAuthUserDetails(user));
    }

    @PostMapping("/bookmark/create")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<?> createBookmark(@AuthenticationPrincipal User user,
                                           @Valid @RequestBody RequestCreateBookmark requestCreateBookmark) {
        BookmarkDTO bookmarkDTO = bookmarkService.createBookmark(user, requestCreateBookmark);
        return ResponseEntity.created(URI.create(backendUrl + "user-bookmarks"))
                .body(ResponseDTO.builder()
                        .timestamp(Instant.now())
                        .message("Bookmark created successfully.")
                        .data(bookmarkDTO)
                        .build());
    }

    @PutMapping("/bookmark/update")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<?> editBookmark(@AuthenticationPrincipal User user,
                                          @Valid @RequestBody RequestEditBookmark requestEditBookmark) {
        BookmarkDTO bookmarkDTO = bookmarkService.editBookmark(user, requestEditBookmark);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .timestamp(Instant.now())
                        .message("Bookmark updated successfully.")
                        .data(bookmarkDTO)
                        .build());
    }

    @PostMapping("/bookmark/add")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<?> addToBookmarkList(@AuthenticationPrincipal User user, @RequestBody RequestBookmark requestBookmark) {
        log.info("user --> " + user);
        bookmarkService.addToBookmarkList(user, requestBookmark);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .timestamp(Instant.now())
                        .message("Item added to your list successfully.")
                        .build());
    }

    @DeleteMapping("/bookmark/delete/{name}")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<?> removeBookmark(
            @AuthenticationPrincipal User user,
            @PathVariable String name
    ) {
        BookmarkDTO bookmarkDTO = bookmarkService.removeBookmark(user, name);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .timestamp(Instant.now())
                        .message("Bookmark deleted successfully")
                        .data(bookmarkDTO)
                        .build());
    }

    @DeleteMapping("/bookmark/delete/{name}/{launchId}")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<?> removeLaunchFromBookmark(
            @AuthenticationPrincipal User user,
            @Valid @PathVariable
            @NotEmpty(message = "Bookmark name should not be empty.")
            @NotNull(message = "Bookmark name is required.") String name,
            @Valid @PathVariable
            @NotEmpty(message = "Launch identifier not be empty.")
            @NotNull(message = "Launch identifier is required") String launchId
    ) {
        Map<String, Object> map = bookmarkService.removeFromBookmark(user, name, launchId);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .timestamp(Instant.now())
                        .message("You have successfully delete item from your list.")
                        .data(map)
                        .build());
    }

    @GetMapping("/user-bookmarks")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<?> getAllBookmarks(@AuthenticationPrincipal User userDetails) {
        return ResponseEntity.ok(bookmarkService.getUserBookmarks(userDetails));
    }

    @GetMapping("/user-bookmarks/{name}")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<?> getBookmarkedLaunches(
            @AuthenticationPrincipal User user,
            @Valid @PathVariable @NotEmpty(message = "Bookmark name should not be empty.")
            @NotNull(message = "Bookmark name is required.") String name,
            @RequestParam(required = false) Map<String, String> requestParams,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "12") Integer limit,
            @RequestParam(defaultValue = "net") String field,
            @RequestParam(defaultValue = "asc") String ordering,
            PagedResourcesAssembler<DTOEntity> assembler
    ) {
        return ResponseEntity
                .ok(assembler.toModel(
                        bookmarkService.searchLaunchesByUserAndBookmarkName(user, name, requestParams,
                                PageSortingDTO.builder()
                                        .page(page)
                                        .limit(limit >= MAX_ITEMS ? MAX_ITEMS : limit)
                                        .field(field)
                                        .sort(ordering)
                                        .build())));
    }
}
