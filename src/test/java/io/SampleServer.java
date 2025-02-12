/*
 * SampleServer.java
 * Copyright 2021 Razertory, all rights reserved.
 * GUSU PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package io;


import com.streamwork.ch03.rpc.io.RpcNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gusu
 * @date 2021/6/26
 */
public class SampleServer extends RpcNode {

    public int foo(String s) {
        return 1;
    }
}
