package com.zeun.main.view;

import com.zeun.main.model.dao.EmployeeDAO;
import com.zeun.main.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.util.Scanner;

import static com.zeun.common.JDBCTemplate.close;
import static com.zeun.common.JDBCTemplate.getConnection;

public class run {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Connection con = getConnection();

        EmployeeDAO employeeDAO = new EmployeeDAO();

        while (true) {

            System.out.println("======= 메뉴를 선택하세요 =======");
            System.out.println("1. 직원 전체 조회     2. 직원 상세 조회     3. 직원 등록");
            System.out.println("4. 직원 수정         5. 직원 삭제         0. 종료");
            System.out.print("메뉴 선택: ");
            int selected = sc.nextInt();
            sc.nextLine();

            switch (selected) {
                case 1:
                    employeeDAO.selectAllEmp(con);
                    break;
                case 2:
                    System.out.print("조회할 사번을 입력하세요: ");
                    String selectedEmpId = sc.nextLine();
                    employeeDAO.selectByIdEmp(con, selectedEmpId);

                    break;

                case 3:
                    EmployeeDTO newEmployee = new EmployeeDTO();

                    System.out.println("등록할 직원 정보를 입력해주세요");
                    System.out.print("사번: ");
                    newEmployee.setEmpId(sc.nextLine());
                    System.out.print("이름: ");
                    newEmployee.setEmpName(sc.nextLine());
                    System.out.print("주민등록번호: ");
                    newEmployee.setEmpNo(sc.nextLine());
                    System.out.print("직급코드: ");
                    newEmployee.setJobCode(sc.nextLine());
                    System.out.print("급여등급: ");
                    newEmployee.setSalLevel(sc.nextLine());

                    int insertResult = employeeDAO.insertEmp(con, newEmployee);

                    if (insertResult > 0) {
                        System.out.println("직원 등록 성공!");
                    } else {
                        System.out.println("직원 등록 실패!");
                    }

                    break;

                case 4:
                    System.out.println("수정할 직원의 사번을 입력해주세요");
                    System.out.print("사번: ");
                    String upEmpId = sc.nextLine();

                    System.out.println("수정할 정보를 입력하세요");

                    System.out.print("직급코드: ");
                    String upJobCode = sc.nextLine();
                    System.out.print("급여등급: ");
                    String upSalLevel = sc.nextLine();

                    int updateResult = employeeDAO.modifyEmp(con, upEmpId, upJobCode, upSalLevel);

                    if (updateResult > 0) {
                        System.out.println("직원 정보 수정 성공!");
                    } else {
                        System.out.println("직원 정보 수정 실패!");
                    }

                case 5:
                    System.out.println("삭제할 직원의 사번을 입력해주세요");
                    System.out.print("사번: ");
                    String delEmpId = sc.nextLine();

                    int delResult = employeeDAO.deleteEmp(con, delEmpId);

                    if (delResult > 0) {
                        System.out.println("직원 삭제 성공!");
                    } else {
                        System.out.println("직원 삭제 실패!");
                    }

                    break;

                case 6:
                    System.out.println("프로그램을 종료합니다.");
                    close(con);

                    return;

                default:
                    System.out.println("잘못선택하셨습니다.");
                    break;
            }
        }

    }
}
