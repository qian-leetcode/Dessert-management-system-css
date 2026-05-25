import request from "@/uitls/request.js";

export function get_purchase_record_form(params){
    return request.post("/purchase_record/purchase_record_list", params)
}

// 新增记录
export function add_purchase_record_form_(params){
    return request.post("/purchase_record/add", params)
}

// 删除记录

export function delete_purchase_record_form_(id){
    return request.post("/purchase_record/delete", {},{params:{id}});
}

// 修改记录
export function update_purchase_record_form_(params){
    return request.post("/purchase_record/update",params);
}

