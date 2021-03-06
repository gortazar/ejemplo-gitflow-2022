package es.codeurjc.daw.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.codeurjc.daw.controller.BasicPostDto;
import es.codeurjc.daw.controller.FullPostDto;
import es.codeurjc.daw.model.Post;
import es.codeurjc.daw.repository.PostRepository;

@Service
public class BlogQueryService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public List<BasicPostDto> getPostsList(Page page) {
		return postRepository.findAll(page.getPageable())
				.stream()
				.map((post) -> convertPostEntityToDto(post))
				.collect(Collectors.toList());
	}

	public FullPostDto getPost(long id) {
		Post post = postRepository.findById(id).get();
		if(post != null) {
			return convertPostEntityToPostFullDto(post);
		}
		return null;
	}

	private FullPostDto convertPostEntityToPostFullDto(Post post) {
		return modelMapper.map(post, FullPostDto.class);
	}

	private BasicPostDto convertPostEntityToDto(Post post) {
		BasicPostDto dto = modelMapper.map(post, BasicPostDto.class);
		dto.setNumComments(post.getComments().size());
		return dto;
	}


}
