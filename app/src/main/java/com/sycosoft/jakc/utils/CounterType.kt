package com.sycosoft.jakc.utils

/**
 * Represents the type of counter.
 *
 * Global is not user deletable. There can only be one global counter per part. This increments all Normal counters linked.
 * Stitch is not user deletable. There can only be one stitch counter per part. This cannot be linked to the Global counter.
 * Normal is user deletable. This can be linked to the Global counter.
 */
enum class CounterType {
    Global,
    Stitch,
    Normal,
}