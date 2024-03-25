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
                this._loadEmployees.bind(this)
            );
        
        this._loadEmployees();
    }

    // Methods:
    _onInsert() {
        
    }

    _onUpdate() {

    }

    async _loadEmployees() {
        try {
            const response = await fetch("/reactive-jee/rest/employee");

            const { result } = await response.json();

            await this._showEmployees(result);
        }
        catch (error) {
            console.error(error);
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

            btnDelete.textContent = "Delete";
            btnDelete.type = "button";

            actionCol.appendChild(btnUpdate);
            actionCol.appendChild(btnDelete);

            row.appendChild(idCol);
            row.appendChild(nameCol);
            row.appendChild(actionCol);

            tbl.appendChild(row);
        }
    }
}