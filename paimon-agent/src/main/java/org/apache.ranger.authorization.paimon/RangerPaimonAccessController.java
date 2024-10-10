package org.apache.ranger.authorization.paimon;

import org.apache.ranger.plugin.policyengine.RangerAccessRequestImpl;
import org.apache.ranger.plugin.policyengine.RangerAccessResult;
import org.apache.ranger.plugin.service.RangerBasePlugin;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class RangerPaimonAccessController {

    private static String DEFAULT_CATALOG = "paimon";

    private RangerBasePlugin paimonPlugin;

    public RangerPaimonAccessController(String serviceName) {
        paimonPlugin = new RangerPaimonPlugin(serviceName);
    }

    public RangerPaimonAccessController(RangerBasePlugin plugin) {
        paimonPlugin = plugin;
    }

    private RangerAccessRequestImpl createRequest(UserIdentity currentUser, PaimonAccessType accessType) {
        RangerAccessRequestImpl request = createRequest(currentUser);
        request.setAction(accessType.getName());
        request.setAccessType(accessType.getName());
        return request;
    }

    protected RangerAccessRequestImpl createRequest(UserIdentity currentUser) {

        RangerAccessRequestImpl request = new RangerAccessRequestImpl();
        Set<String> setGroup = new HashSet<>();
        setGroup.add(currentUser.getUserGroup());
        request.setUserGroups(setGroup);
        request.setClientIPAddress(currentUser.getHost());
        request.setAccessTime(new Date());
        return request;
    }

    public boolean checkTblPriv(UserIdentity currentUser, String db, String tbl, PaimonAccessType paimonAccessType) {
        return checkTblPriv(currentUser, DEFAULT_CATALOG, db, tbl, paimonAccessType);
    }

    public boolean checkTblPriv(UserIdentity currentUser, String ctl, String db, String tbl, PaimonAccessType paimonAccessType) {
        RangerPaimonResource resource = new RangerPaimonResource(PaimonObjectType.TABLE, ctl, db, tbl);
        return checkPrivilege(currentUser, paimonAccessType, resource);
    }

    private boolean checkPrivilege(UserIdentity currentUser, PaimonAccessType accessType,
                                   RangerPaimonResource resource) {
        RangerAccessRequestImpl request = createRequest(currentUser, accessType);
        request.setResource(resource);
        RangerAccessResult result = paimonPlugin.isAccessAllowed(request);
        return checkRequestResult(request, result, accessType.name());

    }

    protected static boolean checkRequestResult(RangerAccessRequestImpl request,
                                                RangerAccessResult result, String name) {
        if (result == null) {
//            LOG.warn("Error getting authorizer result, please check your ranger config. Make sure "
//                    + "ranger policy engine is initialized. Request: {}", request);
            return false;
        }

        if (result.getIsAllowed()) {
//            if (LOG.isDebugEnabled()) {
//                LOG.debug("request {} match policy {}", request, result.getPolicyId());
//            }
            return true;
        } else {


//            if (LOG.isDebugEnabled()) {
//                LOG.debug(String.format(
//                        "Permission denied: user [%s] does not have privilege for [%s] command on [%s]",
//                        result.getAccessRequest().getUser(), name,
//                        result.getAccessRequest().getResource().getAsString()));
//            }
            return false;
        }
    }

}
