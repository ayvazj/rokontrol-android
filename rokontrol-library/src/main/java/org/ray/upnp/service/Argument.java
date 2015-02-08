package org.ray.upnp.service;

public class Argument {
    public static final String TAG = "argument";
    public static final String TAG_NAME = "name";
    public static final String TAG_DIRECTION = "direction";
    public static final String TAG_RETVAL = "retval";
    public static final String TAG_RELATED_STATE_VARIABLE = "relatedStateVariable";
    
    /* Required. Name of formal parameter. */
    String mName;
    /* Required. Defines whether argument is an input or output paramter. */
    String mDirection;
    /* Optional. Identifies at most one output argument as the return value. */
    String mRetval;
    /* Required. Must be the same of a state variable. */
    String mRelatedStateVariable;
}
