package org.apache.ranger.service.paimon;

import org.apache.ranger.plugin.service.RangerBaseService;
import org.apache.ranger.plugin.service.ResourceLookupContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RangerServicePaimon extends RangerBaseService {
    @Override
    public Map<String, Object> validateConfig() {

        return new HashMap<>();
    }

    @Override
    public List<String> lookupResource(ResourceLookupContext resourceLookupContext) throws Exception {

        return new ArrayList<>();
    }
}
