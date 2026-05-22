import request from "../uitls/request";

export function get_dessert_List(params){
    return request.post("/category/list",params);
}


