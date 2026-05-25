import request from "../uitls/request";


export function get_desserts_all_list() {
    return request.get("/api/linked_list");
}

// 获取甜品列表
export function get_dessert_list_(params){
    return request.post("/dessert/list",params);
}

// 新增甜品
export function add_dessert_list_(params){
    return request.post("/dessert/add",params);
}

// 删除甜品
export function delete_dessert_list_(id){
    return request.post("/dessert/delete",{} ,{
        params :{id}
    });
}

// 修改甜品
export function update_dessert_list_(params){
    return request.post("/dessert/update",params);
}

