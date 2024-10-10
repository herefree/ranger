package org.apache.ranger.authorization.paimon;

import org.omg.CORBA.PRIVATE_MEMBER;

public class UserIdentity {

    private String user;
    private String userGroup;
    private String host;

    public UserIdentity(String userGroup) {
        this.userGroup = userGroup;
    }

    public UserIdentity(String userGroup, String host) {
        this.userGroup = userGroup;
        this.host = host;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
