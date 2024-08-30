import React from "react";
import { Link } from "react-router-dom";

export const Nav = () => {
    const Token = false
    return (
        <nav>
            <Link to={'/'}>
            <img src="" alt="logo" />
            </Link>
            <Link to={'/recommendation'}>
            <span>여행지 추천</span>
            </Link>
            <Link to={'/exchange'}>
            <span>목표환율 설정</span>
            </Link>        
            <Link to={'/login'}>
             <span>
                로그인
            </span>
            </Link>

        </nav>
    )
}