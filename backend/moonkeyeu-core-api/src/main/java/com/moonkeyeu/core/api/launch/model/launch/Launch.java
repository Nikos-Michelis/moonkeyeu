package com.moonkeyeu.core.api.launch.model.launch;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.moonkeyeu.core.api.launch.model.agency.Agencies;
import com.moonkeyeu.core.api.launch.model.info.InfoUrlEntity;
import com.moonkeyeu.core.api.launch.model.info.VideoUrl;
import com.moonkeyeu.core.api.launch.model.pad.LaunchPad;
import com.moonkeyeu.core.api.launch.model.rocket.Rocket;
import com.moonkeyeu.core.api.launch.model.astronaut.CrewMember;
import com.moonkeyeu.core.api.launch.model.mission.Mission;
import com.moonkeyeu.core.api.launch.model.mission.MissionPatches;
import com.moonkeyeu.core.api.launch.model.program.Programs;
import com.moonkeyeu.core.api.user.model.Bookmark;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@NamedEntityGraph(
        name = "launch-with-status-agency-images",
        attributeNodes = {
                @NamedAttributeNode(value = "launchStatus", subgraph = "launchStatus"),
                @NamedAttributeNode(value = "agencies", subgraph = "agencies"),
                @NamedAttributeNode(value = "rocket", subgraph = "rocket"),
                @NamedAttributeNode(value = "launchPad", subgraph = "launchPad"),
                @NamedAttributeNode(value = "missionPatches"),
                @NamedAttributeNode(value = "programs"),
                @NamedAttributeNode(value = "videoUrls"),
                @NamedAttributeNode(value = "infoUrls"),
                @NamedAttributeNode(value = "bookmarks", subgraph = "bookmarks")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "launchStatus",
                        attributeNodes = {
                                @NamedAttributeNode("statusName"),
                                @NamedAttributeNode("abbrev")
                        }
                ),
                @NamedSubgraph(
                        name = "agencies",
                        attributeNodes = {
                                @NamedAttributeNode("agencyType"),
                        }
                ),
                @NamedSubgraph(
                        name = "rocket",
                        attributeNodes = {
                                @NamedAttributeNode(value = "rocketConfiguration", subgraph = "rocketConfiguration")
                        }
                ),
                @NamedSubgraph(
                        name = "rocketConfiguration",
                        attributeNodes = {
                                @NamedAttributeNode(value = "rocketConfImages", subgraph = "rocketConfImages"),
                                //@NamedAttributeNode(value = "agencies", subgraph = "agencies")
                        }
                ),
                @NamedSubgraph(
                        name = "rocketConfImages",
                        attributeNodes = {

                        }
                ),
                @NamedSubgraph(
                        name = "launchPad",
                        attributeNodes = {
                                @NamedAttributeNode(value = "location", subgraph = "location"),
                               // @NamedAttributeNode(value = "agencies", subgraph = "agencies")
                        }
                ),
                @NamedSubgraph(
                        name = "location",
                        attributeNodes = {
                                @NamedAttributeNode("locationName")
                        }
                ),
                @NamedSubgraph(
                        name = "programs",
                        attributeNodes = {}
                ),
                @NamedSubgraph(
                        name = "bookmarks",
                        attributeNodes = {}
                )

        }
)
@Table(name = "launch", schema = "moonkey_db")
@EqualsAndHashCode(of = "launchId")
public class Launch {
    @Id
    @Column(name = "launch_id")
    private String launchId;
    @Basic
    @Column(name = "slug")
    private String slug;
    @Basic
    @Column(name = "flightclub_url")
    private String flightclubUrl;
    @Basic
    @Column(name = "name")
    private String launchName;
    @Basic
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX", timezone = "UTC")
    @Column(name = "last_updated")
    private Instant lastUpdated;
    @Basic
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX", timezone = "UTC")
    @Column(name = "net")
    private Instant net;
    @Basic
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX", timezone = "UTC")
    @Column(name = "window_start")
    private Instant windowStart;
    @Basic
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX", timezone = "UTC")
    @Column(name = "window_end")
    private Instant windowEnd;
    @Basic
    @Column(name = "probability")
    private Integer probability;
    @Basic
    @Column(name = "weather_concerns")
    private String weatherConcerns;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id")
    private Agencies agencies;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rocket_id")
    private Rocket rocket;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;
    @ManyToOne
    @JoinColumn(name = "launch_pad_id")
    private LaunchPad launchPad;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private LaunchStatus launchStatus;
    @OneToMany(mappedBy = "launch")
    @BatchSize(size = 20)
    private Set<MissionPatches> missionPatches;
    @OneToMany(mappedBy = "launch")
    @BatchSize(size = 20)
    private Set<VideoUrl> videoUrls;
    @OneToMany(mappedBy = "launch")
    @BatchSize(size = 20)
    private Set<InfoUrlEntity> infoUrls;
    @OneToMany(mappedBy = "launch")
    @BatchSize(size = 20)
    private Set<CrewMember> crewMembers;
    @ManyToMany
    @JoinTable(
            name = "launch_has_programs",
            joinColumns = @JoinColumn(name = "launch_id"),
            inverseJoinColumns = @JoinColumn(name = "program_id")
    )
    @BatchSize(size = 20)
    private Set<Programs> programs;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "bookmark_list",
            joinColumns = @JoinColumn(name = "launch_id"),
            inverseJoinColumns = @JoinColumn(name = "bookmark_id")
    )
    @BatchSize(size = 20)
    private Set<Bookmark> bookmarks = new HashSet<>();

}
