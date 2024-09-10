package com.bookstore.bookstore_dto.repository;

import com.bookstore.bookstore_dto.domain.Address;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class AddressRepository {
    private final JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    /**
     * 주소 저장
     */
    public void saveAddress (Address address, String user_id) {
        String sql = "insert into address (postal_code, basic_address, detail_address, user_id) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, address.getPostal_code(), address.getBasic_address(), address.getDetail_address(), user_id);
    }

    /**
     * 주소 목록 조회
     */
    public List<Address> findByUserId(String user_id) {
        String sql = "select adress_id, postal_code, basic_address, detail_address from address where user_id = ?";
        return jdbcTemplate.query(sql, findRowMapper(), user_id);
    }

//    public List<Address> findPostalCodeById(int address_id) {
//        String sql = "SELECT postal_code FROM address WHERE adress_id = ?";
//        return jdbcTemplate.query(sql, findRowMapper(), address_id);
//    }

    public List<Address> findBasicAddressById(int postal_code) {
        String sql = "SELECT basic_address FROM address WHERE postal_code = ?";
        return jdbcTemplate.query(sql, findBasicAddressRowMapper(), postal_code);
    }

    public List<Address> findDetailAddressById(int postal_code) {
        String sql = "SELECT detail_address FROM address WHERE postal_code = ?";
        return jdbcTemplate.query(sql, findDetailAddressRowMapper(), postal_code);
    }

    private RowMapper<Address> findRowMapper() {
        return (rs, rowNum) -> {
            Address address = new Address();
            address.setAdress_id(rs.getInt("adress_id"));
            address.setPostal_code(rs.getInt("postal_code"));
            address.setBasic_address(rs.getString("basic_address"));
            address.setDetail_address(rs.getString("detail_address"));
            return address;
        };
    }

    private RowMapper<Address> findBasicAddressRowMapper() {
        return (rs, rowNum) -> {
            Address address = new Address();
            address.setBasic_address(rs.getString("basic_address"));
            return address;
        };
    }

    private RowMapper<Address> findDetailAddressRowMapper() {
        return (rs, rowNum) -> {
            Address address = new Address();
            address.setDetail_address(rs.getString("detail_address"));
            return address;
        };
    }
}
