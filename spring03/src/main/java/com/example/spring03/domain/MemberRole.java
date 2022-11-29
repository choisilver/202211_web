package com.example.spring03.domain;

// 사용자(회원)의 역할(권한) - 일반사용자, 관리자
// -> Spring Security에서 사용됨.
public enum MemberRole {
    USER, ADMIN;
}