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

//	@Test
//	void contextLoads() {
//		User dummy = User.builder()
//				.email("dummy@tarzan.com").build();
//		User user = userRepository.findById(1L).orElse(dummy);
//		user = userRepository.saveAndFlush(user);
//		for(SiGunGu gu : SiGunGu.values()){
//			for(BoardTag tag : BoardTag.values()){
//				for(int i=0;i<10;i++){
//					boardRepository.save(Board.builder()
//							.title("더미 데이터")
//							.content("더미 데이터")
//							.tag(tag)
//							.gu(gu)
//							.writer(user)
//							.build());
//				}
//			}
//		}
//
//		boardRepository.flush();
//
//
////		List<Board> boardList = boardRepository.findByWriter(user);
////		boardList.stream().forEach(board -> {
////			System.out.println("board = " + board);
////		});
//	}
//
//	@Test
//	void testMultiInheritance() {
//		System.out.println("multiInheritance test 시작");
//
//		Book book = Book.bookBuilder()
//				.name("햄릿")
//				.author("셰익스피어")
//				.price(10000)
//				.build();
//
//		bookRepository.saveAndFlush(book);
//
//		Classic classic = Classic.classicBuilder()
//				.name("피아노 협주곡")
//				.artist("모차르트")
//				.price(2000)
//				.build();
//
//		classicRepository.saveAndFlush(classic);
//
//		Rock rock = Rock.rockBuilder()
//				.name("what the hell")
//				.artist("avril lavigne")
//				.festival("rock rock")
//				.build();
//
//		rockRepository.saveAndFlush(rock);
//
//		List<Item> allItems = itemRepository.findAll();
//		assertEquals(3, allItems.size(), "Item 리스트의 크기는 3이어야 합니다.");
//
//		allItems.forEach(item -> {
//			System.out.println("item.getClass() = " + item.getClass());
//
//			if(item instanceof Book){
//				System.out.println("book 객체");
//			}else if(item instanceof Classic){
//				System.out.println("classic 객체");
//			}else if(item instanceof Rock){
//				System.out.println("rock 객체");
//			}else{
//				System.out.println("모름");
//			}
//		});
//
//		List<Album> allAlbums = albumRepository.findAll();
//		assertEquals(2, allAlbums.size(), "Album 리스트의 크기는 2이어야 합니다.");
//
//
//	}

//	@Test
//	void boardDetailTest(){
//		boardService.getBoard(55L, UserDto.builder().build());
//	}
}
