package com.example.adventurexp.adventure.config;

import com.example.adventurexp.adventure.entity.*;
import com.example.adventurexp.adventure.repository.*;
import com.example.adventurexp.security.entity.Role;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class DataSetup implements ApplicationRunner {

        final EmployeeRepo employeeRepo;
        final CustomerRepo customerRepo;
        final ShiftRepo shiftRepo;
        final ReservationRepo reservationRepo;
        final ActivityRepo activityRepo;

        public DataSetup ( EmployeeRepo employeeRepo, CustomerRepo customerRepo, ShiftRepo shiftRepo, ReservationRepo reservationRepo, ActivityRepo activityRepo){
                this.employeeRepo = employeeRepo;
                this.customerRepo = customerRepo;
                this.shiftRepo = shiftRepo;
                this.reservationRepo = reservationRepo;
                this.activityRepo = activityRepo;
        }

        @Override
        public void run(ApplicationArguments args) throws Exception {

                List<Employee> employees1 = generateEmployees(8);
                for(Employee e: employees1){
                        e.addRole(Role.EMPLOYEE);
                }
                employeeRepo.saveAll(employees1);
                List<Employee> employees2 = generateEmployees(2);
                for(Employee e: employees2){
                        e.addRole(Role.ADMIN);
                }
                employeeRepo.saveAll(employees2);

                List<Customer> customers = generateCustomers(10);
                for (Customer c: customers){
                        c.addRole(Role.USER);
                }
                customerRepo.saveAll(customers);

                List<Activity> activities = generateActivities(10);
                activityRepo.saveAll(activities);

                List<Reservation> reservations = generateReservations(10, customers, activities);
                reservationRepo.saveAll(reservations);

                List<Shift> shifts = generateShifts(10, employees1);
                shiftRepo.saveAll(shifts);



        }


        public static List<Employee> generateEmployees(int numberOfEmployees) {
                List<Employee> employees = new ArrayList<>();
                Random random = new Random();

                String[] firstNames = {"Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Hannah", "Ivy", "Jack"};
                String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Martinez", "Davis", "Rodriguez", "Martinez"};
                String[] phonePrefixes = {"555-123", "555-456", "555-789", "555-321", "555-654"};
                String[] addresses = {"123 Main St", "456 Elm St", "789 Oak St", "321 Pine St", "654 Cedar St"};

                for (int i = 0; i < numberOfEmployees; i++) {
                        String firstName = firstNames[random.nextInt(firstNames.length)];
                        String lastName = lastNames[random.nextInt(lastNames.length)];
                        String phoneNumber = phonePrefixes[random.nextInt(phonePrefixes.length)] + String.format("%04d", random.nextInt(10000));
                        String address = addresses[random.nextInt(addresses.length)];
                        String username = firstName.toLowerCase() + "." + lastName.toLowerCase() + i;
                        String password = "password" + i;
                        String email = username + "@example.com";

                        Employee employee = new Employee(firstName, lastName, phoneNumber, address, username, password, email);
                        employees.add(employee);
                }

                return employees;
        }

        public static List<Customer> generateCustomers(int numberOfCustomers) {
                List<Customer> customers = new ArrayList<>();
                Random random = new Random();

                String[] firstNames = {"Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Hannah", "Ivy", "Jack"};
                String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Martinez", "Davis", "Rodriguez", "Martinez"};
                String[] phonePrefixes = {"555-123", "555-456", "555-789", "555-321", "555-654"};
                String[] addresses = {"123 Main St", "456 Elm St", "789 Oak St", "321 Pine St", "654 Cedar St"};

                for (int i = 0; i < numberOfCustomers; i++) {
                        String firstName = firstNames[random.nextInt(firstNames.length)];
                        String lastName = lastNames[random.nextInt(lastNames.length)];
                        String phoneNumber = phonePrefixes[random.nextInt(phonePrefixes.length)] + String.format("%04d", random.nextInt(10000));
                        String address = addresses[random.nextInt(addresses.length)];
                        String username = firstName.toLowerCase() + "." + lastName.toLowerCase() + i;
                        String password = "password" + i;
                        String email = username + "@example.com";

                        Customer customer = new Customer(firstName, lastName, phoneNumber, address, username, password, email);
                        customers.add(customer);
                }

                return customers;
        }

        public static List<Activity> generateActivities(int numberOfActivities) {
                List<Activity> activities = new ArrayList<>();
                Random random = new Random();

                String[] activityNames = {"go-kart", "minigolf", "paintball", "zipline", "rock climbing", "bungee jumping", "horseback riding", "archery", "sailing", "surfing"};
                double[] prices = {25.0, 15.0, 30.0, 40.0, 50.0, 60.0, 35.0, 20.0, 45.0, 55.0};

                for (int i = 0; i < numberOfActivities; i++) {
                        String name = activityNames[random.nextInt(activityNames.length)];
                        double price = prices[random.nextInt(prices.length)];
                        int minAge = random.nextInt(18) + 5; // Random age between 5 and 22
                        int capacity = random.nextInt(20) + 1; // Random capacity between 1 and 20

                        Activity activity = new Activity(name, price, minAge, capacity);
                        activities.add(activity);
                }

                return activities;
        }

        public static List<Reservation> generateReservations(int numberOfReservations, List<Customer> customers, List<Activity> activities) {
                List<Reservation> reservations = new ArrayList<>();
                Random random = new Random();

                for (int i = 0; i < numberOfReservations; i++) {
                        Customer customer = customers.get(random.nextInt(customers.size()));
                        LocalDate reservationStart = LocalDate.now().plusDays(random.nextInt(30)); // Random start date within the next 30 days
                        LocalDate reservationEnd = reservationStart.plusDays(random.nextInt(5) + 1); // Random end date within 1-5 days

                        Reservation reservation = new Reservation(customer, reservationStart, reservationEnd);

                        int numActivities = random.nextInt(activities.size()) + 1; // Random number of activities (1 to the total number of activities)
                        for (int j = 0; j < numActivities; j++) {
                                Activity activity = activities.get(random.nextInt(activities.size()));
                                reservation.addActivity(activity);
                        }

                        reservations.add(reservation);
                }

                return reservations;
        }

        public static List<Shift> generateShifts(int numberOfShifts, List<Employee> employees) {
                List<Shift> shifts = new ArrayList<>();
                Random random = new Random();

                for (int i = 0; i < numberOfShifts; i++) {
                        Employee employee = employees.get(random.nextInt(employees.size()));
                        LocalDate shiftStart = LocalDate.now().plusDays(random.nextInt(30)); // Random start date within the next 30 days
                        LocalDate shiftEnd = shiftStart.plusDays(random.nextInt(5) + 1); // Random end date within 1-5 days

                        Shift shift = new Shift(employee, shiftStart, shiftEnd);
                        shifts.add(shift);
                }

                return shifts;
        }
}
