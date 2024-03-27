package org.lequochai.reactive_jee.models;

import java.util.ArrayList;
import java.util.List;

import org.lequochai.reactive_jee.system.Setting;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ReactiveEmployeeContainer {
    // Static fields:
    private static ReactiveEmployeeContainer instance;

    // Static methods:
    public static ReactiveEmployeeContainer getInstance() {
        if (instance == null) {
            instance = new ReactiveEmployeeContainer();
        }

        return instance;
    }

    // Fields:
    private List<Employee> employees;
    private Disposable generateEmployeesDisposable;

    // Constructors:
    public ReactiveEmployeeContainer() {
        employees = new ArrayList<>();

        generateEmployeesDisposable = Observable.range(0, Setting.EMPLOYEE_GENERATE_MAX)
        .observeOn(Schedulers.io())
        .map(
            id -> new Employee(id, "Employee " + id)
        )
        .subscribe(
            employee -> {
                employees.add(employee);

                System.out.println("Generated Employee " + employee.getId() + " !");

                try {
                    Thread.sleep(Setting.EMPLOYEE_GENERATE_INTERVAL);
                }
                catch (Exception e) {
                }
            }
        );
    }

    // Methods:
    public Single<List<Employee>> getAll() {
        return Single.just(employees)
            .observeOn(Schedulers.io());
    }

    public Single<List<Employee>> get(String keyword) {
        return Single.fromSupplier(
            () -> {
                if (keyword == null) {
                    throw new Error("Keyword cannot be null!");
                }

                if (keyword.equals("")) {
                    throw new Error("Keyword cannot be empty!");
                }

                List<Employee> result = new ArrayList<>();

                for (Employee employee : employees) {
                    if (employee.toString().toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(employee);
                    }
                }

                return result;
            }
        )
        .observeOn(Schedulers.io());
    }

    public Maybe<Employee> get(int id) {
        return Maybe.fromSupplier(
            () -> {
                for (Employee employee : employees) {
                    if (employee.getId() == id) {
                        return employee;
                    }
                }

                return null;
            }
        )
        .observeOn(Schedulers.io());
    }

    public Maybe<Boolean> insert(Employee employee) {
        return Maybe.fromSupplier(
            () -> {
                if (
                    get(employee.getId())
                    .blockingGet() != null
                ) {
                    throw new Error("Employee with given ID already exist!");
                }

                employees.add(employee);

                return true;
            }
        )
        .observeOn(Schedulers.io());
    }

    public Maybe<Boolean> update(Employee employee) {
        return Maybe.fromSupplier(
            () -> {
                Employee target = get(employee.getId()).blockingGet();

                if (target == null) {
                    throw new Error("Employee with given ID doesn't exist!");
                }

                target.setName(employee.getName());

                return true;
            }
        )
        .observeOn(Schedulers.io());
    }

    public Maybe<Boolean> delete(int id) {
        return Maybe.fromSupplier(
            () -> {
                Employee target = get(id).blockingGet();

                if (target == null) {
                    throw new Error("Employee with given ID doesn't exist!");
                }

                employees.remove(target);

                return true;
            }
        )
        .observeOn(Schedulers.io());
    }

    public Single<Boolean> cancelGenerateEmployees() {
        return Single.fromSupplier(
            () -> {
                if (!generateEmployeesDisposable.isDisposed()) {
                    generateEmployeesDisposable.dispose();
                }

                return true;
            }
        )
        .observeOn(
            Schedulers.io()
        );
    }
}
