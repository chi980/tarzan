package com.mjutarzan.tarzan;

import com.mjutarzan.tarzan.domain.board.entity.Board;
import com.mjutarzan.tarzan.domain.board.model.vo.BoardTag;
import com.mjutarzan.tarzan.domain.board.repository.BoardRepository;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TarzanApplicationTests {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
		User dummy = User.builder()
				.email("dummy@tarzan.com").build();
		User user = userRepository.findById(1L).orElse(dummy);
		user = userRepository.saveAndFlush(user);
		for(SiGunGu gu : SiGunGu.values()){
			for(BoardTag tag : BoardTag.values()){
				for(int i=0;i<10;i++){
					boardRepository.save(Board.builder()
							.title("더미 데이터")
							.content("더미 데이터")
							.tag(tag)
							.gu(gu)
							.writer(user)
							.build());
				}
			}
		}

		boardRepository.flush();


//		List<Board> boardList = boardRepository.findByWriter(user);
//		boardList.stream().forEach(board -> {
//			System.out.println("board = " + board);
//		});
	}

}
