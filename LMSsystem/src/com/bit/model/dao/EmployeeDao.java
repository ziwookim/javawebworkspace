// class EmployeeDao

package com.bit.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bit.Mysql.MysqlConnection;
import com.bit.model.dto.EmployeeDto;

public class EmployeeDao {
   private Connection conn;
   private PreparedStatement pstmt;
   private ResultSet rs;
   
   public EmployeeDao() throws SQLException{
      
      try { // jdbc connect j ���̺귯�� �ε� ���� ó��
			 Class.forName("com.mysql.cj.jdbc.Driver"); // �ش� Ŭ������ �޸𸮿� �ε� �� ����
	         String url = "jdbc:mysql://192.168.0.216:2080/lmssystem?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
//	          String url = "jdbc:mysql://localhost:3306/lmssystem?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
	         String user = "server3zo";
//	          String user = "root";
	         String password = "123456";
//	         String password = "0494";
         conn = DriverManager.getConnection(url, user, password);
      } catch (ClassNotFoundException e) {
         System.out.println("ClassNotFoundException: " + e.getMessage());
      } 
   }

   public EmployeeDto login(String userId, String password) {
      String sql = "select * from Employee where deleted=0 and userId=? and password=?";
      EmployeeDto bean = null;
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, userId);
         pstmt.setString(2, password);
         rs = pstmt.executeQuery();
         if(rs.next()){
            bean = new EmployeeDto();
            bean.setEmployeeIdx(rs.getInt("employeeIdx"));
            bean.setUserId(rs.getString("userId"));
            bean.setPassword(rs.getString("password"));
            bean.setName(rs.getString("name"));
            bean.setContact(rs.getString("contact"));
            bean.setEmail(rs.getString("email"));
            bean.setDepartment(rs.getString("department"));
            bean.setLevel(rs.getInt("level"));
            bean.setDeleted(rs.getInt("deleted"));
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally{
         try {
            if(rs != null) rs.close();
            if(conn != null) conn.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      
      return bean;
   } // login end
   

	// ������ �߰�
	public int adminInsert(String userId, String password,String name, String contact,String email,String department,int manageClass,int level) throws SQLException{
		String sql = "insert into resume(userId, password, name, contact, email, department, manageClass, level) values ( ?, ?, ?, ?, ?, ?, ?, ?)";

			try {
				conn=MysqlConnection.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userId);
				pstmt.setString(2, password);
				pstmt.setString(3, name);
				pstmt.setString(4, contact);
				pstmt.setString(5,email);
				pstmt.setString(6,department);
				pstmt.setInt(7,manageClass);
				pstmt.setInt(8,level);
				
				return pstmt.executeUpdate();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}finally{
			
				try {
					if(pstmt != null)pstmt.close();
					if(conn != null) conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
			}
			return -1;
		}
	
  // ���� ����
     public int deleteOne(int employeeIdx) throws SQLException {
        String sql = "update Employee set deleted=1 where deleted=0 and employeeIdx=?";
        try{
           pstmt = conn.prepareStatement(sql);
           pstmt.setInt(1, employeeIdx);
           
           return pstmt.executeUpdate();
        }finally{
           if(pstmt != null) pstmt.close();
           if(conn != null) conn.close();
        }
     } // deleteOne end
	
   public ArrayList<EmployeeDto> selectAll(){      
      ArrayList<EmployeeDto> list = new ArrayList<EmployeeDto>();
      String sql = "select * from Employee where deleted = 0 and  order by employeeIdx asc";
      try {
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();      
         while(rs.next()){
            list.add(new EmployeeDto(rs.getInt("employeeIdx"), rs.getString("userId"), rs.getString("password"), 
                  rs.getString("name"), rs.getString("contact"), rs.getString("email"), 
                  rs.getString("department"), rs.getInt("manageClass"),rs.getInt("level"), rs.getInt("deleted")));
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      return list;
   } // selectAll end
   
   public ArrayList<EmployeeDto> departmentAll(String department){ // �μ���
      ArrayList<EmployeeDto> list = new ArrayList<EmployeeDto>();
      String sql = "select * from Employee where deleted = 0 and department=? order by employeeIdx asc";
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, department);
         rs = pstmt.executeQuery();
         
         while(rs.next()){
            list.add(new EmployeeDto(rs.getInt("employeeIdx"), rs.getString("userId"), rs.getString("password"), 
                  rs.getString("name"), rs.getString("contact"), rs.getString("email"), 
                  rs.getString("department"), rs.getInt("manageClass"),rs.getInt("level"), rs.getInt("deleted")));
         }
         return list;
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      return null;
   }
      
      // ����
      public ArrayList<EmployeeDto> getGangsaList(){
         ArrayList<EmployeeDto> list = new ArrayList<EmployeeDto>();
         String sql = "select * from Employee where deleted = 0 and level=2 order by employeeIdx asc";
         try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while(rs.next()){
               list.add(new EmployeeDto(rs.getInt("employeeIdx"), rs.getString("userId"), rs.getString("password"), 
                     rs.getString("name"), rs.getString("contact"), rs.getString("email"), 
                     rs.getString("department"), rs.getInt("manageClass"),rs.getInt("level"), rs.getInt("deleted")));
            }
         } catch (SQLException e) {
            e.printStackTrace();
         } finally {
            try {
               if(rs != null) rs.close();
               if(pstmt != null) pstmt.close();
               if(conn != null) conn.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      
      return list;
   } // getGangsaList end
      
      public ArrayList<EmployeeDto> getChangeableGangsaList(){
          ArrayList<EmployeeDto> list = new ArrayList<EmployeeDto>();
          String sql = "select * from employee where deleted=0 and manageclass=0 and department='����'";
          try {
             pstmt = conn.prepareStatement(sql);
             rs = pstmt.executeQuery();
             
             while(rs.next()){
                list.add(new EmployeeDto(rs.getInt("employeeIdx"), rs.getString("userId"), rs.getString("password"), 
                      rs.getString("name"), rs.getString("contact"), rs.getString("email"), 
                      rs.getString("department"), rs.getInt("manageClass"),rs.getInt("level"), rs.getInt("deleted")));
             }
          } catch (SQLException e) {
             e.printStackTrace();
          } finally {
             try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
                if(conn != null) conn.close();
             } catch (SQLException e) {
                e.printStackTrace();
             }
          }
       
       return list;
    } // getChangeableManagerList end
          
       // ������ �� manageClass�� 0�� �����θ� �������� �޼��� �Դϴ�.
       public ArrayList<EmployeeDto> getAbleManagerList(){
    	   String sql= "select * from employee where department='������' and deleted=0 and manageClass=0";
    	   ArrayList<EmployeeDto> list = new ArrayList<EmployeeDto>();
    	   try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				EmployeeDto bean = new EmployeeDto(rs.getInt("employeeIdx"), rs.getString("userId"), rs.getString("password"), 
	                      rs.getString("name"), rs.getString("contact"), rs.getString("email"), 
	                      rs.getString("department"), rs.getInt("manageClass"),rs.getInt("level"), rs.getInt("deleted"));
				list.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    	   
    	   
    	   return list;
       } // getNewManagerList end
          
          
       public int updateManagerGangsaManageClass(int classIdx, int teacherId, int managerId) throws SQLException {
          // ���� ����� �Ŵ����� manageClass ���� 0���� �ٲٰ�
          String sql1 = "update Employee set manageClass=0 where deleted=0 and department in ('����', '������') and manageClass=?";
          // ���õ� ����� �Ŵ����� manageClass ���� ���� ���� classsIdx ������ �ٲ۴�.
          String sql2= "update Employee set manageClass=? where deleted=0 and employeeIdx=? or employeeIdx=?";
          
          try{
             int result = 0;
             pstmt = conn.prepareStatement(sql1);
             pstmt.setInt(1, classIdx);
             result += pstmt.executeUpdate();
             pstmt.close();
             pstmt = conn.prepareStatement(sql2);
             pstmt.setInt(1, classIdx);
             pstmt.setInt(2, teacherId);
             pstmt.setInt(3, managerId);
             
             result += pstmt.executeUpdate();
             return result;
          }finally{
             if(pstmt != null) pstmt.close();
             if(conn != null) conn.close();
          }
       } // updateManageClass end   
   
       
       
   public EmployeeDto selectOne(int employeeIdx) {
      EmployeeDto bean = null;
      String sql = "select * from Employee where deleted = 0 and employeeIdx = ?";
      try {
         
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, employeeIdx);
         rs = pstmt.executeQuery();
         
         if(rs.next()){
            bean = new EmployeeDto(rs.getInt("employeeIdx"), rs.getString("userId"), rs.getString("password"), 
                    rs.getString("name"), rs.getString("contact"), rs.getString("email"), 
                    rs.getString("department"), rs.getInt("manageClass"),rs.getInt("level"), rs.getInt("deleted"));
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }      
      return bean;
   } // selectOne end
   

   public int updateOne(int employeeIdx, String password, String name, String contact, String email, String department, int level, int deleted) throws SQLException {
      String sql = "update Employee set password=?, name=?, contact=?, email=?, department=?, level=?, "
            + "deleted=? where employeeIdx=?";
      try{
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, password);
         pstmt.setString(2, name);
         pstmt.setString(3, contact);
         pstmt.setString(4, email);
         pstmt.setString(5, department);
         pstmt.setInt(6, level);
         pstmt.setInt(7, deleted);
         pstmt.setInt(8, employeeIdx);
         
         return pstmt.executeUpdate();
      }finally{
         if(pstmt != null) pstmt.close();
         if(conn != null) conn.close();
      }
   } // updateOne end
   
      
   // ���̵� ã��
  	public String findUserId(String name, String contact) {
  		String sql = "select userId from Employee where deleted=0 and name=? and contact=?";
  		String userId = "";
  		try {
  			
  			pstmt = conn.prepareStatement(sql);
  			pstmt.setString(1, name);
  			pstmt.setString(2, contact);
  			rs = pstmt.executeQuery();
  			
  			if(rs.next()){
  				userId = rs.getString("userId");
  			}
  		} catch (SQLException e) {
  			e.printStackTrace();
  		} finally {
  			try {
  				if(rs != null) rs.close();
  				if(pstmt != null) pstmt.close();
  				if(conn != null) conn.close();
  			} catch (SQLException e) {
  				e.printStackTrace();
  			}
  		}
  		return userId;
  	} // findUserId end
  	
  	// ��й�ȣ ã�� �� ���� Ȯ�� 
  	public String findPassword(String id, String name, String contact) {
		String sql = "select userId from Employee where deleted=0 and userId=? name=? and contact=?";
		
		String userId = "";
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, contact);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				userId = rs.getString("userId");
				return userId;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userId;
	}
  	
  	public int updatePassword(String userId, String newPassword) throws SQLException {
		String sql = "update Employee set password=? where deleted=0 and userId=?";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newPassword);
			pstmt.setString(1, userId);
			
			return pstmt.executeUpdate();
		}finally{
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
	}
  	
   public int rowCount() throws SQLException{
      String sql = "select count(*) as cnt from Employee where deleted=0";
      try{
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();
         if(rs.next()) return rs.getInt("cnt");
      } finally{
         if(rs != null) rs.close();
         if(pstmt != null) pstmt.close();
         if(conn != null) conn.close();
      }
      return 0;
   } // rowCount end
   

}