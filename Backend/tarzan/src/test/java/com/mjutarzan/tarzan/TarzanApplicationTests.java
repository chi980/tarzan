package com.mjutarzan.tarzan;

import com.mjutarzan.tarzan.domain.board.entity.Board;
import com.mjutarzan.tarzan.domain.board.entity.Comment;
import com.mjutarzan.tarzan.domain.board.repository.BoardRepository;
import com.mjutarzan.tarzan.domain.board.repository.CommentRepository;
import com.mjutarzan.tarzan.domain.board.service.BoardService;
import com.mjutarzan.tarzan.domain.test.*;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
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
	@Transactional
	@Rollback(false)
	void contextLoads() {
		Optional<Board> board = boardRepository.findById(1556L);
		Optional<User> writer = userRepository.findById(1L);

		if(!board.isEmpty() && !writer.isEmpty()){
			for (int i = 0; i < 10; i++) {
				Comment comment = Comment.builder()
						.board(board.get())
						.writer(writer.get())
						.content("더미데이터입니다")
						.build();
				commentRepository.saveAndFlush(comment);
			}

			List<Comment> commentList = commentRepository.findAll();
			System.out.println(commentList.size());
		}else{
			System.out.println("오류");
		}


	}
}
