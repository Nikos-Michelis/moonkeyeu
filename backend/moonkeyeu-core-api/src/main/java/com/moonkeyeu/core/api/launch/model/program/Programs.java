package com.moonkeyeu.core.api.launch.model.program;

import com.moonkeyeu.core.api.launch.model.agency.Agencies;
import com.moonkeyeu.core.api.launch.model.launch.Launch;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedEntityGraph(
        name = "programs-agencies-launches",
        attributeNodes = {
                @NamedAttributeNode(value = "agencies", subgraph = "agencies"),
                @NamedAttributeNode(value = "type", subgraph = "type"),
                @NamedAttributeNode(value = "programImages", subgraph = "programImages"),
                @NamedAttributeNode(value = "launches", subgraph = "launches"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "agencies",
                        attributeNodes = {}
                ),
                @NamedSubgraph(
                        name = "type",
                        attributeNodes = {}
                ),
                 @NamedSubgraph(
                        name = "programImages",
                        attributeNodes = {}
                ),
                @NamedSubgraph(
                        name = "launches",
                        attributeNodes = {}
                ),

        }
)

@Table(name = "programs", schema = "moonkey_db")
public class Programs {
    @Id
    @Column(name = "program_id")
    private Long programId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "info_url")
    private String infoUrl;
    @Basic
    @Column(name = "wiki_url")
    private String wikiUrl;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "start_date")
    private LocalDate startDate;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private ProgramType type;
    @OneToMany(mappedBy = "program")
    @BatchSize(size = 20)
    private Set<ProgramImages> programImages;
    @ManyToMany
    @JoinTable(
            name = "programs_has_agencies",
            joinColumns = @JoinColumn(name = "program_id"),
            inverseJoinColumns = @JoinColumn(name = "agency_id")
    )
    @BatchSize(size = 20)
    private Set<Agencies> agencies;
    @ManyToMany(mappedBy = "programs")
    @BatchSize(size = 20)
    private Set<Launch> launches;


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Programs that = (Programs) object;
        return Objects.equals(programId, that.programId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(programId);
    }
}
