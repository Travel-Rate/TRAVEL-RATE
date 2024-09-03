import React from "react";
import { Link } from "react-router-dom";
import styles from '../css/Login.module.scss'

const Login = () => {
    return (
        <div className={styles.loginContainer}>
        <form className={styles.loginInnerContainer}>
        <p className={styles.loginTitle}>로그인</p>
        <div className={styles.emailInput}>
        <input type="text" />
        </div>
        <div className={styles.pwInput}>
        <input type="text" />
        <img src="" alt="eye" className={styles.pwImg}/>
        </div>
        <div className={styles.linkBox}>

        <button className={styles.loginBtn}>
            로그인
        </button>

     <Link to={'/signup'}>
     회원가입
     </Link>
        </div>
        </form>
        </div>
    )
}

export default Login