package com.xwy.ArrayList.test;

import com.xwy.ArrayList.ExtArrayList;

import java.util.ArrayList;
import java.util.Arrays;

/*
	jdk1.7之后数组默认数据大小代码存放在add方法
	arrayList底层采用数组实现，数组名称：elementData
	ArrayList底层数组大小默认为10

 */
public class Test001 {

	// 底层采用数组方式
	// 怎么保证集合存放无限大小---数组扩容
	public static void main(String[] args) {
//		Object[] objects = { 1, 2 };
//		System.out.println(objects.length);
//		Object[] copyNewObjects = Arrays.copyOf(objects, 10);
//		System.out.println(copyNewObjects.length);
//
//
//		int[] fun = {0,1,2,3,4,5,6};
//		//参数src 原数组   secPos 起始位置  dest 目标数组 destPos 目标起始位置 length 复制长度
//		System.arraycopy(fun,3,fun,3,4);
//		for(int i :fun){
//			System.out.print(i);
//		}
//
//		ArrayList<String> arrayList = new ArrayList<>();
//		arrayList.add("张三");


		ExtArrayList<Integer> list = new ExtArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		for(int i=0;i<list.getSize();i++){
			System.out.print(list.get(i));
		}
		list.remove(2);
		System.out.println();
		for(int i=0;i<list.getSize();i++){
			System.out.print(list.get(i));
		}



	}

}