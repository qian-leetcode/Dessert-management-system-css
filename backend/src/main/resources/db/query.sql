SELECT q.inventory_id,
       q.material_id,
       q.current_inventory_level,
       q.safety_stock_quantity,
       q.last_purchase_time,
       m.material_name
FROM material_inventory q
         LEFT JOIN material_information m ON m.material_id = q.inventory_id;

SELECT
    p.purchase_id, p.purchase_order_number, p.purchase_date, p.material_id,
    m.material_name,
    p.purchase_quantity, p.purchase_price, p.purchase_amount, p.supplier_name,
    p.production_batch, p.production_date, p.payment_status,
    u.name,
    p.remark, p.create_time
FROM purchase_record p
         LEFT JOIN material_information m ON m.material_id = p.material_id
         LEFT JOIN t_user u ON p.user_id = u.id;

INSERT INTO category (name , description) values (312312 , 312323)