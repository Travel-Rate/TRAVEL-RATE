import React, { useState } from "react";
import { Dropdown } from "../components/Dropdown";

const ExchangeRatio = () => {
    const [view, setView] = useState(false);

    return (
        <div className="ExchangeRatio">
            <header></header>
            <body>
                <div className="upper-side-contents">
                <span>도움말 설정</span><div className="help-check-box"></div>
                <span>목표환율 설정</span><option onClick={()=>{setView(!view)}} value="country" className="upper-side-inner-select-country">
                    {view? '⌃':'⌄'}
                    {view && <Dropdown/>}
                </option>
                <div>1250$</div>
                <img src="" alt="plus-img" />
                 <p>환율알림 추가</p>
                </div>
                <div className="lower-side-contents">
                    <div>
                        <span>최대 3개국/오전 11시 마다 업데이트</span>
                        <span>환율 수정 / 삭제</span>
                    </div>
                    <div className="lower-side-inner-country1">
                        <div>
                        <img src="" alt="" />
                        <span></span>
                        </div>
                        <div>
                            <img src="" alt="modify" />
                            <img src="" alt="delete" />
                        </div>
                    </div>
                </div>
            </body>
        </div>
    )
}

export default ExchangeRatio;