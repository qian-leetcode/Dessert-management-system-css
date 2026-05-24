import request from "@/uitls/request.js";

export function get_query_list(params){
    return request.post("/material_information/query_list",params);
}