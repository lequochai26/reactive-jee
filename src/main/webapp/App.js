export default class App {
    // Constructors:
    constructor() {
        this._employeeForm = document.querySelector("#employeeForm");
        this._searchForm = document.querySelector("#searchForm");
        this._tblEmployee = document.querySelector("#tblEmployee");

        this._employeeForm.querySelector("#btnInsert")
            .addEventListener(
                "click",
                this._onInsert.bind(this)
            );
        
        this._employeeForm.querySelector("#btnUpdate")
            .addEventListener(
                "click",
                this._onUpdate.bind(this)
            );

        this._searchForm.querySelector("#btnReload")
            .addEventListener(
                "click",
                this._onLoadEmployees.bind(this)
            );

        this._searchForm.querySelector("#btnSearch")
            .addEventListener(
                "click",
                this._onSearch.bind(this)
            );
        
        this._searchForm.querySelector("#btnCancelGenerate")
            .addEventListener(
                "click",
                this._onCancelGenerate.bind(this)
            );
        
        this._onLoadEmployees();

        this._setEmployee({ id: 0, name: "" });
    }

    // Methods:
    async _onInsert() {
        const id = Number.parseInt(
            this._employeeForm.querySelector("#txtId").value
        );
        
        if (Number.isNaN(id)) {
            alert("ID must be an integer!");
            return;
        }

        const name = this._employeeForm.querySelector("#txtName").value;
        if (name === '') {
            alert("Name cannot be empty!");
            return;
        }

        const response = await fetch(
            "/reactive-jee/rest/employee",
            {
                method: "POST",
                headers: [
                    ["Content-Type", "application/json"]
                ],
                body: JSON.stringify({ id, name })
            }
        );

        const { success, message } = await response.json();
        if (success) {
            this._onLoadEmployees();
            this._clearEmployeeForm();
        }
        else {
            alert(message);
        }
    }

    async _onUpdate() {
        const id = Number.parseInt(
            this._employeeForm.querySelector("#txtId").value
        );

        if (Number.isNaN(id)) {
            alert("ID must be an integer!");
            return;
        }

        const name = this._employeeForm.querySelector("#txtName").value;
        if (name === '') {
            alert("Name cannot be empty!");
            return;
        }

        const response = await fetch(
            `/reactive-jee/rest/employee/${id}`,
            {
                method: "PUT",
                headers: [
                    ["Content-Type", "application/json"]
                ],
                body: JSON.stringify(
                    { id, name }
                )
            }
        );

        const { success, message } = await response.json();

        if (success) {
            this._onLoadEmployees();
            this._clearEmployeeForm();
        }
        else {
            alert(message);
        }
    }

    _clearEmployeeForm() {
        this._employeeForm.querySelector("#txtId").value = "";
        this._employeeForm.querySelector("#txtName").value = "";
    }

    async _onDelete(id) {
        const response = await fetch(
            `/reactive-jee/rest/employee/${id}`,
            {
                method: "DELETE"
            }
        );

        const { success, message } = await response.json();

        if (success) {
            this._onLoadEmployees();
        }
        else {
            alert(message);
        }
    }

    async _onLoadEmployees() {
        try {
            const response = await fetch("/reactive-jee/rest/employee");

            const { result } = await response.json();

            await this._showEmployees(result);
        }
        catch (error) {
            console.error(error);
        }
    }

    async _onSearch() {
        const keyword = this._searchForm.querySelector("#txtKeyword").value;

        if (keyword === '') {
            alert("Keyword can't be empty!");
            return;
        }

        const response = await fetch(
            `/reactive-jee/rest/employee?keyword=${keyword}`
        );

        const { success, message, result } = await response.json();

        if (success) {
            this._showEmployees(result);
        }
        else {
            alert(message);
        }
    }

    async _showEmployees(employees) {
        const tbl = this._tblEmployee;

        for (const row of tbl.querySelectorAll("tr")) {
            if (row.id !== 'tblEmployeeHeaderRow') {
                tbl.removeChild(row);
            }
        }

        for (const { id, name } of employees) {
            const row = document.createElement("tr");

            const idCol = document.createElement("td");
            const nameCol = document.createElement("td");
            const actionCol = document.createElement("td");
            const btnUpdate = document.createElement("button");
            const btnDelete = document.createElement("button");

            idCol.textContent = id;
            nameCol.textContent = name;
            
            btnUpdate.textContent = "Update"
            btnUpdate.type = "button";
            btnUpdate.addEventListener(
                "click",
                (() => { this._setEmployee({ id, name }) }).bind(this)
            );

            btnDelete.textContent = "Delete";
            btnDelete.type = "button";
            btnDelete.addEventListener(
                "click",
                (() => { this._onDelete(id) }).bind(this)
            );

            actionCol.appendChild(btnUpdate);
            actionCol.appendChild(btnDelete);

            row.appendChild(idCol);
            row.appendChild(nameCol);
            row.appendChild(actionCol);

            tbl.appendChild(row);
        }
    }

    async _onCancelGenerate() {
        const response = await fetch("/reactive-jee/rest/employee/generateEmployees",
        {
            method: "DELETE"
        })

        const { success, message } = await response.json();

        if (success) {
            alert("Employee auto generator cancelled successfully!");
        }
        else {
            alert(message);
        }
    }

    _setEmployee(employee) {
        if (!employee) {
            return;
        }

        this._employee = employee;

        this._employeeForm.querySelector("#txtId").value = this._employee.id;
        this._employeeForm.querySelector("#txtName").value = this._employee.name;
    }
}