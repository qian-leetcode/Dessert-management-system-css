package org.three.dms.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.three.dms.entity.MaterialInformation;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MaterialDataInfo {
    private long total;
    private List<MaterialInformation> rows = new ArrayList<>();
    private int code;
    private String msg;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<MaterialInformation> getRows() {
        return rows;
    }

    public void setRows(List<MaterialInformation> rows) {
        this.rows = rows;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void add(MaterialInformation materialInformation) {
        this.rows.add(materialInformation);
    }
}
