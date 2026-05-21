import request from "../uitls/request";

export function user_login(username, password) {
    return request.post("/user/login", {
        username: username,
        password: password
    })
}

export function user_register(username, password, name , gender , phone , position, hire_date,shift) {
    return request.post("/user/register", {
        username: username,
        password: password,
        name : name,
        gender : gender,
        phone : phone,
        position : position,
        hire_date : hire_date,
        shift : shift,
    })
}

// export function getAllUsers(filter = '') {
//     return request.get('/user/allUsers',{
//         params: {filter}
//     })
// }