package com.softuni.mappingdto.init;

import com.softuni.mappingdto.dto.EmployeeDto;
import com.softuni.mappingdto.dto.ManagerDto;
import com.softuni.mappingdto.entity.Address;
import com.softuni.mappingdto.entity.Employee;
import com.softuni.mappingdto.service.AddressService;
import com.softuni.mappingdto.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppInitializer implements CommandLineRunner {

    private final AddressService addressService;
    private final EmployeeService employeeService;

    @Autowired
    public AppInitializer(AddressService addressService, EmployeeService employeeService) {
        this.addressService = addressService;
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {
        ModelMapper mapper =  new ModelMapper();

        Address address1 = new Address("Bulgaria","Sofia","Graf Ignatiev 50");
        address1=addressService.addAddress(address1);
        Employee employee1 = new Employee("Ivan","Petrov",
                LocalDate.of(1981,5,12), 3500,address1);
        employee1 = employeeService.addEmployee(employee1);

        EmployeeDto employeeDto = mapper.map(employee1, EmployeeDto.class);
        System.out.printf("EmployeeDto: %s%n",employeeDto);

        List<Address> addresses = List.of(
                new Address("Bulgaria", "Sofia", "Street 1"),
                new Address("Bulgaria", "Sofia", "Street 2"),
                new Address("Bulgaria", "Sofia", "Street 3"),
                new Address("Bulgaria", "Sofia", "Street 4"),
                new Address("Bulgaria", "Sofia", "Street 5")
        );
        addresses = addresses.stream().map(addressService::addAddress).collect(Collectors.toList());

        // Employees
        List<Employee> employees = List.of(
                new Employee("Steve", "Adams", LocalDate.of(1982,1,1),5600,addresses.get(0)),
                new Employee("Stephen", "Petrov",LocalDate.of(1983,3,3),3600,addresses.get(1)),
                new Employee("Hristina", "Petrova", LocalDate.of(1984,4,4),5000,addresses.get(1)),
                new Employee("Diana", "Atanasova", LocalDate.of(1985,5,5),2600,addresses.get(3)),
                new Employee("Samuil", "Ivanov", LocalDate.of(1986,6,6),4600,addresses.get(4))
        );
        List<Employee>created = employees.stream().map(employeeService::addEmployee).collect(Collectors.toList());

        created.get(1).setManager(created.get(0));
        created.get(2).setManager(created.get(0));

        created.get(4).setManager(created.get(3));

        List<Employee>updated = created.stream().map(employeeService::updateEmployee).collect(Collectors.toList());

        TypeMap<Employee, ManagerDto> managerTypeMap = mapper.createTypeMap(Employee.class, ManagerDto.class)
                .addMappings(m-> {
                    m.map(Employee::getSubordinates, ManagerDto::setEmployees);
                    m.map(src->src.getAddress().getCity(), ManagerDto::setCity);
//                    m.skip(ManagerDto::setCity);
                });
        mapper.getTypeMap(Employee.class,EmployeeDto.class).addMapping(
                src->src.getAddress().getCity(), EmployeeDto::setCity
        );

        List<Employee>managers = employeeService.getAllManagers();
        List<ManagerDto>managerDtos = managers.stream().map(managerTypeMap::map).collect(Collectors.toList());
        managerDtos.forEach(System.out::println);

       TypeMap employeeMap2= mapper.getTypeMap(Employee.class,EmployeeDto.class).addMapping(
                src->src.getManager().getLastName(), EmployeeDto::setManagerLastName);

        System.out.println("-".repeat(80)+"\n");
        List<Employee>employeesBefore1990 = employeeService.getAllEmployeesBornBefore(LocalDate.of(1990,1,1));
//        employeesBefore1990.forEach(System.out::println);
        employeesBefore1990.stream().map(employeeMap2::map).forEach(System.out::println);
    }
}
