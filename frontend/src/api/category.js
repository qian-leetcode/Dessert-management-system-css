import request from "../uitls/request";

export function get_dessert_List(params){
    return request.post("/category/list",params);
}

export function get_dessert_list_name(){
    return request.get("/category/list_name");
}

