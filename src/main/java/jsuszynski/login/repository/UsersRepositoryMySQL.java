package jsuszynski.login.repository;


import jsuszynski.login.domain.Role;
import jsuszynski.login.domain.User;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Singleton
public class UsersRepositoryMySQL implements UsersRepository {
    public static final String LOGIN = "login";
    @PersistenceContext(unitName = "contacts")
    private EntityManager entityManager;

    @Override
    public User findUserByLogin(String login) {
        return (User) entityManager.createQuery("FROM User u WHERE u.login=:login")
                .setParameter(LOGIN, login)
                .getSingleResult();
    }


    @Override
    public boolean doesExist(String login) {
        List results = entityManager.createQuery("FROM User u WHERE  u.login=:login")
                .setParameter(LOGIN, login).getResultList();
        return !results.isEmpty();

    }


    @Override
    public void register(User user) {

        entityManager.persist(user);
        entityManager.persist(new Role(user.getLogin() //add role to db with new users login and id
                , "user"
                , "user"));
    }

}
