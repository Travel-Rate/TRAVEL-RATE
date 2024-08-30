import React from "react";
<<<<<<< HEAD

export function Dropdown() {
    return (
        <ul>
            <li>CAD 캐나다</li>
            <li>USD 미국</li>
            <li>AED 아랍에미리트</li>
            <li>BHD 바레인</li>
            <li>BND 브루나이</li>
            <li>CNH 중국</li>
            <li>HKD 홍콩</li>
            <li>IDR 인도네시아</li>
            <li>JPY 일본</li>
            <li>KRW 한국</li>
            <li>KWD 쿠웨이트</li>
            <li>MYR 말레이시아</li>
            <li>SAR 사우디아라비아</li>
            <li>SGD 싱가포르</li>
            <li>THB 태국</li>
            <li>AUD 호주</li>
            <li>NZD 뉴질랜드</li>
            <li>CHF 스위스</li>
            <li>DKK 덴마크</li>
            <li>EUR 유로</li>
            <li>GBP 영국</li>
            <li>NOK 노르웨이</li>
            <li>SEK 스웨덴</li>

        </ul>

    )
}
=======
import styles from'../css/DropDown.module.scss'
import useStore from "../store/store.jsx";

function Dropdown () {
    
    // const country = useStore()
    // console.log(country)
    // // const {storeCountry} = useStore((state) => state)


    const countries = [
        'CAD 캐나다',
        'USD 미국',
        'ED 아랍에미리트',
        'BHD 바레인',
        'BND 브루나이',
        'CNH 중국',
        'HKD 홍콩',
        'IDR 인도네시아',
        'JPY 일본',
        'KWD 쿠웨이트',
        'MYR 말레이시아',
        'SAR 사우디아라비아',
        'SGD 싱가포르',
        'THB 태국',
        'AUD 호주',
        'NZD 뉴질랜드',
        'CHF 스위스',
        'DKK 덴마크',
        'EUR 유로',
        'GBP 영국',
        'NOK 노르웨이',
        'SEK 스웨덴'
    ]
    return (
                <>
                {countries.map((country, index) => (
                    <option key={index} className={styles.dropdownItem}
                    >
                    {country}
                    </option>
                ))}
                </>
   
    )
}

export default Dropdown;
>>>>>>> ceed877 (feat: compo folder)
