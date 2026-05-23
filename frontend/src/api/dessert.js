import request from "../uitls/request";

export function get_dessert_list(params){
    return request.post("/dessert/list",params);
}