package TEAM5.Roomie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false, length = 25)
    private String userId;

    @Column(nullable = false, length = 25)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

//    @Column(name = "meet_place", nullable = false, length = 255)
//    private String meetPlace;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "map_id", referencedColumnName = "id")
    private Map map;


    @Column(name = "meet_time", nullable = false)
    private LocalDateTime meetTime;

    @Column(name = "max_count", nullable = false)
    private Integer maxCount;

    @Column(name = "user_count", nullable = false)
    private Integer userCount = 0;

    @Column(nullable = false, length = 20)
    private String tag;

    @Lob
    @Column
    private byte[] image;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants;
}

