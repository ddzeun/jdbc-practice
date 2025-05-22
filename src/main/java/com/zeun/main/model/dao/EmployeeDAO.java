package com.zeun.main.model.dao;

import com.zeun.main.model.dto.EmployeeDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.zeun.common.JDBCTemplate.close;

public class EmployeeDAO {

    private Properties prop = new Properties();

    public EmployeeDAO() {
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/zeun/mapper/employee-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectAllEmp(Connection con) {

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        List<EmployeeDTO> employeeList = new ArrayList<>();

        String query = prop.getProperty("selectAllEmp");

        try {
            pstmt = con.prepareStatement(query);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                EmployeeDTO employee = new EmployeeDTO();

                employee.setEmpId(rset.getString("EMP_ID"));
                employee.setEmpName(rset.getString("EMP_NAME"));
                employee.setEmpNo(rset.getString("EMP_NO"));
                employee.setEmail(rset.getString("EMAIL"));
                employee.setPhone(rset.getString("PHONE"));
                employee.setDeptCode(rset.getString("DEPT_CODE"));
                employee.setJobCode(rset.getString("JOB_CODE"));
                employee.setSalLevel(rset.getString("SAL_LEVEL"));
                employee.setSalary(rset.getInt("SALARY"));
                employee.setBonus(rset.getDouble("BONUS"));
                employee.setManagerId(rset.getString("MANAGER_ID"));
                employee.setHireDate(rset.getDate("HIRE_DATE"));
                employee.setEntDate(rset.getDate("ENT_DATE"));
                employee.setEntYn(rset.getString("ENT_YN"));

                employeeList.add(employee);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (EmployeeDTO employee: employeeList) {
            System.out.println("employee = " + employee);
        }
    }
//
    public void selectByIdEmp(Connection con, String empId) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        EmployeeDTO employee = new EmployeeDTO();

        String query = prop.getProperty("selectByIdEmp");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, empId);
            rset = pstmt.executeQuery();

            if (rset.next()) {

                employee.setEmpId(rset.getString("EMP_ID"));
                employee.setEmpName(rset.getString("EMP_NAME"));
                employee.setEmpNo(rset.getString("EMP_NO"));
                employee.setEmail(rset.getString("EMAIL"));
                employee.setPhone(rset.getString("PHONE"));
                employee.setDeptCode(rset.getString("DEPT_CODE"));
                employee.setJobCode(rset.getString("JOB_CODE"));
                employee.setSalLevel(rset.getString("SAL_LEVEL"));
                employee.setSalary(rset.getInt("SALARY"));
                employee.setBonus(rset.getDouble("BONUS"));
                employee.setManagerId(rset.getString("MANAGER_ID"));
                employee.setHireDate(rset.getDate("HIRE_DATE"));
                employee.setEntDate(rset.getDate("ENT_DATE"));
                employee.setEntYn(rset.getString("ENT_YN"));
            }

            System.out.println("employee = " + employee);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
        }

    }

    public int insertEmp(Connection con, EmployeeDTO employee) {
        PreparedStatement pstmt = null;
        int result = 0;

        String query = prop.getProperty("insertEmp");

        try {
            pstmt = con.prepareStatement(query);

            pstmt.setString(1, employee.getEmpId());
            pstmt.setString(2, employee.getEmpName());
            pstmt.setString(3, employee.getEmpNo());
            pstmt.setString(4, employee.getJobCode());
            pstmt.setString(5, employee.getSalLevel());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }

        return result;
    }

    public int modifyEmp(Connection con, String empId, String jobCdoe, String salLevel) {
        PreparedStatement pstmt = null;
        int result = 0;

        String query = prop.getProperty("modifyEmp");

        try {
            pstmt = con.prepareStatement(query);

            pstmt.setString(1, jobCdoe);
            pstmt.setString(2, salLevel);
            pstmt.setString(3, empId);

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }

        return result;
    }
    public int deleteEmp(Connection con, String empId) {

        PreparedStatement pstmt = null;
        int result = 0;

        String query = prop.getProperty("deleteEmp");
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, empId);

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }

        return result;
    }

}
