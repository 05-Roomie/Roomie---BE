package TEAM5.Roomie.service;

import TEAM5.Roomie.Exception.InvalidPostException;
import TEAM5.Roomie.Exception.ParticipantException;
import TEAM5.Roomie.dto.MapDto;
import TEAM5.Roomie.dto.ParticipantDto;
import TEAM5.Roomie.dto.PostDto;
import TEAM5.Roomie.entity.Map;
import TEAM5.Roomie.entity.Participant;
import TEAM5.Roomie.entity.Post;
import TEAM5.Roomie.repository.MapRepository;
import TEAM5.Roomie.repository.ParticipantRepository;
import TEAM5.Roomie.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ParticipantRepository participantRepository;
    private final MapRepository mapRepository;

    public List<PostDto> getAllPosts(int page, int size) {
        Page<Post> postsPage = postRepository.findAll(PageRequest.of(page, size));
        return postsPage.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<PostDto> getPostsByTag(String tag, int page, int size) {
        Page<Post> postsPage = postRepository.findByTag(tag, PageRequest.of(page, size));
        return postsPage.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public PostDto createPost(PostDto postDto, MapDto mapDto) {
        if (postDto.getUserId() == null || postDto.getUserId().isEmpty()) {
            throw new InvalidPostException("User ID is required");
        }
        if (postDto.getTitle() == null || postDto.getTitle().isEmpty()) {
            throw new InvalidPostException("Title is required");
        }
        if (postDto.getContent() == null || postDto.getContent().isEmpty()) {
            throw new InvalidPostException("Content is required");
        }
        if (mapDto.getLatitude() == null || mapDto.getLongitude() == null) {
            throw new InvalidPostException("Latitude and Longitude are required");
        }

        Map map = Map.builder()
                .locationName(mapDto.getLocationName())
                .latitude(mapDto.getLatitude())
                .longitude(mapDto.getLongitude())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Map saveMap = mapRepository.save(map);

        Post post = Post.builder()
                .userId(postDto.getUserId())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .map(saveMap)
                .meetTime(postDto.getMeetTime())
                .maxCount(postDto.getMaxCount())
                .userCount(0)
                .tag(postDto.getTag())
                .image(postDto.getImage())
                .build();

        Post savePost = postRepository.save(post);

        return convertToDto(savePost);
    }

    public void joinPost(Integer postId, ParticipantDto participantDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new InvalidPostException("Post not found"));

        if (post.getUserCount() >= post.getMaxCount()) {
            throw new ParticipantException("Cannot join, the maximum number of participants has been reached");
        }

        if (participantRepository.findByPostIdAndUserId(postId, String.valueOf(participantDto.getUserId())) != null) {
            throw new ParticipantException("User is already a participant in this post");
        }

        Participant participant = Participant.builder()
                .postId(post)
                .userId(participantDto.getUserId())
                .name(participantDto.getName())
                .phone(participantDto.getPhone())
                .build();

        participantRepository.save(participant);

        post.setUserCount(post.getUserCount() + 1);
        postRepository.save(post);
    }

    public void cancelJoinPost(Integer postId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new InvalidPostException("Post not found"));

        Participant participant = participantRepository.findByPostIdAndUserId(postId, userId);
        if (participant == null) {
            throw new ParticipantException("User is not a participant in this post");
        }

        participantRepository.delete(participant);

        post.setUserCount(post.getUserCount() - 1);
        postRepository.save(post);
    }

    public List<ParticipantDto> getPostParticipants(Integer postId) {
        List<Participant> participants = participantRepository.findByPostId(postId);
        return participants.stream()
                .map(participant -> ParticipantDto.builder()
                        .id(participant.getId())
                        .postId(participant.getPostId())
                        .userId(Integer.valueOf(participant.getUserId()))
                        .name(participant.getName())
                        .phone(participant.getPhone())
                        .build())
                .collect(Collectors.toList());
    }

    private PostDto convertToDto(Post post) {
        Map map = post.getMap();
        return PostDto.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .title (post.getTitle())
                .content(post.getContent())
                .map(map.getLocationName())
                .latitude(map.getLatitude())
                .longitude(map.getLongitude())
                .meetTime(post.getMeetTime())
                .maxCount(post.getMaxCount())
                .currentCount(post.getUserCount())
                .tag(post.getTag())
                .image(post.getImage())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .deletedAt(post.getDeletedAt())
                .build();
    }
}
