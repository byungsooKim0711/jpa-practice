package org.kimbs.jpademo.unittests.user;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kimbs.jpademo.user.domain.User;
import org.kimbs.jpademo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
	private TestEntityManager entityManager;

	@Autowired
	UserRepository repository;
	
	@Test
	public void shouldFindNoUsersIfrepositoryIsEmpty() {
		Iterable<User> members = repository.findAll();
 
		assertThat(members).isEmpty();
	}
	
	@Test
	public void shouldStoreAUser() {
		User user = new User();
        user.setLoginId("kimbs");
        user.setPassword("kimbs");
        user.setPhone("010-0000-0000");
        user.setEmail("vlakd0711@naver.com");
		
		User saved = repository.save(user);
 
		assertEquals(saved.getLoginId(), user.getLoginId());
		assertEquals(saved.getEmail(), user.getEmail());
        assertEquals(saved.getPhone(), user.getPhone());
        assertEquals(saved.getPassword(), user.getPassword());
	}

	@Test
	public void shouldFindAllUsers() {
		User user1 = new User();
        user1.setLoginId("kimbs1");
        user1.setPassword("kimbs1");
        user1.setPhone("010-1111-1111");
		user1.setEmail("kimbs1@naver.com");
		entityManager.persist(user1);
		
		User user2 = new User();
        user2.setLoginId("kimbs2");
        user2.setPassword("kimbs2");
        user2.setPhone("010-2222-2222");
		user2.setEmail("kimbs2@naver.com");
		entityManager.persist(user2);

		Iterable<User> users = repository.findAll();
 
		assertThat(users).hasSize(2).contains(user1, user2);
	}

	@Test
	public void shouldFindUserById() {
		User user1 = new User();
        user1.setLoginId("kimbs1");
        user1.setPassword("kimbs1");
        user1.setPhone("010-1111-1111");
		user1.setEmail("kimbs1@naver.com");
		entityManager.persist(user1);
		
		User user2 = new User();
        user2.setLoginId("kimbs2");
        user2.setPassword("kimbs2");
        user2.setPhone("010-2222-2222");
		user2.setEmail("kimbs2@naver.com");
		entityManager.persist(user2);
 
		User found = repository.findById(user2.getId()).get();
 
		assertThat(found).isEqualTo(user2);
	}

	@Test
	public void shouldDeleteAllUsers() {
		User user1 = new User();
        user1.setLoginId("kimbs1");
        user1.setPassword("kimbs1");
        user1.setPhone("010-1111-1111");
		user1.setEmail("kimbs1@naver.com");
		entityManager.persist(user1);
		
		User user2 = new User();
        user2.setLoginId("kimbs2");
        user2.setPassword("kimbs2");
        user2.setPhone("010-2222-2222");
		user2.setEmail("kimbs2@naver.com");
		entityManager.persist(user2);
 
		repository.deleteAll();
 
		assertThat(repository.findAll()).isEmpty();
	}
}