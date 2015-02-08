package org.ray.upnp.service;

public class StateVariable {
    public static final String TAG = "stateVariable";
    public static final String TAG_NAME = "name";
    public static final String TAG_DATA_TYPE = "dataType";
    
    /* Optional. Defines whether event messages will be generated when the value
     * of this state variable changes. Defaut value is "yes".
     */
    String mSendEvents = "yes";
    
    /* Optional. Defines whether event messages will be delivered using 
     * multicast eventing. Default value is "no".
     */
    String mMulticast = "no";
    
    /* Required. Name of state variable. */
    String mName;
    
    /* Required. Same as data types defined by XML Schema. */
    String mDataType;
    
}
