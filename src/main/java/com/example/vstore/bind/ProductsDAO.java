package com.example.vstore.bind;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;
@Repository
@Transactional
@Component
public class ProductsDAO extends JdbcDaoSupport {

    @Autowired
    public ProductsDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<Products> findUnsafeByName_product(String name) throws SQLException {
        String sql = "Select * from products where name_product = '" + name + "';";

        //List<Products> products = this.getJdbcTemplate().queryForList(sql, Products.class);
        Connection c = this.getConnection();
        ResultSet rs = c.createStatement().executeQuery(sql);

        List<Products> products = new ArrayList<>();
        while (rs.next()) {
            Products p = new Products(rs.getLong("id_product"), rs.getLong("price"),rs.getLong("number"), rs.getString("name_product"));
            products.add(p);
        }
        return products;
    }

}