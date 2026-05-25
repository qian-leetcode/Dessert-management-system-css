import request from "../uitls/request";

// 不分页查询
export function get_user_list_() {
    return request.get("/user/list")
}


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

export function delete_user_information_(id){
    return request.post("/user/delete",{ },{
        params:{id}
    })
}

export function update_user_information_(params){
    return request.post("/user/update",params)
}