import request from "../uitls/request";

// 获取列表信息
export function get_dessert_List_(params){
    return request.post("/category/list",params);
}


// 获取分类名称
export function get_dessert_list_name_(){
    return request.get("/category/list_name");
}

// 新增分类
export function add_dessert_list_(params){
    return request.post("/category/add",params);
}

// 根据id 删除分类
export function delete_dessert_list_(id){
    return request.post("/category/delete" ,{}, {
        params: { id }
    });
}

// 根据id 修改分类
export function update_dessert_list_(params){
    return request.post("/category/update",params);
}
