package com.xwy.redisLock;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;

/**
 * @description:
 * @projectName:source
 * @see:com.xwy.redisLock
 * @author:xwy
 * @createTime:5/8/2021 下午9:43
 * @version:1.0
 */
public class BloomFilterDemo {
    public static void main(String[] args) {
        BloomFilter bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()),
                1000000,0.001); //1%
                bloomFilter.put("xxx");
        System.out.println(bloomFilter.mightContain("xxx"));

    }
}
