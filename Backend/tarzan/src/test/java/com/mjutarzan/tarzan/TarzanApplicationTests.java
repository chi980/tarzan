package com.mjutarzan.tarzan;

import com.mjutarzan.tarzan.domain.board.repository.BoardRepository;
import com.mjutarzan.tarzan.domain.board.repository.CommentRepository;
import com.mjutarzan.tarzan.domain.board.service.BoardService;
import com.mjutarzan.tarzan.domain.test.*;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
@SpringBootTest
@Transactional
class TarzanApplicationTests {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AlbumRepository albumRepository;

	@Autowired
	private ClassicRepository classicRepository;

	@Autowired
	private RockRepository rockRepository;

	@Autowired
	private BoardService boardService;

	@Autowired
	private CommentRepository commentRepository;

	@Test
	void contextLoads() {
//		Optional<Board> board = boardRepository.findById(6L);
//		Optional<User> writer = userRepository.findById(1L);
//
//		if(!board.isEmpty() && !writer.isEmpty()){
//			for (int i = 0; i < 10; i++) {
//				Comment comment = Comment.builder()
//						.board(board.get())
//						.writer(writer.get())
//						.content("더미데이터입니다")
//						.build();
//				commentRepository.saveAndFlush(comment);
//			}

//			List<Comment> commentList = commentRepository.findAll();
//			System.out.println(commentList.size());
//		}else{
//			System.out.println("오류");
//		}


	}
}
