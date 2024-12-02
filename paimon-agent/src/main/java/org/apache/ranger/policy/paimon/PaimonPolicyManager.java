package org.apache.ranger.policy.paimon;

import org.apache.ranger.plugin.model.RangerPolicy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaimonPolicyManager {
    private RangerClient rangerClient;

    private String policyName;
    private String RANGER_SERVICE_NAME;
    public void createPolicy() {
        Map<String, String> filter = new HashMap<>();
        filter.put("policyName", policyName);
        filter.put("serviceName", RANGER_SERVICE_NAME);
        List<RangerPolicy> policies = rangerClient.findPolicies(filter);
        if (policies.isEmpty()) {
            rangerClient.createPolicy(creatPolicy("group", policyName, "db", "tb", Collections.singletonList("select")));
        }else{
            List<String> updatePermission = new ArrayList<>();
            if(checkPolicy(policies.get(0),"group","select")) {
                RangerPolicy rangerPolicy = addPolicyIterm(policies.get(0), "group", Collections.singletonList("select"));
                rangerClient.updatePolicy(RANGER_SERVICE_NAME,policyName,rangerPolicy);
            }
        }
    }

    public boolean checkPolicy(RangerPolicy rangerPolicy, String group, String permissionOp) {
        for (RangerPolicy.RangerPolicyItem policyItem : rangerPolicy.getPolicyItems()) {
            List<String> groups = policyItem.getGroups();
            if (!groups.contains(group)) {
                continue;
            }
            for (RangerPolicy.RangerPolicyItemAccess access : policyItem.getAccesses()) {
                if (access.getType().equals(permissionOp)) {
                    return true;
                }
            }
        }
        return false;
    }

    public RangerPolicy creatPolicy(String group, String policyName, String dbname, String tbName, List<String> permissionOpsList) {
        RangerPolicy rangerPolicy = new RangerPolicy();
        rangerPolicy.setService(RANGER_SERVICE_NAME);
        rangerPolicy.setName(policyName);
        rangerPolicy.setResources(creatResource(dbname,tbName));
        List<RangerPolicy.RangerPolicyItem> rangerPolicyItemList = new ArrayList<>();
        for(String op:permissionOpsList) {
            RangerPolicy.RangerPolicyItem rangerPolicyItem = creatRangerPolicyItem(group, op);
            rangerPolicyItemList.add(rangerPolicyItem);
        }
        rangerPolicy.setPolicyItems(rangerPolicyItemList);
        return rangerPolicy;
    }

    public static Map<String, RangerPolicy.RangerPolicyResource> creatResource(String dbName, String tbName) {

        Map<String, RangerPolicy.RangerPolicyResource> resourceMap = new HashMap<>();
        resourceMap.put("catalog", new RangerPolicy.RangerPolicyResource("paimon"));
        resourceMap.put("database", new RangerPolicy.RangerPolicyResource(dbName));
        resourceMap.put("table", new RangerPolicy.RangerPolicyResource(tbName));

        return resourceMap;
    }

    public RangerPolicy.RangerPolicyItem creatRangerPolicyItem(String group, String permission) {

        RangerPolicy.RangerPolicyItem rangerPolicyItem = new RangerPolicy.RangerPolicyItem();
        rangerPolicyItem.setGroups(Collections.singletonList(group));
        RangerPolicy.RangerPolicyItemAccess rangerPolicyItemAccess = new RangerPolicy.RangerPolicyItemAccess();
        rangerPolicyItemAccess.setType(permission);
        rangerPolicyItemAccess.setIsAllowed(true);
        rangerPolicyItem.setAccesses(Collections.singletonList(rangerPolicyItemAccess));

        return rangerPolicyItem;
    }

    private RangerPolicy addPolicyIterm(RangerPolicy rangerPolicy, String group, List<String> permissionOpList) {

        List<RangerPolicy.RangerPolicyItem> addRangerPolicyItemList = new ArrayList<>();
        for (String permissionOp : permissionOpList) {
            addRangerPolicyItemList.add(creatRangerPolicyItem(group, permissionOp));
        }
        List<RangerPolicy.RangerPolicyItem> policyItems = rangerPolicy.getPolicyItems();
        policyItems.addAll(addRangerPolicyItemList);

        return rangerPolicy;
    }

}
