function CleanUserTable() {
    let e = document.getElementById('usersTable');
    while (e.rows[0]) {
        e.deleteRow(0);
    }
}

function GetUsersTable() {
    let url = 'http://localhost:8080/admin/users';
    fetch(url)
        .then(response => {
            return response.json();
        })
        .then(data => {
            function buildTable(users){
                let table = document.getElementById('usersTable');
                for (i in users){
                    let roles =``;
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
                                    <td><a class="btn btn-info eBtn" href="/admin/users/${users[i].id}/edit">Edit</a></td>
                                    <td><a class="btn btn-danger dBtn" href="/admin/users/${users[i].id}/delete">Delete</a></td>
                              </tr>`;
                    table.innerHTML += row;
                }
            }
            buildTable(data);
        })
        .catch(function() {
            this.dataError = true;
        })
}

function GetAllRoles(id) {
    let url = 'http://localhost:8080/admin/roles';
    fetch(url)
        .then(response => {
            return response.json();
        })
        .then(data => {
            function buildFormSelectRoles(roles){
                let table = document.getElementById(id);
                for (i in roles){
                    let row = `<option value="${roles[i].id}">${roles[i].name}</option>`;
                    table.innerHTML += row;
                }
            }
            buildFormSelectRoles(data)
        })
        .catch(function() {
            this.dataError = true;
        })
}

function AddNewUser() {
    let url =  'http://localhost:8080/admin/users/add';

    let options = document.getElementById('allRolesNewUser').selectedOptions;
    let values = Array.from(options).map(({ value }) => value);

    let data = {
        username: document.getElementById("newUsername").value,
        name: document.getElementById("newName").value,
        password: document.getElementById("newPassword").value,
        age: document.getElementById("newAge").value,
        email: document.getElementById("newEmail").value,
        roles: values
    }

    fetch(url, {
        method:'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(data)
    });
}


//вызов функций при инициализации
GetUsersTable();
GetAllRoles('allRolesNewUser')