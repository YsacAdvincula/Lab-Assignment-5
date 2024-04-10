import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

    public class DepartmentDA {
        private HashMap<String, Employee> employeeMap;

        public DepartmentDA () throws FileNotFoundException {
            Scanner deptFile = new Scanner(new FileReader("dep.csv"));
            employeeMap = new HashMap<>();
        
            deptFile.nextLine();

            while (deptFile.hasNext()) {
                String depRow = deptFile.nextLine();
                String[] depRowSpecific = new String[2];
                depRowSpecific = depRow.split(",");

                Department department = new Department();
                department.setDepCode(depRowSpecific[0]);
                department.setDepName(depRowSpecific[1].trim());

                readDepEmp(department); 
                department.setEmployeeMap(employeeMap);
            
                Double salary = 0.00;
                for (Map.Entry<String, Employee> entryMap : department.getEmployeeMap().entrySet()) {
                    salary += entryMap.getValue().getSalary();
                }
                department.setDepTotalSalary(salary);
            
                printDepartment(department);
            }
            deptFile.close();
        }

        private void printDepartment(Department department) {
            DecimalFormat df = new DecimalFormat("###,###.00");
            System.out.println("Department code: " + department.getDepCode());
            System.out.println("Department name: " + department.getDepName());
            System.out.println("Department total salary: " + df.format(department.getDepTotalSalary()));
            System.out.println("---------------------Details -------------------------");
            System.out.println("EmpNo\t\tEmployee Name\tSalary");
            for (Map.Entry<String, Employee> entryMap : department.getEmployeeMap().entrySet()) {
                System.out.print(entryMap.getValue().getEmpNo() + "\t");
                System.out.print(entryMap.getValue().getLastName() + "," + entryMap.getValue().getFirstName() + "\t");
                System.out.println(df.format(entryMap.getValue().getSalary()));
            }
            System.out.println();
        }

        private void readDepEmp(Department department) throws FileNotFoundException {
            Scanner deptEmpFile = new Scanner(new FileReader("deptemp.csv"));
            employeeMap.clear();
            deptEmpFile.nextLine();

            Integer key = 0;
            while (deptEmpFile.hasNext()) {
                String deptEmpRow = deptEmpFile.nextLine();
                String[] deptEmpRowSpecific = new String[3];
                deptEmpRowSpecific = deptEmpRow.split(",");

                if (department.getDepCode().equals(deptEmpRowSpecific[0])) {
                EmployeeDA employeeDA = new EmployeeDA(deptEmpRowSpecific[1].trim(), Double.parseDouble(deptEmpRowSpecific[2].trim()));
                
                    employeeMap.put(deptEmpRowSpecific[1].trim()+key, employeeDA.getEmployee());
                    key++;
                
                }
            
            }
            deptEmpFile.close();
        }
    
    }