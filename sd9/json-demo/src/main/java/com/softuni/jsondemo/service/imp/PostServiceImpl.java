package com.softuni.jsondemo.service.imp;

import com.softuni.jsondemo.entity.Post;
import com.softuni.jsondemo.exception.InvalidEntityDataException;
import com.softuni.jsondemo.exception.NonExistingEntityException;
import com.softuni.jsondemo.repository.PostRepository;
import com.softuni.jsondemo.repository.UserRepository;
import com.softuni.jsondemo.service.PostService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional()
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(()->
                new NonExistingEntityException(String.format("Post with ID=%s does not exist.",id)));
    }

    @Transactional
    @Override
    public Post addPost(Post post) {
        post.setId(null);
        if (post.getAuthor()==null){
            if (post.getAuthorId()==null){
                throw new InvalidEntityDataException("Post author is required but missing.");
            }
            post.setAuthor(userRepository.findById(post.getAuthorId()).orElseThrow(()->
                    new InvalidEntityDataException(
                            String.format("Post author with ID:%s does not exist.",post.getAuthorId()))));
        }
        return postRepository.save(post);
    }

    @Transactional
    @Override
    public Post updatePost(Post post) {
        getPostById(post.getId());
        return postRepository.save(post);
    }

    @Transactional
    @Override
    public Post deletePost(Long id) {
        Post removed= getPostById(id);
        postRepository.deleteById(id);
        return removed;
    }

    @Override
    public long getPostsCount() {
        return postRepository.count();
    }
}
