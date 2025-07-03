package com.moonkeyeu.core.api.user.model;

import com.moonkeyeu.core.api.launch.model.launch.Launch;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(
        name = "bookmark",
        uniqueConstraints = {
             @UniqueConstraint(columnNames = {"bookmark_name", "user_id"})
        }
)
@EqualsAndHashCode(of = "bookmarkId")
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long bookmarkId;
    @Column(name = "bookmark_name", unique = true)
    private String bookmarkName;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(mappedBy = "bookmarks", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @BatchSize(size = 20)
    private Set<Launch> launches = new HashSet<>();
    @CreatedDate
    @Column(name = "created_at", nullable = false, unique = false)
    private Instant createdAt;
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, unique = false)
    private Instant updatedAt;

    public void addLaunch(Launch launch) {
        this.launches.add(launch);
        launch.getBookmarks().add(this);
    }
    public void deleteBookmark(Bookmark bookmark) {
        bookmark.getLaunches().removeIf(launch -> launch.getBookmarks().remove(bookmark));
        this.launches.clear();
    }
    public Set<Launch> getLaunches() {
        if (launches == null) {
            setLaunches(new HashSet<>());
        }
        return launches;
    }
}
