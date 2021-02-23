package com.jonsson.enu;

public enum OrderState {
    Not_Pay(0), // 未支付
    Pay(1); // 已支付
    private final int value;

    // 构造器默认也只能是private, 从而保证构造函数只能在内部使用
    OrderState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
