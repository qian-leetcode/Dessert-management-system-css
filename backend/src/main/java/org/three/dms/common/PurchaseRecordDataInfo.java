package org.three.dms.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.three.dms.entity.PurchaseRecord;


import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PurchaseRecordDataInfo {
    private long total;
    private List<PurchaseRecord> rows = new ArrayList<>();
    private int code;
    private String msg;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<PurchaseRecord> getRows() {
        return rows;
    }

    public void setRows(List<PurchaseRecord> rows) {
        this.rows = rows;
    }

    public void add(PurchaseRecord purchaseRecord) {
        this.rows.add(purchaseRecord);
    }
}
