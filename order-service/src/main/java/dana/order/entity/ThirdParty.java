package dana.order.entity;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

public class ThirdParty {
    private Integer idThirdParty;
    private String partyName;
    private Integer partyCode;

    public Integer getIdThirdParty() {
        return idThirdParty;
    }

    public void setIdThirdParty(Integer idThirdParty) {
        this.idThirdParty = idThirdParty;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public Integer getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(Integer partyCode) {
        this.partyCode = partyCode;
    }
}
