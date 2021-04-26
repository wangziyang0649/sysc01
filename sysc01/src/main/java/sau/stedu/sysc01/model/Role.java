package sau.stedu.sysc01.model;

import java.util.List;

public class Role {
    private Long id;
    private String rname;
    private String rnameZh;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRnameZh() {
        return rnameZh;
    }

    public void setRnameZh(String rnameZh) {
        this.rnameZh = rnameZh;
    }
}
