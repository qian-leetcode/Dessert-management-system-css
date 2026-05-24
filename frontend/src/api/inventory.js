import request from "@/uitls/request.js";

export function get_inventory_information(params) {
    return request.post("/material_inventory/get_inventory_information", params)
}

