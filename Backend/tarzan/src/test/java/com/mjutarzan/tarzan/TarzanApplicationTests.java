package com.mjutarzan.tarzan;

import com.mjutarzan.tarzan.domain.board.repository.BoardRepository;
import com.mjutarzan.tarzan.domain.board.service.BoardService;
import com.mjutarzan.tarzan.domain.test.*;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

	@Test
	void contextLoads() {
		
	}
}
