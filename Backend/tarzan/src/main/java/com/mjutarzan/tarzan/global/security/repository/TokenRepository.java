package com.mjutarzan.tarzan.global.security.repository;

import com.mjutarzan.tarzan.global.security.model.Token;
import org.springframework.data.repository.CrudRepository;
public interface TokenRepository  extends CrudRepository<Token, Long> {
}
