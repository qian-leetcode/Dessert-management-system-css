import request from "../uitls/request";


export function get_user_role_List() {
    return request.get("/role/role")
}

export function get_user_username_list() {
    return request.get("/user/username")
}