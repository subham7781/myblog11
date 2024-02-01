package com.myblog.myblog11.service.impl;

import com.myblog.myblog11.Exception.ResourceNotFoundException;
import com.myblog.myblog11.entity.Comment;
import com.myblog.myblog11.entity.Post;
import com.myblog.myblog11.payload.CommentDto;
import com.myblog.myblog11.repository.PostRepository;
import com.myblog.myblog11.repository.CommentRepository;
import com.myblog.myblog11.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;


    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post Not found with id::" + postId)
        );
        Comment comment= new Comment();
        comment.setEmail(commentDto.getEmail());
        comment.setText(commentDto.getText());
        comment.setPost(post);

        Comment save = commentRepository.save(comment);

        CommentDto dto = new CommentDto();
        dto.setId(save.getId());
        dto.setEmail(save.getEmail());
        dto.setText(dto.getText());
        return dto;
    }

    //http://localhost:8080/api/comment/1/post/1
    @Override
    public void deleteComment(long id) {

        commentRepository.deleteById(id);
    }

    @Override
    public CommentDto UpdateComment(long id, CommentDto commentdto, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post is not found"+postId)
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Comment is not found"+id)
        );
        Comment c = mapToEntity(commentdto);
        c.setId(comment.getId());
        c.setPost(post);
        Comment save = commentRepository.save(c);
        return mapToDto(save);
    }

    CommentDto mapToDto(Comment comment) {
        CommentDto dto = modelMapper.map(comment, CommentDto.class);
        return dto;
    }

    Comment mapToEntity(CommentDto commentDto) {
        Comment dto = modelMapper.map(commentDto, Comment.class);
        return dto;
    }
}
