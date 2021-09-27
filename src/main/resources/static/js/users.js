//for admin page
function CleanUserTable() {
    let e = document.getElementById('usersTable');
    while (e.rows[1]) {
        e.deleteRow(1);
    }
}

function GetUsersTable() {
    let url = 'http://localhost:8080/admin/users';
    fetch(url)
        .then(response => {
            return response.json();
        })
        .then(data => {
            function buildTable(users) {
                let table = document.getElementById('usersTable');
                for (i in users) {
                    let roles = ``;
                    for (j in users[i].roles) {
                        let role = `${users[i].roles[j].name}`
                        roles += role + `<br/>`;
                    }
                    let row = `<tr>
                                    <td>${users[i].id}</td>
                                    <td>${users[i].username}</td>
                                    <td>${users[i].name}</td>
                                    <td>${users[i].age}</td>
                                    <td>${users[i].email}</td>
                                    <td>`
                        + roles +
                        `</td>
                                    <td>${users[i].updatedAt}</td>
                                    <td><a class="btn btn-info eBtn" onclick="GetUserDataModalForm(${users[i].id}, 'edit')">Edit</a></td>
                                    <td><a class="btn btn-danger dBtn" onclick="GetUserDataModalForm(${users[i].id}, 'delete')">Delete</a></td>
                              </tr>`;
                    table.innerHTML += row;
                }
            }

            buildTable(data);
        })
        .catch(function () {
            this.dataError = true;
        })
}

function GetAllRoles(tableId) {
    let url = 'http://localhost:8080/admin/roles';
    fetch(url)
        .then(response => {
            return response.json();
        })
        .then(data => {
            function buildFormSelectRoles(roles) {
                let table = document.getElementById(tableId);
                for (i in roles) {
                    let row = `<option value="${roles[i].id}">${roles[i].name}</option>`;
                    table.innerHTML += row;
                }
            }

            buildFormSelectRoles(data)
        })
        .catch(function () {
            this.dataError = true;
        })
}

async function AddNewUser() {

    let url = 'http://localhost:8080/admin/users/add';
    let options = document.getElementById('allRolesNewUser').selectedOptions;
    let values = Array.from(options).map(({value}) => value);
    let data = {
        username: document.getElementById("newUsername").value,
        name: document.getElementById("newName").value,
        password: document.getElementById("newPassword").value,
        age: document.getElementById("newAge").value,
        email: document.getElementById("newEmail").value,
        roles: values
    }
    let request = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(data)
    }

    let response = await fetch(url, request);
    if (response.ok) {
        CleanUserTable();
        GetUsersTable();
    }
}

function GetUserDataModalForm(userId, formId) {

    let pref;
    if (formId === `edit`) {
        pref = `.editForm #edit`
    }
    if (formId === `delete`) {
        pref = `.deleteForm #delete`
    }

    let url = 'http://localhost:8080/admin/users/' + userId;
    fetch(url)
        .then(response => {
            return response.json();
        })
        .then(data => {
            $(pref + `-id`).val(`${data.id}`);
            $(pref + `-username`).val(`${data.username}`);
            $(pref + `-password`).val(`${data.password}`);
            $(pref + `-name`).val(`${data.name}`);
            $(pref + `-age`).val(`${data.age}`);
            $(pref + `-email`).val(`${data.email}`);
        })
        .catch(function () {
            this.dataError = true;
        })

    $(pref + `Modal`).modal('show');
}

async function EditUser() {

    let userId = document.getElementById("edit-id").value;
    let url = 'http://localhost:8080/admin/users/' + userId + '/edit';

    let options = document.getElementById("edit-roles").selectedOptions;
    let values = Array.from(options).map(({value}) => value);

    let data = {
        id: document.getElementById("edit-id").value,
        username: document.getElementById("edit-username").value,
        name: document.getElementById("edit-name").value,
        password: document.getElementById("edit-password").value,
        age: document.getElementById("edit-age").value,
        email: document.getElementById("edit-email").value,
        roles: values
    }
    let request = {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(data)
    }

    let response = await fetch(url, request);
    if (response.ok) {
        CleanUserTable();
        GetUsersTable();
    }
}

async function DeleteUser() {
    let userId = document.getElementById("delete-id").value;
    let url = 'http://localhost:8080/admin/users/' + userId + '/delete';
    let request = {
        method: 'DELETE'
    }

    let response = await fetch(url, request);
    if (response.ok) {
        CleanUserTable();
        GetUsersTable();
    }
}

function GetCurrentUserInfo() {

    url = 'http://localhost:8080/admin/users/';

    fetch(url)
        .then(response => {
            return response.json();
        })
        .then(data => {
            function buildUserInfo(info) {
                let label = document.getElementById('currentUser');
                let row = `<label>${info[0].username}&nbsp with roles: &nbsp</label>`;
                label.innerHTML += row;
                for (i in info[0].roles) {
                    row = `<label>${info[0].roles[i].name}&nbsp</label>`;
                    label.innerHTML += row;
                }

                let tableI = document.getElementById('userInfoTableAdmin');
                let rolesI = ``;
                for (i in info[0].roles) {
                    let roleI = `${info[0].roles[i].name}`
                    rolesI += roleI + `<br/>`;
                }
                let rowI = `<tr>
                                <td>${info[0].id}</td>
                                <td>${info[0].username}</td>
                                <td>${info[0].name}</td>
                                <td>${info[0].age}</td>
                                <td>${info[0].email}</td>
                                <td>` + rolesI + `</td>
                                <td>${info[0].updatedAt}</td>
                                <td>${info[0].createdAt}</td>
                            </tr>`;
                tableI.innerHTML += rowI;
            }

            buildUserInfo(data);

        })
        .catch(function () {
            this.dataError = true;
        })
}

//for user page
function GetCurrentUserInfoUserPage() {

    url = 'http://localhost:8080/user/info/';

    fetch(url)
        .then(response => {
            return response.json();
        })
        .then(data => {
            function buildUserInfo(info) {
                let label = document.getElementById('currentUser');
                let row = `<label>${info.username}&nbsp with roles: &nbsp</label>`;
                label.innerHTML += row;
                for (i in info.roles) {
                    row = `<label>${info.roles[i].name}&nbsp</label>`;
                    label.innerHTML += row;
                }

                let tableI = document.getElementById('userInfoTableUser');
                let rolesI = ``;
                for (i in info.roles) {
                    let roleI = `${info.roles[i].name}`
                    rolesI += roleI + `<br/>`;
                }
                let rowI = `<tr>
                                <td>${info.id}</td>
                                <td>${info.username}</td>
                                <td>${info.name}</td>
                                <td>${info.age}</td>
                                <td>${info.email}</td>
                                <td>` + rolesI + `</td>
                                <td>${info.updatedAt}</td>
                                <td>${info.createdAt}</td>
                            </tr>`;
                tableI.innerHTML += rowI;
            }

            buildUserInfo(data);

        })
        .catch(function () {
            this.dataError = true;
        })
}