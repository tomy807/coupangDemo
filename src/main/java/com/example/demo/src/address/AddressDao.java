package com.example.demo.src.address;

import com.example.demo.src.address.model.GetAddressRes;
import com.example.demo.src.address.model.PostAddressReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class AddressDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public GetAddressRes getAddress(int userIdx) {

        return jdbcTemplate.queryForObject("select main_address,sub_address_1,sub_address_2 from Address where user_id=?",
                (rs, rowNum) -> new GetAddressRes(
                        rs.getString("main_address"),
                        rs.getString("sub_address_1"),
                        rs.getString("sub_address_2")),
                userIdx);
    }

    public void createAddress(int userIdx, PostAddressReq postAddressReq) {
        Object[] createUserParams = new Object[]{userIdx,postAddressReq.getMainAddress(),postAddressReq.getSubAddress1(),postAddressReq.getSubAddress2()};
        jdbcTemplate.update("insert into Address(user_id,main_address,sub_address_1,sub_address_2) VALUES (?,?,?,?)", createUserParams);
    }

    public void modifyAddress(int userIdx, PostAddressReq postAddressReq) {
        jdbcTemplate.update("update Address set main_address=?,sub_address_1=?,sub_address_2=? where user_id=?", userIdx, postAddressReq.getMainAddress(), postAddressReq.getSubAddress1(), postAddressReq.getSubAddress2());
    }
}
