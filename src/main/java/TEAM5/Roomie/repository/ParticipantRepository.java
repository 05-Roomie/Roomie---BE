package TEAM5.Roomie.repository;

import TEAM5.Roomie.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    List<Participant> findByPostId(Integer postId);
    Participant findByPostIdAndUserId(Integer postId, String userId);
}
