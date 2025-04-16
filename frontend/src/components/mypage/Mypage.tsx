'use client';

import SelectHeader from '../layout/header/SelectHeader';
import MyInfo from './MyInfo';
import Setting from './Setting';

export default function Mypage() {
  const handleLogout = () => {};
  return (
    <>
      <SelectHeader
        mainText='마이 페이지'
        buttonText='Logout'
        onClick={handleLogout}
      />
      <section className='h-full overflow-y-auto'>
        <MyInfo />
        <Setting />
      </section>
    </>
  );
}
