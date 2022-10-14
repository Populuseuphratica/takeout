package com.killstan.takeout.util;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/10/14 15:17
 */
public class ThreadLocalForEmp {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal();

    public static void set(Long employeeId){
        threadLocal.set(employeeId);
    }

    public static Long get(){
        return threadLocal.get();
    }

    public static void remove(){
        threadLocal.remove();
    }


}
