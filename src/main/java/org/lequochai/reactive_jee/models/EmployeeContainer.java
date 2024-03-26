package org.lequochai.reactive_jee.models;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.ArrayList;

public class EmployeeContainer {
    // Static fields:
    private static final int LIMIT = 10000;
    private static EmployeeContainer instance;

    // Static methods:
    public static EmployeeContainer getInstance() {
        if (instance == null) {
            instance = new EmployeeContainer();
        }

        return instance;
    }

    // Fields:
    private List<Employee> employees;
    private Disposable generateEmployeesDisposable;

    // Constructors:
    public EmployeeContainer() {
        employees = new ArrayList<>();

        generateEmployeesDisposable = Flowable.range(0, LIMIT)
        .observeOn(
            Schedulers.computation()
        )
        .subscribe(
            id -> {
                employees.add(
                    new Employee(id, "Employee " + id)
                );
                System.out.println("Added employee with ID: " + id);
                
                try {
                    Thread.sleep(100);
                }
                catch (Exception e) {
                }
            }
        );
    }

    // Methods:
    public List<Employee> getAll() {
        return this.employees;
    }

    public Employee get(int id) {
        for (Employee employee : this.employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    public void insert(Employee employee) {
        employees.add(employee);
    }

    public void delete(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                employees.remove(employee);
                return;
            }
        }
    }

    public void cancelGenerateEmployees() {
        if (!generateEmployeesDisposable.isDisposed()) {
            generateEmployeesDisposable.dispose();
        }
    }
}
