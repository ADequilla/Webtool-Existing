/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.valuequest.common.annotation.spi;

import java.util.Objects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nugie
 */
@Component
@Aspect
public class LogConcreate {

    private final ThreadLocal<Log> threadLocalStringBuilder = new ThreadLocal<Log>() {

        @Override
        protected Log initialValue() {
            return new Log();
        }
    };

    @Around("@within(com.valuequest.common.annotation.Loggable)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature ms = MethodSignature.class.cast(point.getSignature());
        Log log = threadLocalStringBuilder.get();
        StringBuilder sb = log.getStringBuilder();
        boolean inner = false;
        String methodName;
        boolean isVoid = ms.getMethod().getReturnType() == Void.class;
        System.out.println(ms.getMethod().getReturnType());
        if (sb.length() == 0 || (sb.length() != 0 && sb.charAt(sb.length() - 1) == '\n')) {
            sb.setLength(0);
            sb.append(ms.getDeclaringTypeName()).append(". ").append(ms.getMethod().getReturnType()).append(" ").append(ms.getMethod().getName()).append('(');
            Class[] parameterType = ms.getParameterTypes();
            for (Class parameterType1 : parameterType) {
                sb.append(parameterType1).append(" , ");
            }
            if (parameterType.length != 0) {
                sb.setLength(sb.length() - 3);
            }
            sb.append(')');
            methodName = sb.toString();
            sb.setLength(0);
        } else {
            log.increamentDepth();
            inner = true;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(ms.getDeclaringTypeName()).append(". ").append(ms.getMethod().getReturnType().getName()).append(" ").append(ms.getMethod().getName()).append('(');
            Class[] parameterType = ms.getParameterTypes();
            for (Class parameterType1 : parameterType) {
                sb2.append(parameterType1.getName()).append(" , ");
            }
            if (parameterType.length != 0) {
                sb2.setLength(sb2.length() - 3);
            }
            sb2.append(')');
            methodName = sb2.toString();
            sb.append('\n');
        }
        padMessage(log, "############ START ", methodName, " ############");
        padMessage(log, "Object : ", point.getTarget().toString());
        padMessage(log, "Thread : ", Thread.currentThread().getName());
        
        Object[] args = point.getArgs();
        if (args != null) {
            if (args.length == 0) {
                padMessage(log, "No Parameter Passing To Method [argument length is 0]");
            } else {
                for (int i = 0; i < args.length; i++) {
                    padMessage(log, "Parameter[", Integer.toString(i), "]  type --> ", args[i].getClass().getName(), " , value --> ", Objects.toString(args[i]));
                }
            }
        } else {
            padMessage(log, "NULL Parameter Passing To Method");
        }
        long start = System.currentTimeMillis();
        Object result = null;
        try {
            result = point.proceed();
            if (!isVoid) {
                padMessage(log, "Result : " + result);
            }
            return result;
        } catch (Throwable t) {
            padMessage(log, "---------------START EXCEPTION HERE-------------------");
            padMessage(log, "Message ==>", t.getMessage());
            padMessage(log, "Throwable Class ==>", t.getClass().getName());
            
            StackTraceElement st = t.getStackTrace()[0];
            padMessage(log, "\tat ", st.getClassName(), ".", st.getMethodName(), "(", st.getFileName(), ":", Integer.toString(st.getLineNumber()), ")");
            padMessage(log, "---------------END EXCEPTION HERE----------------------");
            throw t;
        } finally {
            start = System.currentTimeMillis() - start;
            padMessage(log, "Time To Execute : ", Long.toString(start), " ms");
            padMessage(log, "############ END ", methodName, " ############\n");
            if (!inner) {
                System.out.println(sb.toString());
            } else {
                log.decreamentDepth();
            }
        }
    }

    /**
     * 
     * @param log
     * @param msg 
     */
    private void padMessage(Log log, String... msg) {
        log.getStringBuilder().append('\n');
        for (int i = 0; i < log.getInner(); i++) {
            log.getStringBuilder().append('\t');
        }
        for (String str : msg) {
            log.getStringBuilder().append(str);
        }

    }
}
