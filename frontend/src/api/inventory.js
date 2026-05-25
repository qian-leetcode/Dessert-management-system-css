import request from "@/uitls/request.js";

export function get_inventory_information(params) {
    return request.post("/material_inventory/get_inventory_information", params)
}

// 新增库存记录
export function add_inventory_information_(params) {
    return request.post("/material_inventory/add", params);
}

// 删除库存记录
export function delete_inventory_information_(id) {
    return request.post("/material_inventory/delete", {},{params:{id}});
}

// 修改库存记录
export function update_inventory_information_(params) {
    return request.post("/material_inventory/update", params);
}

