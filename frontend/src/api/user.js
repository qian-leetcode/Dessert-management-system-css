import request from "../uitls/request";

// 获取用户职称列表
export function get_user_role_List() {
    return request.get("/role/role")
}

// 获取用户名用户名列表
export function get_user_username_list() {
    return request.get("/user/username")
}

// 获取用户信息列表
export function get_user_information_list(params) {
    return request.post("/user/information",params)
}