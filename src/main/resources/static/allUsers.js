const tbody = $('#all_user');
getTableUser()
function getTableUser() {
    tbody.empty()
    fetch("/api/users")
        .then(res => res.json())
        .then(js => {
            js.forEach(item => {
                const users = `$(
                    <tr>
                        <td class="pt-3" id="userID">${item.id}</td>
                        <td class="pt-3" >${item.firstName}</td>
                        <td class="pt-3" >${item.lastname}</td>
                        <td class="pt-3" >${item.age}</td>
                        <td class="pt-3" >${item.email}</td>
                        <td class="pt-3" >${item.roles.map(r => ' ' + r.name.replaceAll('ROLE_', ' '))}</td>
                        <td>
                            <button type="button" class="btn btn-info" data-toggle="modal" data-target="#edit" onclick="editModal(${item.id})">
                            Edit
                            </button>
                        </td>
                        <td>
                            <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#delete" onclick="deleteModal(${item.id})">
                                Delete
                            </button>
                        </td>
                    </tr>)`;
                tbody.append(users)
            })
        })
}
