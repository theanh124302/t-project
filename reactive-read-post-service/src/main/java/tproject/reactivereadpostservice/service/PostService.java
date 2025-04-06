package tproject.reactivereadpostservice.service;


import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tproject.reactivereadpostservice.repository.ContentRepository;
import tproject.reactivereadpostservice.repository.MediaRepository;
import tproject.reactivereadpostservice.repository.PostRepository;

@Service
@AllArgsConstructor
public class PostService {

    private static final Logger log = LoggerFactory.getLogger(PostService.class);
    private final PostRepository postRepository;
    private final ContentRepository contentRepository;
    private final MediaRepository mediaRepository;



}

