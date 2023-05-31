package com.korea.triplocation.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class TimerApp {
	
	@Pointcut("execution(* com.korea.triplocation..*.*(..))")
	private void pointCut() {}
	
	@Pointcut("@annotation(com.korea.triplocation.aop.annotation.TimerAspect)")
	private void annotationPointCut() {}
	
	@Around("pointCut()")  // advice라고 생각하면 됨.
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
	
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();		
		
		// 전처리
		Object logic = joinPoint.proceed(); // proceed = 메소드 호출, 해당 매소드 실행

		// 후처리
		
		stopWatch.stop();
		return logic;
	}
}
