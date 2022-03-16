package com.example.demo.src.user;


import com.example.demo.src.oauth.ProfileDto;
import com.example.demo.src.oauth.UserDto;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers(){
        String getUsersQuery = "select * from User";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("point"))
        );
    }

    public List<GetUserRes> getUsersByEmail(String email){
        String getUsersByEmailQuery = "select * from User where email =?";
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("point")),
                getUsersByEmailParams);
    }

    public GetUserRes getUser(int userIdx){
        String getUserQuery = "select * from User where user_id = ?";
        int getUserParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("point")),
                getUserParams);
    }

    public int getPoint(int userIdx) {
        return jdbcTemplate.queryForObject("select point from User where user_id=?", int.class, userIdx);
    }

    public int getSellerIdx(int productIdx) {
        return jdbcTemplate.queryForObject("select seller_id from Product where product_id=?", int.class, productIdx);
    }

    public void updatePoint(int totalOrderPrice, int userIdx) {
        jdbcTemplate.update("update User set point=point-? where user_id=?", totalOrderPrice, userIdx);
    }

    public void updateSellerPoint(int point, int sellerIdx) {
        jdbcTemplate.update("update Seller set point=point+? where seller_id=?", point, sellerIdx);
//        throw new RuntimeException();
    }



    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into User (user_name, email, password, phone) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getUserName(), postUserReq.getEmail(), postUserReq.getPassword(), postUserReq.getPhone()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public int createUserOAuth(ProfileDto profileDto,String provider){
        String createUserQuery = "insert into User (user_name, email, password,provider) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{profileDto.getName(), profileDto.getEmail(), UUID.randomUUID().toString(), provider};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);

    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);
    }

    public int checkUser(int userIdx) {
        String checkUserIdxQuery = "select exists(select user_id from User where user_id = ?)";
        return this.jdbcTemplate.queryForObject(checkUserIdxQuery,
                int.class,
                userIdx);
    }

    public int modifyUserName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update User set user_name = ? where user_id = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select user_id, user_name,email,password,phone,point,provider from User where email = ?";
        String getPwdParams = postLoginReq.getEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getInt("point"),
                        rs.getString("provider")
                ),
                getPwdParams
                );
    }


    public List<GetUserRes> findByEmailAndProvider(String email, String provider) {
        return jdbcTemplate.query("select * from User where email=? and provider=?",
                            (rs, rowNum) -> new GetUserRes(
                                    rs.getInt("user_id"),
                                    rs.getString("user_name"),
                                    rs.getString("email"),
                                    rs.getString("phone"),
                                    rs.getInt("point")
                ), email, provider);
    }

    public int withdrawal(int userIdx) {
        return jdbcTemplate.update("update User set status = 'WITHDRAWAL' where user_id=?", userIdx);
    }

    public String getEmailByUserIdx(int userIdx) {
        return jdbcTemplate.queryForObject("select email from User where user_id=?", String.class, userIdx);
    }
}
