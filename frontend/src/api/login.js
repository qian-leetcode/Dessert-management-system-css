import request from "../uitls/request";

export function user_login(username, password) {
    return request.post("/api/role",null , {
        params: {username, password}
    })
}

export function user_register(username, password) {
    return request.post("/api/role",null , {
        params: {username, password}
    })
}

// export function getAllUsers(filter = '') {
//     return request.get('/user/allUsers',{
//         params: {filter}
//     })
// }