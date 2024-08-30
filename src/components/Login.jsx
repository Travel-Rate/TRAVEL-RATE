import React from "react";
import { Routes, Route, Link } from "react-router-dom";
import SignUp from "./SignUp";

const Login = () => {
    return (
     <Link to={'/signup'}>
     회원가입
     </Link>
    )
}

export default Login