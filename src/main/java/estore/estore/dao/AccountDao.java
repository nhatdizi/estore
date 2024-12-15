package estore.estore.dao;

import estore.estore.model.Account;
import estore.estore.model.Role;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountDao{

    private JdbcTemplate jdbcTemplate;

    public AccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Account getByName(String name) {
        String sql = "SELECT a.id, a.user_name, a.password, a.roleId, r.position FROM " +
                "account a JOIN role r ON a.roleId = r.id where a.user_name = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{name}, (rs, rowNum) -> {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setUsername(rs.getString("user_name"));
                account.setPassword(rs.getString("password"));
                Role role = new Role();
                role.setId(rs.getInt("roleId"));
                role.setPosition(rs.getString("position"));
                account.setRole(role);
                return account;
            });
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public int add(Account account) {
        Account account1 = getByName(account.getUsername());
        if (account1 != null){
            return 0;
        }else{
            String sql = "insert into account(user_name, password, email, roleId) value (?, ?, ?, ?)";
            return jdbcTemplate.update(sql, account.getUsername(), account.getPassword(),
                    account.getEmail(), 2);
        }
    }
}
