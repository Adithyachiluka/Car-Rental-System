package Car_Rental_System;

import java.util.Scanner;

public class CarRentalSystem 
{
	    private Car[] cars;
	    private Customer[] customers;
	    private Rental[] rentals;
	    private int carCount;
	    private int customerCount;
	    private int rentalCount;

	    public CarRentalSystem() {
	        cars = new Car[10];
	        customers = new Customer[10];
	        rentals = new Rental[10];
	        carCount = 0;
	        customerCount = 0;
	        rentalCount = 0;
	    }

	    public void addCar(Car car) {
	        cars[carCount++] = car;
	    }

	    public void addCustomer(Customer customer) {
	        customers[customerCount++] = customer;
	    }

	    public void addRental(Rental rental) {
	        rentals[rentalCount++] = rental;
	    }

	    public void rentCar(Car car, Customer customer, int days) {
	        if (car.isAvailable()) {
	            car.rent();
	            addRental(new Rental(car, customer, days));
	        } else {
	            System.out.println("Car is not available for rent.");
	        }
	    }

	    public void returnCar(Car car) {
	        car.returnCar();
	        for (int i = 0; i < rentalCount; i++) {
	            if (rentals[i].getCar() == car) {
	                // remove rental
	                for (int j = i; j < rentalCount - 1; j++) {
	                    rentals[j] = rentals[j + 1];
	                }
	                rentals[--rentalCount] = null;
	                System.out.println("Car returned successfully!");
	                return;
	            }
	        }
	        System.out.println("Car was not rented.");
	    }

	    public void showAvailableCars() {
	        System.out.println("\nAvailable Cars:");
	        for (int i = 0; i < carCount; i++) {
	            if (cars[i].isAvailable()) {
	                System.out.println(cars[i].getCarId() + " - " + cars[i].getBrand() + " " + cars[i].getModel());
	            }
	        }
	    }

	    public void menu() {
	        Scanner scanner = new Scanner(System.in);

	        while (true) {
	            System.out.println("\n===== Car Rental System =====");
	            System.out.println("1. Rent a Car");
	            System.out.println("2. Return a Car");
	            System.out.println("3. Exit");
	            System.out.print("Enter your choice: ");
	            int choice;

	            try {
	                choice = Integer.parseInt(scanner.nextLine());
	            } catch (NumberFormatException e) {
	                System.out.println("Invalid input. Please enter a number.");
	                continue;
	            }

	            if (choice == 1) {
	                System.out.print("\nEnter your name: ");
	                String customerName = scanner.nextLine();

	                showAvailableCars();

	                System.out.print("\nEnter the car ID you want to rent: ");
	                String carId = scanner.nextLine();

	                System.out.print("Enter the number of days for rental: ");
	                int rentalDays;
	                try {
	                    rentalDays = Integer.parseInt(scanner.nextLine());
	                } catch (NumberFormatException e) {
	                    System.out.println("Invalid number of days.");
	                    continue;
	                }

	                Customer newCustomer = new Customer("CUS" + (customerCount + 1), customerName);
	                addCustomer(newCustomer);

	                Car selectedCar = null;
	                for (int i = 0; i < carCount; i++) {
	                    if (cars[i].getCarId().equals(carId) && cars[i].isAvailable()) {
	                        selectedCar = cars[i];
	                        break;
	                    }
	                }

	                if (selectedCar != null) {
	                    double totalPrice = selectedCar.calculatePrice(rentalDays);
	                    System.out.println("\n== Rental Information ==");
	                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
	                    System.out.println("Customer Name: " + newCustomer.getName());
	                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
	                    System.out.println("Rental Days: " + rentalDays);
	                    System.out.printf("Total Price: $%.2f%n", totalPrice);

	                    System.out.print("\nConfirm rental (Y/N): ");
	                    String confirm = scanner.nextLine();

	                    if (confirm.equalsIgnoreCase("Y")) {
	                        rentCar(selectedCar, newCustomer, rentalDays);
	                        System.out.println("\nCar rented successfully!");
	                    } else {
	                        System.out.println("\nRental canceled.");
	                    }
	                } else {
	                    System.out.println("\nInvalid car selection or car not available for rent.");
	                }

	            } else if (choice == 2) {
	                System.out.print("\nEnter the car ID you want to return: ");
	                String carId = scanner.nextLine();

	                Car carToReturn = null;
	                for (int i = 0; i < carCount; i++) {
	                    if (cars[i].getCarId().equals(carId) && !cars[i].isAvailable()) {
	                        carToReturn = cars[i];
	                        break;
	                    }
	                }

	                if (carToReturn != null) {
	                    returnCar(carToReturn);
	                } else {
	                    System.out.println("Invalid car ID or car is not rented.");
	                }

	            } else if (choice == 3) {
	                System.out.println("\nThank you for using the Car Rental System!");
	                break;
	            } else {
	                System.out.println("Invalid choice. Please enter a valid option.");
	            }
	        }

	        scanner.close();
	    }
	}