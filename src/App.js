import logo from './logo.svg';
import './App.css';

function App() {
  const user = false
  return (
    <div className="App">
      <header className="App-header">
        <div className='nav-bar'>
          <img src="logo" alt="" />
          <div>여행지 추천</div>
          <div>목표환율 설정</div>
          <div>{user? '로그인' : '로그아웃'}</div>
        </div>
      </header>
    </div>
  );
}

export default App;
