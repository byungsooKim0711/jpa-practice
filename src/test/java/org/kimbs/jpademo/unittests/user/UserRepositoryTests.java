package org.kimbs.jpademo.unittests.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kimbs.jpademo.user.domain.User;
import org.kimbs.jpademo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class UserRepositoryTests {

    @Autowired
	private TestEntityManager entityManager;

	@Autowired
	UserRepository repository;

	private User generateUserInfo() {
		User user = new User();
		user.setPhone("010-0000-0000");
		user.setLoginId(UUID.randomUUID().toString());
		user.setEmail("test@test.test");
		user.setPassword(UUID.randomUUID().toString());
		return user;
	}
	
	@Test
	public void shouldFindNoUsersIfRepositoryIsEmpty() {
		// act
		Iterable<User> members = repository.findAll();

		// assert
		assertThat(members).isEmpty();
	}
	
	@Test
	public void shouldStoreAUser() {
		// arrange
		User user = this.generateUserInfo();

		// act
		User expected = repository.saveAndFlush(user);

		// assert
		assertEquals(user.getLoginId(), expected.getLoginId());
		assertEquals(user.getEmail(), expected.getEmail());
        assertEquals(user.getPhone(), expected.getPhone());
        assertEquals(user.getPassword(), expected.getPassword());
        assertEquals(user.getId(), expected.getId());
	}

	@Test
	public void shouldFindAllUsers() {
		// arrange
		User user1 = this.generateUserInfo();
		User user2 = this.generateUserInfo();

		entityManager.persist(user1);
		entityManager.persist(user2);

		// act
		Iterable<User> users = repository.findAll();

		// assert
		assertThat(users).hasSize(2).contains(user1, user2);
	}

	@Test
	public void shouldFindUserById() {
		// arrange
		User user1 = this.generateUserInfo();
		entityManager.persist(user1);
		
		User user2 = this.generateUserInfo();
		entityManager.persist(user2);

		// act
		Optional<User> actual = repository.findById(user2.getId());
		assertThat(actual).isNotEmpty().contains(user2);
	}

	@Test
	public void shouldDeleteAllUsers() {
		// arrange
		User user1 = this.generateUserInfo();
		entityManager.persist(user1);
		
		User user2 = this.generateUserInfo();
		entityManager.persist(user2);

		// act
		repository.deleteAll();

		// assert
		assertThat(repository.findAll()).isEmpty();
	}
}