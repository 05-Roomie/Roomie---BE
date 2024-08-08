package TEAM5.Roomie.controller;


import TEAM5.Roomie.Exception.InvalidPostException;
import TEAM5.Roomie.dto.MapDto;
import TEAM5.Roomie.dto.ParticipantDto;
import TEAM5.Roomie.dto.PostDto;
import TEAM5.Roomie.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(@RequestParam int page, @RequestParam int size) {
        List<PostDto> posts = postService.getAllPosts(page, size);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/tag")
    public ResponseEntity<List<PostDto>> getPostsByTag(@RequestParam String tag, @RequestParam int page, @RequestParam int size) {
        List<PostDto> posts = postService.getPostsByTag(tag, page, size);
        return ResponseEntity.ok(posts);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @RequestBody MapDto mapDto) {
        try {
            PostDto createdPost = postService.createPost(postDto, mapDto);
            return ResponseEntity.ok(createdPost);
        } catch (InvalidPostException ex) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/{postId}/join")
    public ResponseEntity<Void> joinPost(@PathVariable Integer postId, @RequestBody ParticipantDto participantDto) {
        try {
            postService.joinPost(postId, participantDto);
            return ResponseEntity.ok().build();
        } catch (ParticipantException | InvalidPostException ex) {
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/{postId}/cancel")
    public ResponseEntity<Void> cancelJoinPost(@PathVariable Integer postId, @RequestParam String userId) {
        try {
            postService.cancelJoinPost(postId, userId);
            return ResponseEntity.ok().build();
        } catch (ParticipantException | InvalidPostException ex) {
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{postId}/participants")
    public ResponseEntity<List<ParticipantDto>> getPostParticipants(@PathVariable Integer postId) {
        List<ParticipantDto> participants = postService.getPostParticipants(postId);
        return ResponseEntity.ok(participants);
    }
}
