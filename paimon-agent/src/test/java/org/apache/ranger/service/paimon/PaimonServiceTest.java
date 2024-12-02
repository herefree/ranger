package org.apache.ranger.service.paimon;

import com.amazonaws.annotation.SdkTestInternalApi;
import org.apache.ranger.authorization.paimon.PaimonAccessType;
import org.apache.ranger.authorization.paimon.RangerPaimonAccessController;
import org.apache.ranger.authorization.paimon.UserIdentity;

public class PaimonServiceTest {
    @SdkTestInternalApi
    public void test() {
        RangerPaimonAccessController rangerPaimonAccessController = new RangerPaimonAccessController("");
        UserIdentity userIdentity=new UserIdentity("");
        boolean b = rangerPaimonAccessController.checkTblPriv(userIdentity, "paimon", "db", PaimonAccessType.ALTER);


    }
}
