package com.example.postservice;

import com.example.postservice.entities.Post;
import com.example.postservice.entities.User;
import com.example.postservice.repositories.PostRepository;
import com.example.postservice.service.PostService;
import com.example.postservice.service.impl.PostServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceApplicationTests {

	String dateString1 = "2023-06-29T10:30:00";
	String dateString2 = "2023-07-29T10:40:00";

	DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	LocalDateTime dateTime = LocalDateTime.parse(dateString1, formatter);
	LocalDateTime dateTime2 = LocalDateTime.parse(dateString2, formatter);

    PostService postService;

	@Mock
	private PostRepository postRepository;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        postService = new PostServiceImpl(postRepository,objectMapper);
    }

	@Test
	public void getAllPostsTest() {
        List<Post> postList = initializePosts();
        doReturn(postList).when(postRepository).findAll();
        List<Post> expected = postService.getAllPosts();
		assertEquals(postList.size(), expected.size());
	}

	@Test
	public void savePostTest() {
        Post post=initializePosts().get(1);
        doReturn(post).when(postRepository).save(post);
        Post expectedPost=postService.savePost(post);
        assertEquals(post, expectedPost);
    }

	@Test
	void deletePostById() {
        Post post=initializePosts().get(1);
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.of(post));
        boolean result = postService.deletePostById(post.getPostId());
        assertTrue(result);
        verify(postRepository, times(1)).deleteById(post.getPostId());
        verify(postRepository, times(1)).findById(post.getPostId());
	}

	@Test
	public void updatePostById() {
        Post existingPost=initializePosts().get(1);

        Post updatedPost=initializePosts().get(1);
        updatedPost.setTitle("Pranyaaa");
        updatedPost.setContent("Hello Everybody and you");

		Optional<Post> optionalPost = Optional.of(existingPost);
		Mockito.when(postRepository.findById(existingPost.getPostId())).thenReturn(optionalPost);
		Mockito.when(postRepository.save(Mockito.any(Post.class))).thenReturn(updatedPost);

		Post result = postService.updatePostById(existingPost.getPostId(), updatedPost);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(updatedPost.getTitle(), result.getTitle());
		Assertions.assertEquals(updatedPost.getContent(), result.getContent());
		Assertions.assertEquals(updatedPost.getCommentsCount(), result.getCommentsCount());
	}

    @Test
    public void getPostById() {
        Post existingPost=initializePosts().get(1);

        Optional<Post> optionalPost = Optional.of(existingPost);
        Mockito.when(postRepository.findById(existingPost.getPostId())).thenReturn(optionalPost);

        Post result = postService.getPostById(existingPost.getPostId());

        assertEquals(result, existingPost);
    }


    private List<Post> initializePosts(){
        return Arrays.asList(
                new Post("1",0,"Pranya","Hello Everybody",new User(5,"Mostafa"), dateTime),
                new Post("2",0, "London","Hello All Of People",new User(16,"Ahmed"), dateTime2));
    }


}
