package course.springdata.intro.dao;

import course.springdata.intro.entity.Account;
import course.springdata.intro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account ,Long> {
}
