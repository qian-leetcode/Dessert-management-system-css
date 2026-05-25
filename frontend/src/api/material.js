import request from "@/uitls/request.js";

// 不分页查询
export function get_material_list_() {
    return request.get("/material_information/list")
}


//分页查询
export function get_query_list(params){
    return request.post("/material_information/query_list",params);
}

// 新增原料
export function add_material_list_(params){
    return request.post("/material_information/add",params);
}

// 删除原料
export function delete_material_list_(id){
    return request.post("/material_information/delete", {} ,{params : {id}});
}

// 修改原料
export function update_material_list_(params){
    return request.post("/material_information/update",params);
}
