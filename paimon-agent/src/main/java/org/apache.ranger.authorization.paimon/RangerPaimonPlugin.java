package org.apache.ranger.authorization.paimon;

import org.apache.ranger.plugin.service.RangerBasePlugin;

public class RangerPaimonPlugin extends RangerBasePlugin {

    public RangerPaimonPlugin(String serviceType) {
        super(serviceType, null, null);
        super.init();
    }

    public RangerPaimonPlugin(String serviceType, String serviceName) {
        super(serviceType, serviceName, null);
        super.init();
    }

}
