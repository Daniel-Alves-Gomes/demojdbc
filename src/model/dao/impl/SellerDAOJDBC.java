package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDAOJDBC implements SellerDAO{

	private Connection conn;
	
	public SellerDAOJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller sellerObj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller sellerObj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement(
				    "SELECT seller.*, department.Name as DepName "
				    + "FROM seller INNER JOIN department "
				    + "ON seller.DepartmentId = department.Id "
				    + "WHERE seller.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Department department = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs, department);
				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Seller instantiateSeller(ResultSet rSet, Department department) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rSet.getInt("Id"));
		seller.setName(rSet.getString("Name"));
		seller.setEmail(rSet.getString("Email"));
		seller.setBaseSalary(rSet.getDouble("BaseSalary"));
		seller.setBirthDate(rSet.getDate("BirthDate"));
		seller.setDepartment(department);
		return seller;
	}

	private Department instantiateDepartment(ResultSet rSet) throws SQLException {
		Department department = new Department();
		department.setId(rSet.getInt("DepartmentId"));
		department.setName(rSet.getString("DepName"));
		return department;
	}
	
	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name");
			
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
			
			st.setInt(1, department.getId());
			
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
