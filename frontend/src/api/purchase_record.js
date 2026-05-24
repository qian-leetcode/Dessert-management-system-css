import request from "@/uitls/request.js";

export function get_purchase_record_form(params){
    return request.post("/purchase_record/purchase_record_list", params)
}
